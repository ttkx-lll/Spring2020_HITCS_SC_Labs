package common.element;

import java.time.LocalDateTime;

/**
 * 不可变类型，表示一个起止时间对。
 * 
 * @author 梁书育
 */
public class Timeslot {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	// Abstraction function:
	// 	  一个时间对，包含起始时间和终止时间
	
	// Representation invariant:
	//    起始时间一定要在终止时间之前
	
	// Safety from rep exposure:
	//    使用的变量均为不可变类型
	
	public void checkRep() {
		assert startTime.isBefore(endTime);
	}
	
	/**
	 * 创建一个起止时间对。
	 * 
	 * @param y1 起始年
	 * @param mon1 起始月
	 * @param d1 起始日
	 * @param h1 起始小时
	 * @param min1 起始分钟
	 * @param y2 终止年
	 * @param mon2 终止月
	 * @param d2 终止日
	 * @param h2 终止小时
	 * @param min2 终止分钟
	 */
	public Timeslot(int y1, int mon1, int d1, int h1, int min1, int y2, int mon2, int d2, int h2, int min2) {
		startTime = LocalDateTime.of(y1, mon1, d1, h1, min1);
		endTime = LocalDateTime.of(y2, mon2, d2, h2, min2);
		checkRep();
	}

	/**
	 * 用两个{@code LocalDateTime}的数据创建Timeslot
	 * 
	 * @param start 起始时间
	 * @param end 终止时间
	 */
	public Timeslot(LocalDateTime start, LocalDateTime end) {
		this.startTime = start;
		this.endTime = end;
		checkRep();
	}
	
	/**
	 * 获得起始时间
	 * 
	 * @return 起始时间
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	/**
	 * 获得终止时间
	 * 
	 * @return 终止时间
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timeslot other = (Timeslot) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	
	
}
