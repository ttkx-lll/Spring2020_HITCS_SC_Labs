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
	
	// Abstraction function:
	// 	  表示计划项发生的起止位置以及中间位置
	
	// Representation invariant:
	//    起始位置、中间位置应各不相同
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	public void checkRep() {
		assert !start.equals(end);
		for(Location l : midLocList) {
			assert !start.equals(l);
			assert !end.equals(l);
			for(Location m : midLocList) {
				if(m == l) continue;
				else assert !l.equals(m);
			}
		}
	}
	
	/**
	 * 创建有多个位置的位置模式。
	 * 
	 * @param locList 位置列表
	 */
	public MultipleLocationEntryImpl(Location start, Location end, List<Location> midLocList) {
		this.start = start;
		this.end = end;
		this.midLocList = midLocList;
		checkRep();
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
