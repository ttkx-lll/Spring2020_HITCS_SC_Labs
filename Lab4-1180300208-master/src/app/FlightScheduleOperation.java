package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applicationEntry.FlightEntry;
import applicationEntry.FlightFactory;
import board.FlightBoard;
import board.FlightData;
import common.element.Location;
import common.element.Timeslot;
import common.operation.FirstStrategy;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import exception.EntryCancelException;
import exception.LocationConflictException;
import exception.ResourceConflictException;
import resource.Plane;

/**
 * 航班计划项app要用到的操作。
 * 
 * @author 梁书育
 */
public class FlightScheduleOperation extends ScheduleOperation<Plane> {

	private FlightData flightData = new FlightData();
	
	// Abstraction function:
	// 	  航班计划项app要用到的操作集合
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    没有对外泄露信息。
	
	/**
	 * 获得名称为planName，起止时间为timeslot的计划项
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @return 如果存在该航班则返回该计划项，若不存在则返回{@code null}
	 */
	public FlightEntry getPlan(String planName, Timeslot timeslot) {
		
		return flightData.getPlan(planName, timeslot);
	}
	
	@Override
	public boolean delResource(Plane resource) throws ResourceConflictException {
		
		for(FlightEntry fe : flightData) {
			if(fe.getResource().equals(resource)) {
				if(!fe.getPresentStateName().equals("已降落") || !fe.getPresentStateName().equals("已取消")) {
					throw new ResourceConflictException();
				}
			}
		}
		
		return super.delResource(resource);
	}
	
	@Override
	public boolean delLocation(Location loc) throws LocationConflictException {
		
		for(FlightEntry fe : flightData) {
			if(fe.getStartLoc().equals(loc) || fe.getEndLoc().equals(loc)) {
				if(!fe.getPresentStateName().equals("已降落") || !fe.getPresentStateName().equals("已取消")) {
					throw new LocationConflictException();
				}
			}
		}
		
		return super.delLocation(loc);
	}
	
	/**
	 * 检测如果要添加一个计划项是否非法
	 * 
	 * @param planName 要检测的计划项名称
	 * @param start 要检测的计划项起始位置
	 * @param end 要检测的计划项终止位置
	 * @param timeslot 要检测的计划项起止时间
	 * @return {@code 1} 如果加入该计划项名称是非法的；{@code 2} 如果存在多个航班计划项的“日期,航班号”信息完全一样的情况；
	 * 			{@code 3} 如果加入该计划项同一个航班号，虽然日期不同，但其出发或到达机场、 出发或到达时间有差异；{@code 0} 如果加入该计划项是合法的。
	 */
	public int checkIlleagalPlan(String planName, Location start, Location end, Timeslot timeslot) {
		
		Pattern namePattern = Pattern.compile("^([A-Z]{2})(\\d{2,4})$");
		Matcher matcher = namePattern.matcher(planName);
		String perfix = null;
		int num = -1;
		
		if(matcher.find() && matcher.groupCount() == 2) {
			perfix = matcher.group(1);
			num = Integer.valueOf(matcher.group(2));
		} else {
			return 1;
		}
		
		for(FlightEntry fe : flightData) {
			matcher = namePattern.matcher(fe.getPlanningName());
			if(matcher.find() && matcher.groupCount() == 2) {
				if(perfix.equals(matcher.group(1)) && num == Integer.valueOf(matcher.group(2))) {
					if(fe.getStartTime().toLocalDate().equals(timeslot.getStartTime().toLocalDate())) {
						return 2;
					}
					else if(!fe.getStartLoc().equals(start) || !fe.getEndLoc().equals(end) 
							|| !fe.getStartTime().toLocalTime().equals(timeslot.getStartTime().toLocalTime())
							|| !fe.getEndTime().toLocalTime().equals(timeslot.getEndTime().toLocalTime())) {
						return 3;
					}
					
				}
			}
			
		}
		return 0;
	}
	
	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param start 起始位置
	 * @param end 终止位置
	 * @param timeslot 起止时间
	 */
	public boolean addPlan(String planName, Location start, Location end, Timeslot timeslot) {
		
		if(!super.getLocationSet().contains(start)) {
			System.out.println("起始位置" + start.getLocName() + "不存在!");
			return false;
		}
		
		if(!super.getLocationSet().contains(end)) {
			System.out.println("终止位置" + end.getLocName() + "不存在!");
			return false;
		}
		
		if(checkIlleagalPlan(planName, start, end, timeslot) != 0) {
			System.out.println("航班信息不符合规范！");
			return false;
		}
		
		FlightEntry plan = new FlightFactory().getEntry(planName, start, end, timeslot);
		flightData.addData(plan);
		return true;
	}
	
	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void cancelPlan(String planName, Timeslot timeslot) throws EntryCancelException {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			if(fe.getPresentStateName().equals("未分配飞机") || fe.getPresentStateName().equals("已分配飞机")) {
				fe.cancel();
			} else {
				throw new EntryCancelException();
			}
		}
	}
	
	/**
	 * 为名称为planName，起止时间为timeslot的计划项分配资源。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @param resource 要添加的资源
	 */
	public boolean distributeResource(String planName, Timeslot timeslot, Plane resource) throws ResourceConflictException {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			if(super.getResourceSet().contains(resource)) {
				for(FlightEntry f : flightData) {
					if(f.getResource() != null && f.getResource().equals(resource)) {
						LocalDateTime ps = f.getStartTime();
						LocalDateTime pe = f.getEndTime();
						LocalDateTime qs = fe.getStartTime();
						LocalDateTime qe = fe.getEndTime();
						if(qs.isBefore(ps) && qe.isAfter(ps)) {
							throw new ResourceConflictException();
						}
						else if(qs.isAfter(ps) && qs.isBefore(pe)) {
							throw new ResourceConflictException();
						}
					}
				}
				fe.setResource(resource);
				return true;
			} else {
				System.out.println("该资源不存在!");
			}
		}
		return false;
	}
	
	/**
	 * 启动名称为planName，起止时间为timeslot的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void startPlan(String planName, Timeslot timeslot) {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			fe.start();
		}
	}
	
	/**
	 * 结束名称为planName，起止时间为timeslot的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void endPlan(String planName, Timeslot timeslot) {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			fe.end();
		}
	}
	
	/**
	 * 获得名称为planName，起止时间为timeslot的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName, Timeslot timeslot) {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			return fe.getPresentStateName();
		}
		return null;
	}
	
	@Override
	public boolean checkLocationAndResourseConflict() {
		
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		list.addAll(flightData.getDataList());
		PlanningEntryAPIs<Plane> helper = new PlanningEntryAPIs<Plane>();
		if(helper.checkLocationConflict(new FirstStrategy<Plane>(list))) {
			return true;
		}
		if(helper.checkResourceExclusiveConflict(list)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<PlanningEntry<Plane>> getAllPlanWithResource(Plane resource) {
		
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		
		for(FlightEntry fe : flightData) {
			if(fe != null && fe.getResource() != null && fe.getResource().equals(resource)) {
				list.add(fe);
			}
		}
		
		return list;
	}

	@Override
	public void viewBoardOfLoc(String locName) {
		FlightBoard fb;
		for(Location location : getLocationSet()) {
			if(location.getLocName().equals(locName)) {
				fb = new FlightBoard(flightData, location);
				fb.visualize();
			}
		}
	}
}
