package characterEntry;

import common.element.Location;

/**
 * 计划项位置可变的接口。
 * 
 * @author 梁书育
 */
public interface ChangeableLocationEntry {

	/**
	 * 将一个位置改为另一个位置。
	 * 
	 * @param postLoc 更改后的位置信息
	 * @return 更改成功返回{@code true}，否则返回{@code false}
	 */
	public void changeLocation(Location Loc);
}
