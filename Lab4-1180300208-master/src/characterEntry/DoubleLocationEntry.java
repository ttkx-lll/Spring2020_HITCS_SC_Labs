package characterEntry;

import common.element.Location;

/**
 * 计划项的位置属性为两个位置的接口。
 * 
 * @author 梁书育
 */
public interface DoubleLocationEntry {
	
	/**
	 * 返回起始位置。
	 * 
	 * @return 起始位置
	 */
	public Location getStartLoc();
	
	/**
	 * 返回终止位置。
	 * 
	 * @return 终止位置。
	 */
	public Location getEndLoc();
}
