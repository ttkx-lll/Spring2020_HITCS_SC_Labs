package common.operation;

/**
 * 一个计划项的共性接口，利用特定资源{@code R}，在特定地点/位置开展的一项任务
 * 
 * @author 梁书育
 *
 * @param <R> 计划中用到的资源
 */
public interface PlanningEntry<R> extends Comparable<PlanningEntry<R>>{

	/**
	 * 为计划项分配资源。
	 */
	public void allocate();
	
	/**
	 * 启动该计划项，此时状态为已启动
	 */
	public void start();

	/**
	 * 取消该计划项，此时状态为已取消
	 */
	public void cancel();
	
	/**
	 * 计划项完成，此时状态为已完成
	 */
	public void end();

	/**
	 * 返回该计划的名称
	 * 
	 * @return 该计划的名称
	 */
	public String getPlanningName();

	/**
	 * 得到该计划项的状态的名称
	 * 
	 * @return 返回该计划项的状态的名称
	 */
	public String getPresentStateName();

}
