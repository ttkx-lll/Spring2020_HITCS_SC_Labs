package app;

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
import source.Plane;

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
	
	/**
	 * 检测如果要添加一个计划项是否非法
	 * 
	 * @param planName 要检测的计划项名称
	 * @param start 要检测的计划项起始位置
	 * @param end 要检测的计划项终止位置
	 * @param timeslot 要检测的计划项起止时间
	 * @return {@code true}如果加入该计划项是非法的；{@code false}如果加入该计划项是合法的。
	 */
	public boolean checkIlleagalPlan(String planName, Location start, Location end, Timeslot timeslot) {
		
		Pattern namePattern = Pattern.compile("^([A-Z]{2})(\\d{2,4})$");
		Matcher matcher = namePattern.matcher(planName);
		String perfix = null;
		int num = -1;
		
		if(matcher.find() && matcher.groupCount() == 2) {
			perfix = matcher.group(1);
			num = Integer.valueOf(matcher.group(2));
		} else {
			return true;
		}
		
		for(FlightEntry fe : flightData) {
			matcher = namePattern.matcher(fe.getPlanningName());
			if(matcher.find() && matcher.groupCount() == 2) {
				if(perfix.equals(matcher.group(1)) && num == Integer.valueOf(matcher.group(2))) {
					if(fe.getStartTime().toLocalDate().equals(timeslot.getStartTime().toLocalDate())) {
						return true;
					}
					else if(!fe.getStartLoc().equals(start) || !fe.getEndLoc().equals(end) 
							|| !fe.getStartTime().toLocalTime().equals(timeslot.getStartTime().toLocalTime())
							|| !fe.getEndTime().toLocalTime().equals(timeslot.getEndTime().toLocalTime())) {
						return true;
					}
					
				}
			}
			
		}
		return false;
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
		
		if(checkIlleagalPlan(planName, start, end, timeslot)) {
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
	public void cancelPlan(String planName, Timeslot timeslot) {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			fe.cancel();
		}
	}
	
	/**
	 * 为名称为planName，起止时间为timeslot的计划项分配资源。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @param resource 要添加的资源
	 */
	public boolean distributeResource(String planName, Timeslot timeslot, Plane resource) {
		FlightEntry fe = flightData.getPlan(planName, timeslot);
		if(fe != null) {
			if(super.getResourceSet().contains(resource)) {
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
			if(fe.getResource().equals(resource)) {
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
