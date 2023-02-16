package app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.element.Location;
import common.operation.PlanningEntry;

/**
 * 计划项app的共性操作。
 * 
 * @author 梁书育
 *
 * @param <R> 用到的资源
 */
public abstract class ScheduleOperation<R> {

	private Set<R> resourceSet = new HashSet<>();
	private Set<Location> locationSet = new HashSet<>();
	
	// Abstraction function:
	// 	  所有计划项app的共有操作
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    返回的set类型均进行了防御式拷贝。
	
	/**
	 * 添加资源。
	 * 
	 * @param resource 要添加的资源
	 */
	public boolean addResource(R resource) {
		
		if(!resourceSet.contains(resource)) {
			resourceSet.add(resource);
			return true;
		}
		return false;
	}
	
	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(R resource) {
		
		if(resourceSet.contains(resource)) {
			resourceSet.remove(resource);
			return true;
		}
		return false;
	}
	
	/**
	 * 取得资源列表。
	 * 
	 * @return 资源列表
	 */
	public Set<R> getResourceSet() {
		Set<R> resourceSetClone = new HashSet<>();
		resourceSetClone.addAll(resourceSet);
		return resourceSetClone;
	}
	
	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		
		if(!locationSet.contains(loc)) {
			locationSet.add(loc);
			return true;
		}
		return false;
		
	}
	
	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		
		if(locationSet.contains(loc)) {
			locationSet.remove(loc);
			return true;
		}
		return false;
	}
	
	/**
	 * 取得位置列表。
	 * 
	 * @return 位置列表
	 */
	public Set<Location> getLocationSet(){
		Set<Location> locationSetClone = new HashSet<>();
		locationSetClone.addAll(locationSet);
		return locationSetClone;
	}
	
	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public abstract boolean checkLocationAndResourseConflict();
	
	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public abstract List<PlanningEntry<R>> getAllPlanWithResource(R resource);
	
	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public abstract void viewBoardOfLoc(String locName);
}
