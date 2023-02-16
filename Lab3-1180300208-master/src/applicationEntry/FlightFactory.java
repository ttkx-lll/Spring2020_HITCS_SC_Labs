package applicationEntry;

import characterEntryImpl.DoubleLocationEntryImpl;
import characterEntryImpl.SingularResourceEntryImpl;
import characterEntryImpl.UnblockableEntryImpl;
import common.element.Location;
import common.element.Timeslot;
import source.Plane;

/**
 * 实现FlightEntry的工厂类。
 * 
 * @author 梁书育
 */
public class FlightFactory {

	/**
	 * 通过给定一定的条件实现FlightEntry。
	 * 
	 * @param name 航班名称
	 * @param dle 实现好的两个位置接口的实现类
	 * @param ue 实现好的时间不可阻塞接口的实现类
	 * @param sre 实现好的计划项单一资源接口的实现类
	 * @return 新的航班计划项
	 */
	public FlightEntry getEntry(String name, DoubleLocationEntryImpl dle, UnblockableEntryImpl ue, SingularResourceEntryImpl<Plane> sre) {
		return new FlightEntry(name, dle, ue, sre);
	}
	
	/**
	 * 通过给定一定的条件实现FlightEntry。
	 * 
	 * @param name 航班名称
	 * @param start 起飞地点
	 * @param end 降落地点
	 * @param timeslot 计划起止时间
	 * @return 新的航班计划项
	 */
	public FlightEntry getEntry(String name, Location start, Location end, Timeslot timeslot) {
		DoubleLocationEntryImpl dle = new DoubleLocationEntryImpl(start, end);
		UnblockableEntryImpl ue = new UnblockableEntryImpl(timeslot);
		SingularResourceEntryImpl<Plane> sre = new SingularResourceEntryImpl<Plane>();
		return new FlightEntry(name, dle, ue, sre);
	}
}
