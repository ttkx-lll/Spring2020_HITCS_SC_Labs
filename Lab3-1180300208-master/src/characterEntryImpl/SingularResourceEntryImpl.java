package characterEntryImpl;

import characterEntry.SingularResourceEntry;

/**
 * 计划项单一资源接口的实现类。
 * 
 * @author 梁书育
 *
 * @param <R> 资源
 */
public class SingularResourceEntryImpl<R> implements SingularResourceEntry<R> {

	private R resource;
	
	/**
	 * 创建单一资源的资源模式（创建时分配资源）
	 * 
	 * @param resource 该计划项的资源
	 */
	public SingularResourceEntryImpl(R resource) {
		this.resource = resource;
	}
	
	/**
	 * 创建单一资源的资源模式（创建后分配资源）
	 */
	public SingularResourceEntryImpl() {	}
	
	@Override
	public R getResource() {
		return resource;
	}

	@Override
	public void setResource(R resource) {
		this.resource = resource;
	}

}
