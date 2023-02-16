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
	
	/**
	 * 构建可阻塞类型的时间状态。
	 * 
	 * @param timeslot 起止时间
	 */
	public UnblockableEntryImpl(Timeslot timeslot) {
		this.timeslot = timeslot;
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
