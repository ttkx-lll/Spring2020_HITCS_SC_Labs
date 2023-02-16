package characterEntry;

import java.time.LocalDateTime;
import java.util.List;

import common.element.Timeslot;

/**
 * 时间可阻塞，一系列时间对，阻塞期间，计划是暂停的。总体和阻塞的起始时间都应当早于终止时间。
 * 
 * @author 梁书育
 */
public interface BlockableEntry {
	
	/**
	 * 返回起始时间。
	 * 
	 * @return 起始时间信息
	 */
	public LocalDateTime getTotalStartTime();
	
	/**
	 * 返回终止时间。
	 * 
	 * @return 终止时间信息
	 */
	public LocalDateTime getTotalEndTime();
	
	/**
	 * 返回阻塞时间对的列表。
	 * 
	 * @return 阻塞时间对的列表
	 */
	public List<Timeslot> getBlockTimeslotList();
}
