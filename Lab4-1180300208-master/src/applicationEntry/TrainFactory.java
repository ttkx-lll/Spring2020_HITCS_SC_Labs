package applicationEntry;

import java.util.List;

import characterEntryImpl.BlockableEntryImpl;
import characterEntryImpl.MultipleLocationEntryImpl;
import characterEntryImpl.MultipleSortedResourceEntryImpl;
import common.element.Location;
import common.element.Timeslot;
import resource.Carriage;

/**
 * 实现TrainEntry的工厂类。
 * 
 * @author 梁书育
 */
public class TrainFactory {

	/**
	 * 新建一个列车计划项。
	 * 
	 * @param name 列车名称
	 * @param mle 实现好的多个位置的接口的实现类
	 * @param msre 实现好的多个有序资源接口的实现类
	 * @param be 实现好的多个有序资源接口的实现类
	 * @return 新的火车计划项
	 */
	public TrainEntry getEntry(String name, MultipleLocationEntryImpl mle, MultipleSortedResourceEntryImpl<Carriage> msre,
			BlockableEntryImpl be) {
		return new TrainEntry(name, mle, msre, be);
	}
	
	/**
	 * 新建一个列车计划项。
	 * 
	 * @param name 列车名称
	 * @param start 始发站
	 * @param end 终点站
	 * @param midLocList 中间经停车站列表
	 * @param totalTimeslot 总出发终止时间
	 * @param blockTimeslotList 经停起始时间
	 * @return 新的火车计划项
	 */
	public TrainEntry getEntry(String name, Location start, Location end, List<Location> midLocList, 
			Timeslot totalTimeslot, List<Timeslot> blockTimeslotList) {
		MultipleLocationEntryImpl mle = new MultipleLocationEntryImpl(start, end, midLocList);
		MultipleSortedResourceEntryImpl<Carriage> msre = new MultipleSortedResourceEntryImpl<Carriage>();
		BlockableEntryImpl be = new BlockableEntryImpl(totalTimeslot, blockTimeslotList);
		return new TrainEntry(name, mle, msre, be);
	}
}
