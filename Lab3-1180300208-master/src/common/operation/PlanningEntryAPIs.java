package common.operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import applicationEntry.CourseEntry;
import applicationEntry.FlightEntry;
import applicationEntry.TrainEntry;
import source.Carriage;

/**
 * 计划项集合的辅助操作类，提供了检测位置、资源冲突，以及查找使用了同一资源的前置项的方法。
 * 
 * @author 梁书育
 *
 * @param <R> 使用到的资源
 */
public class PlanningEntryAPIs<R> {

	/**
	 * 检测计划项列表中是否有位置冲突的情况。
	 * 
	 * @param entries 计划项列表
	 * @return {@code true}当计划项中有位置冲突（不可共享的位置在同一时间被不同计划项占用）;
	 * 		   {@code false}当计划向中没有位置冲突
	 */
	public boolean checkLocationConflict(List<PlanningEntry<R>> entries) {
		
		if(entries.isEmpty()) return false;
		
		if(!(entries.get(0) instanceof CourseEntry)) {
			return false;
		}
		
		List<CourseEntry> courses = new ArrayList<>();
		
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
					if(qs.isBefore(ps) && qe.isAfter(ps)) {
						return true;
					}
					else if(qs.isAfter(ps) && qs.isBefore(pe)) {
						return true;
					}
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * 检测计划项列表中是否有位置冲突的情况。（策略模式的应用）
	 * 
	 * @param checkMethod 使用的检测策略
	 * @return {@code true}当计划项中有位置冲突（不可共享的位置在同一时间被不同计划项占用）;
	 * 		   {@code false}当计划向中没有位置冲突
	 */
	public boolean checkLocationConflict(CheckLocationConflictStrategy<R> checkMethod) {
		
		return checkMethod.checkLocationConflict();
		
	}
	
	/**
	 * 检测计划项列表中是否有资源冲突(同一资源被不同计划项同时使用)
	 * 
	 * @param entries 计划项列表
	 * @return {@code true}当计划项中有资源冲突; {@code false}计划项之间没有资源冲突。
	 */
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<R>> entries) {
		
		if(entries.isEmpty()) return false;
		
		if(entries.get(0) instanceof FlightEntry) {
			
			List<FlightEntry> flights = new ArrayList<>();
			
			for(PlanningEntry<R> p : entries) {
				flights.add((FlightEntry)p);
			}
			
			for(FlightEntry p : flights) {
				for(FlightEntry q : flights) {
					if(p == q) continue;
					else if(p.getResource().equals(q.getResource())) {
						LocalDateTime ps = p.getStartTime();
						LocalDateTime pe = p.getEndTime();
						LocalDateTime qs = q.getStartTime();
						LocalDateTime qe = q.getEndTime();
						if(qs.isBefore(ps) && qe.isAfter(ps)) {
							return true;
						}
						else if(qs.isAfter(ps) && qs.isBefore(pe)) {
							return true;
						}
					}
				}
			}
		}
		
		if(entries.get(0) instanceof TrainEntry) {
			
			List<TrainEntry> trains = new ArrayList<>();
			boolean hasSameElement;
			
			for(PlanningEntry<R> p : entries) {
				trains.add((TrainEntry)p);
			}
			
			for(TrainEntry p : trains) {
				for(TrainEntry q : trains) {
					hasSameElement = false;
					if(p == q) continue;
					else {
						for(Carriage c : p.getResources()) {
							if(q.getResources().contains(c)) {
								hasSameElement = true;
							}
						}
						if(hasSameElement) {
							LocalDateTime ps = p.getTotalStartTime();
							LocalDateTime pe = p.getTotalEndTime();
							LocalDateTime qs = q.getTotalStartTime();
							LocalDateTime qe = q.getTotalEndTime();
							if(qs.isBefore(ps) && qe.isAfter(ps)) {
								return true;
							}
							else if(qs.isAfter(ps) && qs.isBefore(pe)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		if(entries.get(0) instanceof CourseEntry) {
			
			List<CourseEntry> courses = new ArrayList<>();
			
			for(PlanningEntry<R> p : entries) {
				courses.add((CourseEntry)p);
			}
			
			for(CourseEntry p : courses) {
				for(CourseEntry q : courses) {
					if(p == q) continue;
					else if(p.getResource().equals(q.getResource())) {
						LocalDateTime ps = p.getStartTime();
						LocalDateTime pe = p.getEndTime();
						LocalDateTime qs = q.getStartTime();
						LocalDateTime qe = q.getEndTime();
						if(qs.isBefore(ps) && qe.isAfter(ps)) {
							return true;
						}
						else if(qs.isAfter(ps) && qs.isBefore(pe)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 提取面向特定资源的前序计划项：针对某个资源{@code r}和使用{@code r}的某个计划 项{@code e}，
	 * 从一组计划项中找出{@code e}的前序{@code f}，{@code f}也使用资源{@code r}，{@code f}的执行时间在{@code e}之前，
	 * 且在{@code e}和{@code f}之间不存在使用资源{@code r}的其他计划项。若不存在这样的计划项{@code f}，
	 * 则返回{@code null}。如果存在多个这样的{@code f}，返回其中任意一个即可
	 * 
	 * @param r 资源
	 * @param e 使用资源{@code r}的计划项
	 * @param entries 计划项列表
	 * @return 使用资源{@code r}的计划项{@code e}的前置项，如果不存在这样的前置项，则返回{@code null}
	 */
	@SuppressWarnings("unchecked")
	public PlanningEntry<R> findPreEntryPerResource(R r, 
			PlanningEntry<R> e, List<PlanningEntry<R>> entries) {
		
		if(entries.isEmpty()) return null;
		
		if(e instanceof FlightEntry) {
			
			List<FlightEntry> flights = new ArrayList<>();
			
			for(PlanningEntry<R> p : entries) {
				flights.add((FlightEntry)p);
			}
			
			for(FlightEntry p : flights) {
				if(p == e) continue;
				else if(p.getResource().equals(r) && p.getEndTime().isBefore(((FlightEntry)e).getStartTime())) {
					return (PlanningEntry<R>) p;
				}
			}
			
		}
		
		if(e instanceof TrainEntry) {
			
			List<TrainEntry> trains = new ArrayList<>();
			
			for(PlanningEntry<R> p : entries) {
				trains.add((TrainEntry)p);
			}
			
			for(TrainEntry p : trains) {
				if(p == e) continue;
				else if(p.getResources().contains(r) && p.getTotalEndTime().isBefore(((TrainEntry) e).getTotalStartTime())) {
					return (PlanningEntry<R>) p;
				}
			}
			
		}
		
		if(e instanceof CourseEntry) {

			List<CourseEntry> courses = new ArrayList<>();
			
			for(PlanningEntry<R> p : entries) {
				courses.add((CourseEntry)p);
			}
			
			for(CourseEntry p : courses) {
				if(p == e) continue;
				else if(p.getResource().equals(r) && p.getEndTime().isBefore(((CourseEntry)e).getStartTime())) {
					return (PlanningEntry<R>) p;
				}
			}
			
		}
		
		return null;
		
	}
}
