package board;

import java.util.Collections;
import java.util.Iterator;

import applicationEntry.CourseEntry;

/**
 * 课程计划项列表。
 * 
 * @author 梁书育
 */
public class CourseData extends Data<CourseEntry> {

	@Override
	public Iterator<CourseEntry> iterator() {
		Collections.sort(dataList);
		return super.iterator();
	}

	/**
	 * 取得指定名称的课程计划项。
	 * 
	 * @param planName 课程名称
	 * @return 如果存在该课程则返回该计划项，若不存在则返回{@code null}
	 */
	public CourseEntry getPlan(String planName) {
		for(CourseEntry ce : dataList) {
			if(ce.getPlanningName().equals(planName)) {
				return ce;
			}
		}
		return null;
	}
	
}
