package characterEntryImpl;

import java.time.LocalDateTime;

import characterEntry.UnblockableEntry;
import common.element.Timeslot;

/**
 * 时间不可阻塞接口的实现类。
 * 
 * @author 梁书育
 */
public class UnblockableEntryImpl implements UnblockableEntry {

	private Timeslot timeslot;

	// Abstraction function:
	// 	  表示计划项的起止时间。
	
	// Representation invariant:
	//    起始时间应当早于终止时间。
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	public void checkRep() {
		assert timeslot.getStartTime().isBefore(timeslot.getEndTime());
	}
	
	/**
	 * 构建可阻塞类型的时间状态。
	 * 
	 * @param timeslot 起止时间
	 */
	public UnblockableEntryImpl(Timeslot timeslot) {
		this.timeslot = timeslot;
		checkRep();		
	}

	@Override
	public LocalDateTime getStartTime() {
		return timeslot.getStartTime();
	}

	@Override
	public LocalDateTime getEndTime() {
		return timeslot.getEndTime();
	}

}
