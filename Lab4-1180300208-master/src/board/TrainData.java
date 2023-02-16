package board;

import java.util.Collections;
import java.util.Iterator;

import applicationEntry.TrainEntry;

/**
 * 列车计划项列表。
 * 
 * @author 梁书育
 */
public class TrainData extends Data<TrainEntry> {

	@Override
	public Iterator<TrainEntry> iterator() {
		Collections.sort(dataList);
		return super.iterator();
	}

	/**
	 * 取得指定名称的列车计划项。
	 * 
	 * @param planName 列车名称
	 * @return 如果存在该列车则返回该计划项，若不存在则返回{@code null}
	 */
	public TrainEntry getPlan(String planName) {
		for(TrainEntry te : dataList) {
			if(te.getPlanningName().equals(planName)) {
				return te;
			}
		}
		return null;
	}

}
