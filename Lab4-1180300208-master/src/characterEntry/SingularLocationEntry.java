package characterEntry;

import common.element.Location;

/**
 * 计划项的位置属性为一个位置的接口。
 * 
 * @author 梁书育
 */
public interface SingularLocationEntry {
	
	/**
	 * 获得该计划项的位置。
	 * 
	 * @return 该计划项的位置。
	 */
	public Location getLocation();
}
