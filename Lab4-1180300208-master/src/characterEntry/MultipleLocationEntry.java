package characterEntry;

import java.util.List;

import common.element.Location;

/**
 * 计划项的位置属性为多个位置的接口。
 * 
 * @author 梁书育
 */
public interface MultipleLocationEntry {
	
	/**
	 * 返回该计划项的中间位置列表
	 * 
	 * @return 该计划项的中间位置列表
	 */
	public List<Location> getMidLocations();
	
	/**
	 * 返回该计划项的起点
	 * 
	 * @return 该计划项的起点
	 */
	public Location getStartLoc();
	
	/**
	 * 返回该计划项的终点
	 * 
	 * @return 该计划项的终点
	 */
	public Location getEndLoc();
}
