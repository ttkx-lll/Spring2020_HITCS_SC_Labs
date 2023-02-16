package characterEntryImpl;


import characterEntry.DoubleLocationEntry;
import common.element.Location;

/**
 * 两个位置接口的实现类。
 * 
 * @author 梁书育
 */
public class DoubleLocationEntryImpl implements DoubleLocationEntry {

	private Location start;
	private Location end;
	
	// Abstraction function:
	// 	  两个位置分别表示起始位置和终止位置
	
	// Representation invariant:
	//    起始位置和终止位置应不相同
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型	
	
	public void checkRep() {
		assert !start.equals(end);
	}
	
	/**
	 * 创建有起止两个位置的位置模式。
	 * 
	 * @param start 起始位置
	 * @param end   终止位置
	 */
	public DoubleLocationEntryImpl(Location start, Location end) {
		this.start = start;
		this.end = end;
		checkRep();
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
