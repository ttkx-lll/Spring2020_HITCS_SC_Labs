package app;

import java.util.ArrayList;
import java.util.List;

import applicationEntry.CourseEntry;
import applicationEntry.CourseFactory;
import board.CourseBoard;
import board.CourseData;
import common.element.Location;
import common.element.Timeslot;
import common.operation.FirstStrategy;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import source.Teacher;

/**
 * 课程计划项app要用到的操作。
 * 
 * @author 梁书育
 */
public class CourseScheduleOperation extends ScheduleOperation<Teacher> {

	private CourseData courseData = new CourseData();
	
	// Abstraction function:
	// 	  课程计划项app要用到的操作集合
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    没有对外泄露信息。
	
	/**
	 * 取得指定名称的课程计划项。
	 * 
	 * @param planName 课程名称
	 * @return 如果存在该课程则返回该计划项，若不存在则返回{@code null}
	 */
	public CourseEntry getPlan(String planName) {
		
		return courseData.getPlan(planName);
	}
	
	/**
	 * 改变计划项名称为planName的计划项的位置
	 * 
	 * @param planName 计划项名称
	 * @param postLoc 改变后的位置
	 * @return 改变成功返回{@code true}，否则返回{@code false}
	 */
	public boolean changeLocation(String planName, Location postLoc) {
		
		CourseEntry ce = courseData.getPlan(planName);
		
		if(!super.getLocationSet().contains(postLoc)) {
			System.out.println("位置" + postLoc.getLocName() + "不存在!");
			return false;
		}
		
		if(ce != null) {
			ce.changeLocation(postLoc);
			return true;
		}
		
		return false;
	}

	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param loc 教室信息
	 * @param timeslot 上下课时间
	 * @return {@code true}添加成功，否则{@code false}
	 */
	public boolean addPlan(String planName, Location loc, Timeslot timeslot) {
		if(courseData.getPlan(planName) != null) {
			System.out.println("已有名称为" + planName + "的计划项!");
			return false;
		}
		
		if(!super.getLocationSet().contains(loc)) {
			System.out.println("位置" + loc.getLocName() + "不存在!");
			return false;
		}
		
		CourseEntry plan = new CourseFactory().getEntry(planName, loc, timeslot);
		courseData.addData(plan);
		
		return true;
	}

	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void cancelPlan(String planName) {
		CourseEntry ce = courseData.getPlan(planName);
		if(ce != null) {
			ce.cancel();
		}
	}

	/**
	 * 为名称为planName的计划项分配资源。
	 * 
	 * @param planName
	 * @param resource
	 */
	public boolean distributeResource(String planName, Teacher resource) {
		CourseEntry ce = courseData.getPlan(planName);
		if(ce != null) {
			if(super.getResourceSet().contains(resource)) {
				ce.setResource(resource);
				return true;
			} else {
				System.out.println("该资源不存在!");
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
		CourseEntry ce = courseData.getPlan(planName);
		if(ce != null) {
			ce.start();
		}
		
	}

	/**
	 * 结束名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void endPlan(String planName) {
		CourseEntry ce = courseData.getPlan(planName);
		if(ce != null) {
			ce.end();
		}
		
	}

	/**
	 * 获得名称为planName的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName) {
		CourseEntry ce = courseData.getPlan(planName);
		if(ce != null) {
			return ce.getPresentStateName();
		}
		return null;
	}

	@Override
	public boolean checkLocationAndResourseConflict() {

		List<PlanningEntry<Teacher>> list = new ArrayList<>();
		list.addAll(courseData.getDataList());
		PlanningEntryAPIs<Teacher> helper = new PlanningEntryAPIs<>();
		if(helper.checkLocationConflict(new FirstStrategy<Teacher>(list))) {
			return true;
		}
		if(helper.checkResourceExclusiveConflict(list)) {
			return true;
		}
		return false;
	}

	@Override
	public List<PlanningEntry<Teacher>> getAllPlanWithResource(Teacher resource) {

		List<PlanningEntry<Teacher>> list = new ArrayList<>();
		
		for(CourseEntry ce : courseData) {
			if(ce.getResource().equals(resource)) {
				list.add(ce);
			}
		}
		
		return list;
	}

	@Override
	public void viewBoardOfLoc(String locName) {
		CourseBoard cb;
		for(Location location : getLocationSet()) {
			if(location.getLocName().equals(locName)) {
				cb = new CourseBoard(courseData, location);
				cb.visualize();
			}
		}
		 
	}

}
