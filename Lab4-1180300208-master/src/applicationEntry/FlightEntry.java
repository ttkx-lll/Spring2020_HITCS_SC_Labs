package applicationEntry;

import java.time.LocalDateTime;

import characterEntryImpl.DoubleLocationEntryImpl;
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
import resource.Plane;

/**
 * 航班计划项。
 * 
 * @author 梁书育
 */
public class FlightEntry extends CommonPlanningEntry<Plane> implements FlightPlanningEntry{

	private DoubleLocationEntryImpl dle;
	private UnblockableEntryImpl ue;
	private SingularResourceEntryImpl<Plane> sre;
	
	/**
	 * 创建一个新的航班计划项。
	 * 
	 * @param name 航班名称
	 * @param dle 实现好的两个位置接口的实现类
	 * @param ue 实现好的时间不可阻塞接口的实现类
	 * @param sre 实现好的计划项单一资源接口的实现类
	 */
	public FlightEntry(String name, DoubleLocationEntryImpl dle, UnblockableEntryImpl ue, SingularResourceEntryImpl<Plane> sre) {
		super(name);
		this.dle = dle;
		this.ue = ue;
		this.sre = sre;
	}

	@Override
	public Location getStartLoc() {
		return dle.getStartLoc();
	}

	@Override
	public Location getEndLoc() {
		return dle.getEndLoc();
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
	public void setResource(Plane resource) {
		sre.setResource(resource);
		super.allocate();
	}
	
	@Override
	public Plane getResource() {
		return sre.getResource();
	}

	@Override
	public int compareTo(PlanningEntry<Plane> o) {
		return this.getStartTime().isBefore(((FlightEntry)o).getStartTime()) ? -1 : 1;
	}

	@Override
	public String getPresentStateName() {
		if(state instanceof WAITING) {
			return "未分配飞机";
		} else if(state instanceof ALLOCATED) {
			return "已分配飞机";
		} else if(state instanceof RUNNING) {
			return "飞行中";
		} else if(state instanceof ENDED) {
			return "已降落";
		} else if(state instanceof CANCELLED) {
			return "已取消";
		} else {
			return "状态出错！";
		}
	}

}
