package applicationEntry;

import java.time.LocalDateTime;

import characterEntryImpl.SingularChangeableLocationEntryImpl;
import characterEntryImpl.SingularResourceEntryImpl;
import characterEntryImpl.UnblockableEntryImpl;
import common.element.ALLOCATED;
import common.element.CANCELLED;
import common.element.ENDED;
import common.element.Location;
import common.element.RUNNING;
import common.element.WAITING;
import common.operation.CommonPlanningEntry;
import common.operation.PlanningEntry;
import source.Teacher;

/**
 * 课程计划项。
 * 
 * @author 梁书育
 */
public class CourseEntry extends CommonPlanningEntry<Teacher> implements CoursePlanningEntry {

	private SingularChangeableLocationEntryImpl scle;
	private SingularResourceEntryImpl<Teacher> sre;
	private UnblockableEntryImpl ue;
	
	/**
	 * 新建一个课程计划项。
	 * 
	 * @param name 课程名称
	 * @param scle 实现好的单一可更换位置接口的实现类
	 * @param sre 实现好的计划项单一资源接口的实现类
	 * @param ue 实现好的时间不可阻塞接口的实现类
	 */
	public CourseEntry(String name, SingularChangeableLocationEntryImpl scle, SingularResourceEntryImpl<Teacher> sre,
			UnblockableEntryImpl ue) {
		super(name);
		this.scle = scle;
		this.sre = sre;
		this.ue = ue;
	}
	
	@Override
	public Location getLocation() {
		return scle.getLocation();
	}

	@Override
	public void changeLocation(Location loc) {
		scle.changeLocation(loc);
	}

	@Override
	public void setResource(Teacher resource) {
		sre.setResource(resource);
		super.allocate();
	}
	
	@Override
	public Teacher getResource() {
		return sre.getResource();
	}

	@Override
	public LocalDateTime getStartTime() {
		return ue.getStartTime();
	}

	@Override
	public LocalDateTime getEndTime() {
		return ue.getEndTime();
	}

	@Override
	public int compareTo(PlanningEntry<Teacher> o) {
		return this.getStartTime().isBefore(((CourseEntry)o).getStartTime()) ? -1 : 1;
	}

	@Override
	public String getPresentStateName() {
		if(state instanceof WAITING) {
			return "未安排教师";
		} else if(state instanceof ALLOCATED) {
			return "已安排教师";
		} else if(state instanceof RUNNING) {
			return "上课中";
		} else if(state instanceof ENDED) {
			return "已下课";
		} else if(state instanceof CANCELLED) {
			return "已取消";
		} else {
			return "状态出错！";
		}
	}

}
