package characterEntry;

import java.util.List;

/**
 * 计划项的资源有多个且多个资源有序的接口。
 * 
 * @author 梁书育
 */
public interface MultipleSortedResourceEntry<R> {

	/**
	 * 添加资源项。
	 * 
	 * @param resource 要添加的资源
	 */
	public void addResource(R resource);
	
	/**
	 * 删除资源项。
	 * 
	 * @param resource 要删除的资源
	 */
	public void deleteResource(R resource);
	
	/**
	 * 返回该计划项的资源列表。
	 * 
	 * @return 该计划项的资源列表
	 */
	public List<R> getResources();
}
