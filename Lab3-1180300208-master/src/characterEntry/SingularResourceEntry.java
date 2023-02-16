package characterEntry;

/**
 * 计划项的资源单一的接口。
 * 
 * @author 梁书育
 */
public interface SingularResourceEntry<R> {
	
	/**
	 * 设置资源。
	 * 
	 * @param resource 被分配到该计划项的资源
	 */
	public void setResource(R resource);
	
	/**
	 * 获得所属计划项的资源。
	 * 
	 * @return 该计划项的资源
	 */
	public R getResource();
}
