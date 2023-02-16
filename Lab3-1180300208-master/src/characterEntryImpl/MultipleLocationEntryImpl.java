package characterEntryImpl;

import java.util.ArrayList;
import java.util.List;

import characterEntry.MultipleLocationEntry;
import common.element.Location;

/**
 * 多个位置的接口的实现类。
 * 
 * @author 梁书育
 */
public class MultipleLocationEntryImpl implements MultipleLocationEntry {

	private List<Location> midLocList;
	private Location start;
	private Location end;
	
	/**
	 * 创建有多个位置的位置模式。
	 * 
	 * @param locList 位置列表
	 */
	public MultipleLocationEntryImpl(Location start, Location end, List<Location> midLocList) {
		this.start = start;
		this.end = end;
		this.midLocList = midLocList;
	}
	
	@Override
	public List<Location> getMidLocations() {
		List<Location> locListClone = new ArrayList<>();
		locListClone.addAll(midLocList);
		return locListClone;
	}

	@Override
	public Location getStartLoc() {
		return start;
	}

	@Override
	public Location getEndLoc() {
		return end;
	}

}
