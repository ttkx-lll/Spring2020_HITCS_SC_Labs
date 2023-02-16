package common.operation;

/**
 * 检测计划项列表中是否有地点冲突的策略接口。
 * 
 * @author 梁书育
 *
 * @param <R> 用到的资源
 */
public interface CheckLocationConflictStrategy<R> {

	/**
	 * 检测计划项列表中是否有位置冲突。
	 * 
	 * @return {@code true}当计划项中有位置冲突（不可共享的位置在同一时间被不同计划项占用）;
	 * 		   {@code false}当计划向中没有位置冲突
	 */
	public boolean checkLocationConflict();
}
