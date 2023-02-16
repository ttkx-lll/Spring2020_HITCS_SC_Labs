package applicationEntry;

import java.time.LocalDateTime;
import java.util.List;

import characterEntryImpl.BlockableEntryImpl;
import characterEntryImpl.MultipleLocationEntryImpl;
import characterEntryImpl.MultipleSortedResourceEntryImpl;
import common.element.ALLOCATED;
import common.element.BLOCKED;
import common.element.CANCELLED;
import common.element.ENDED;
import common.element.Location;
import common.element.RUNNING;
import common.element.Timeslot;
import common.element.WAITING;
import common.operation.CommonPlanningEntry;
import common.operation.PlanningEntry;
import resource.Carriage;

/**
 * 列车计划项。
 * 
 * @author 梁书育
 */
public class TrainEntry extends CommonPlanningEntry<Carriage> implements TrainPlanningEntry {

	private MultipleLocationEntryImpl mle;
	private MultipleSortedResourceEntryImpl<Carriage> msre;
	private BlockableEntryImpl be;

	// Abstraction function:
	// 	  表示列车计划项
	
	// Representation invariant:
	//    车厢的编号之间不应相同
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型

	public void checkRep() {
		for(Carriage c : msre.getResources()) {
			for(Carriage d : msre.getResources()) {
				if(c == d) continue;
				else {
					assert !c.getCarriageID().equals(d.getCarriageID());
				}
			}
			
		}
	}
	
	/**
	 * 新建一个列车计划项。
	 * 
	 * @param name 列车名称
	 * @param mle 实现好的多个位置的接口的实现类
	 * @param msre 实现好的多个有序资源接口的实现类
	 * @param be 实现好的多个有序资源接口的实现类
	 */
	public TrainEntry(String name, MultipleLocationEntryImpl mle, MultipleSortedResourceEntryImpl<Carriage> msre,
			BlockableEntryImpl be) {
		
		super(name);
		this.mle = mle;
		this.msre = msre;
		this.be = be;
	}
	
	/**
	 * 阻塞该计划项，此时状态为已阻塞。
	 */
	public void block() {
		super.state = state.block();
	}

	/**
	 * 重新启动该计划项，此时状态为运行中。
	 */
	public void restart() {
		super.state = state.restart();
	}
	
	@Override
	public List<Location> getMidLocations() {
		return mle.getMidLocations();
	}
	
	@Override
	public Location getStartLoc() {
		return mle.getStartLoc();
	}

	@Override
	public Location getEndLoc() {
		return mle.getEndLoc();
	}
	
	@Override
	public void addResource(Carriage resource) {
		msre.addResource(resource);
		super.allocate();
		checkRep();
	}

	@Override
	public void deleteResource(Carriage resource) {
		msre.deleteResource(resource);
		checkRep();
	}
	
	@Override
	public List<Carriage> getResources() {
		return msre.getResources();
	}

	@Override
	public LocalDateTime getTotalStartTime() {
		return be.getTotalStartTime();
	}

	@Override
	public LocalDateTime getTotalEndTime() {
		return be.getTotalEndTime();
	}

	@Override
	public List<Timeslot> getBlockTimeslotList() {
		return be.getBlockTimeslotList();
	}

	@Override
	public int compareTo(PlanningEntry<Carriage> o) {
		return this.getTotalStartTime().isBefore(((TrainEntry)o).getTotalStartTime()) ? -1 : 1;
	}

	@Override
	public String getPresentStateName() {

		if(state instanceof WAITING) {
			return "未分配车厢";
		} else if(state instanceof ALLOCATED) {
			return "已分配车厢";
		} else if(state instanceof RUNNING) {
			return "列车运行中";
		} else if(state instanceof BLOCKED) {
			return "停靠中间车站";
		} else if(state instanceof ENDED) {
			return "抵达终点站";
		} else if(state instanceof CANCELLED) {
			return "已取消";
		} else {
			return "状态出错！";
		}
	}

}
