package common.operation;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import applicationEntry.CourseEntry;

/**
 * 检测位置冲突的第二种策略。
 * 
 * @author 梁书育
 *
 * @param <R> 用到的资源
 */
public class SecondStrategy<R> implements CheckLocationConflictStrategy<R>{

	List<PlanningEntry<R>> entries;
	
	public SecondStrategy(List<PlanningEntry<R>> entries) {
		this.entries = entries;
	}
	
	@Override
	public boolean checkLocationConflict() {

		if(entries.isEmpty()) return false;
		
		if(!(entries.get(0) instanceof CourseEntry)) {
			return false;
		}
		
		List<CourseEntry> courses = new LinkedList<>();
		
		for(PlanningEntry<R> p : entries) {
			courses.add((CourseEntry)p);
		}
		
		for(CourseEntry p : courses) {
			for(CourseEntry q: courses) {
				if(p == q) continue;
				else if(p.getLocation().equals(q.getLocation())) {
					LocalDateTime ps = p.getStartTime();
					LocalDateTime pe = p.getEndTime();
					LocalDateTime qs = q.getStartTime();
					LocalDateTime qe = q.getEndTime();
					if(qs.isBefore(ps) && qe.isAfter(ps) || qs.isAfter(ps) && qs.isBefore(pe)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
