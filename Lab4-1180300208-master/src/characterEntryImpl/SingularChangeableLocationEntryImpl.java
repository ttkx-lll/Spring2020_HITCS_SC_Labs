package characterEntryImpl;

import characterEntry.ChangeableLocationEntry;
import characterEntry.SingularLocationEntry;
import common.element.Location;

/**
 * 单一可更换位置接口的实现类。
 * 
 * @author 梁书育
 */
public class SingularChangeableLocationEntryImpl implements SingularLocationEntry, ChangeableLocationEntry {

	private Location loc;

	// Abstraction function:
	// 	  表示计划项发生的单一位置
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	/**
	 * 可改变位置的单一位置的位置模式。
	 * 
	 * @param loc 计划项的位置
	 */
	public SingularChangeableLocationEntryImpl(Location loc) {
		this.loc = loc;
	}
	
	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public void changeLocation(Location postLoc) {
		this.loc = postLoc;
	}

}
