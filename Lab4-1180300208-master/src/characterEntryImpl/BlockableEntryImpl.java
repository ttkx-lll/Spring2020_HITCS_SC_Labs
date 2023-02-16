package characterEntryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import characterEntry.BlockableEntry;
import common.element.Timeslot;

/**
 * 时间可阻塞的接口的实现类。
 * 
 * @author 梁书育
 */
public class BlockableEntryImpl implements BlockableEntry {

	private Timeslot totalTimeslot;
	private List<Timeslot> blockTimeslotList = new ArrayList<Timeslot>();
	
	// Abstraction function:
	// 	  表示可阻塞时间，由总的起始时间和阻塞起始时间构成
	
	// Representation invariant:
	//    阻塞起始时间应当在总起止时间的范围内。
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	public void checkRep() {
		assert totalTimeslot.getStartTime().isBefore(getTotalEndTime());
		for(Timeslot t : blockTimeslotList) {
			assert t.getStartTime().isBefore(t.getStartTime());
			assert t.getEndTime().isAfter(t.getEndTime());
		}
	}
	
	/**
	 * 构建可阻塞的时间状态。
	 * 
	 * @param totalTimeslot 总体的起止时间
	 */
	public BlockableEntryImpl(Timeslot totalTimeslot, List<Timeslot> blockTimeslotList) {
		this.totalTimeslot = totalTimeslot;
		this.blockTimeslotList = blockTimeslotList;
	}

	@Override
	public LocalDateTime getTotalStartTime() {
		return totalTimeslot.getStartTime();
	}

	@Override
	public LocalDateTime getTotalEndTime() {
		return totalTimeslot.getEndTime();
	}

	@Override
	public List<Timeslot> getBlockTimeslotList() {
		List<Timeslot> blockTimeslotListClone = new ArrayList<Timeslot>();
		blockTimeslotListClone.addAll(blockTimeslotList);
		return blockTimeslotListClone;
	}

}
