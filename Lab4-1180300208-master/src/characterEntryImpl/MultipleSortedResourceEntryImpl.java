package characterEntryImpl;

import java.util.ArrayList;
import java.util.List;

import characterEntry.MultipleSortedResourceEntry;

/**
 * 多个有序资源接口的实现类。
 * 
 * @author 梁书育
 *
 * @param <R> 资源
 */
public class MultipleSortedResourceEntryImpl<R> implements MultipleSortedResourceEntry<R> {

	private List<R> resourcesList = new ArrayList<>();
	
	// Abstraction function:
	// 	  多个可排序的资源。
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	/**
	 * 创建有多个可排序资源的资源模式（创建前分配资源）
	 * 
	 * @param resourcesList 可排序的资源列表
	 */
	public MultipleSortedResourceEntryImpl(List<R> resourcesList) {
		this.resourcesList = resourcesList;
	}
	
	/**
	 * 创建有多个可排序资源的资源模式（创建后分配资源）
	 */
	public MultipleSortedResourceEntryImpl() { }
	
	@Override
	public List<R> getResources() {
		List<R> resourcesListClone = new ArrayList<>();
		resourcesListClone.addAll(resourcesList);
		return resourcesListClone;
	}

	@Override
	public void addResource(R resource) {
		if(!resourcesList.contains(resource)) {
			resourcesList.add(resource);
		}
	}

	@Override
	public void deleteResource(R resource) {
		if(resourcesList.contains(resource)) {
			resourcesList.remove(resource);
		}
	}
	
}
