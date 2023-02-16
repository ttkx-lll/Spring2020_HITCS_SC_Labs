package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import applicationEntry.TrainEntry;
import applicationEntry.TrainFactory;
import board.TrainBoard;
import board.TrainData;
import common.element.Location;
import common.element.Timeslot;
import common.operation.FirstStrategy;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import exception.EntryCancelException;
import exception.LocationConflictException;
import exception.ResourceConflictException;
import resource.Carriage;

/**
 * 列车计划项app要用到的操作。
 * 
 * @author 梁书育
 */
public class TrainScheduleOperation extends ScheduleOperation<Carriage> {

	private TrainData trainData = new TrainData();
	
	// Abstraction function:
	// 	  列车计划项app要用到的操作集合
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    没有对外泄露信息。
	
	/**
	 * 取得指定名称的列车计划项。
	 * 
	 * @param planName 列车名称
	 * @return 如果存在该列车则返回该计划项，若不存在则返回{@code null}
	 */
	public TrainEntry getPlan(String planName) {
		
		return trainData.getPlan(planName);
	}
	
	/**
	 * 阻塞名称为planNmae的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void blockPlan(String planName) {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			te.block();
		}
	}
	
	/**
	 * 重新开始名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void restartPlan(String planName) {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			te.restart();
		}
	}

	@Override
	public boolean delResource(Carriage resource) throws ResourceConflictException {
		
		for(TrainEntry te : trainData) {
			if(te.getResources().contains(resource)) {
				if(!te.getPresentStateName().equals("已降落") || !te.getPresentStateName().equals("已取消")) {
					throw new ResourceConflictException();
				}
			}
		}
		
		return super.delResource(resource);
	}
	
	@Override
	public boolean delLocation(Location loc) throws LocationConflictException {
		
		for(TrainEntry te : trainData) {
			if(te.getStartLoc().equals(loc) || te.getEndLoc().equals(loc) || te.getMidLocations().contains(loc)) {
				if(!te.getPresentStateName().equals("已降落") || !te.getPresentStateName().equals("已取消")) {
					throw new LocationConflictException();
				}
			}
		}
		
		return super.delLocation(loc);
	}
	
	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param start 起始位置
	 * @param end 终止位置
	 * @param midLocList 中间经停位置
	 * @param totalTimeslot 总体起止时间
	 * @param blockTimeslotList 中间经停阻塞时间
	 */
	public boolean addPlan(String planName, Location start, Location end, List<Location> midLocList, 
			Timeslot totalTimeslot, List<Timeslot> blockTimeslotList) {
		if(trainData.getPlan(planName) != null) {
			System.out.println("已有名称为" + planName + "的计划项!");
			return false;
		}
		
		if(!super.getLocationSet().contains(start)) {
			System.out.println("起始位置" + start.getLocName() + "不存在!");
			return false;
		}
		
		if(!super.getLocationSet().contains(end)) {
			System.out.println("终止位置" + end.getLocName() + "不存在!");
			return false;
		}
		
		if(!super.getLocationSet().containsAll(midLocList)) {
			System.out.println("有的中间位置不存在!");
			return false;
		}
		
		TrainEntry plan = new TrainFactory().getEntry(planName, start, end, midLocList, totalTimeslot, blockTimeslotList);
		
		trainData.addData(plan);
		return true;
	}

	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void cancelPlan(String planName) throws EntryCancelException {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			if(te.getPresentStateName().equals("未分配车厢") || te.getPresentStateName().equals("已分配车厢")
					|| te.getPresentStateName().equals("停靠中间车站")) {
				te.cancel();
			} else {
				throw new EntryCancelException();
			}
		}
	}

	/**
	 * 为名称为planName的计划项分配资源（添加一个车厢）
	 * 
	 * @param planName 计划项名称
	 * @param resource 资源
	 */
	public boolean distributeResource(String planName, Carriage resource) throws ResourceConflictException {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			if(super.getResourceSet().contains(resource)) {
				for(TrainEntry t : trainData) {
					if(t.getResources().contains(resource)) {
						LocalDateTime ps = t.getTotalStartTime();
						LocalDateTime pe = t.getTotalEndTime();
						LocalDateTime qs = te.getTotalStartTime();
						LocalDateTime qe = te.getTotalEndTime();
						if(qs.isBefore(ps) && qe.isAfter(ps)) {
							throw new ResourceConflictException();
						}
						else if(qs.isAfter(ps) && qs.isBefore(pe)) {
							throw new ResourceConflictException();
						}
					}
				}
				te.addResource(resource);
				return true;
			} else {
				System.out.println("该资源不存在！");
			}
		}
		return false;
	}

	/**
	 * 启动名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void startPlan(String planName) {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			te.start();
		}
		
	}

	/**
	 * 结束名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void endPlan(String planName) {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			te.end();
		}
		
	}

	/**
	 * 获得名称为planName的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName) {
		TrainEntry te = trainData.getPlan(planName);
		if(te != null) {
			return te.getPresentStateName();
		}
		return null;
	}

	@Override
	public boolean checkLocationAndResourseConflict() {
		
		List<PlanningEntry<Carriage>> list = new ArrayList<>();
		list.addAll(trainData.getDataList());
		PlanningEntryAPIs<Carriage> helper = new PlanningEntryAPIs<>();
		if(helper.checkLocationConflict(new FirstStrategy<Carriage>(list))) {
			return true;
		}
		if(helper.checkResourceExclusiveConflict(list)) {
			return true;
		}
		return false;
	}

	@Override
	public List<PlanningEntry<Carriage>> getAllPlanWithResource(Carriage resource) {

		List<PlanningEntry<Carriage>> list = new ArrayList<>();
		
		for(TrainEntry te : trainData) {
			if(te.getResources().contains(resource)) {
				list.add(te);
			}
		}
		
		return list;
	}

	@Override
	public void viewBoardOfLoc(String locName) {
		TrainBoard tb;
		for(Location location : getLocationSet()) {
			if(location.getLocName().equals(locName)) {
				tb = new TrainBoard(trainData, location);
				tb.visualize();
			}
		}
	}

}
