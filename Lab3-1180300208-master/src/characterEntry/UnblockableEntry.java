package characterEntry;

import java.time.LocalDateTime;

/**
 * 不可阻塞时间，只有一个起始时间对，起始时间应当早于终止时间。
 * 
 * @author 梁书育
 */
public interface UnblockableEntry {
	
	/**
	 * 返回起始时间。
	 * 
	 * @return 起始时间信息
	 */
	public LocalDateTime getStartTime();
	
	/**
	 * 返回终止时间。
	 * 
	 * @return 终止时间信息
	 */
	public LocalDateTime getEndTime();
}
