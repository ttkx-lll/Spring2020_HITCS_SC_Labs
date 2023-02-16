package board;

import java.util.Collections;
import java.util.Iterator;

import applicationEntry.FlightEntry;
import common.element.Timeslot;

/**
 * 航班计划项列表。
 * 
 * @author 梁书育
 */
public class FlightData extends Data<FlightEntry> {

	@Override
	public Iterator<FlightEntry> iterator() {
		Collections.sort(dataList);
		return super.iterator();
	}

	/**
	 * 取得指定名称、时间的航班计划项。
	 * 
	 * @param planName 航班名称
	 * @param timeslot 起始时间
	 * @return 如果存在该航班则返回该计划项，若不存在则返回{@code null}
	 */
	public FlightEntry getPlan(String planName, Timeslot timeslot) {
		for(FlightEntry fe : dataList) {
			if(fe.getPlanningName().equals(planName) 
					&& fe.getStartTime().equals(timeslot.getStartTime())
					&& fe.getEndTime().equals(timeslot.getEndTime())) {
				return fe;
			}
		}
		return null;
	}

}
