package common.element;

/**
 * 计划项的状态接口。其中的方法均为更改状态的方法。
 * 
 * @author 梁书育
 */
public interface EntryState {

	/**
	 * 创建一个计划项。
	 * 
	 * @return 满足条件的将状态设置为WAITING，不满足的保持现有状态不变
	 */
	public EntryState create();
	
	/**
	 * 为计划项分配资源。
	 * 
	 * @return 满足条件的将状态设置为ALLOCATED，不满足的保持现有状态不变
	 */
	public EntryState allocate();
	
	/**
	 * 启动计划项。
	 * 
	 * @return 满足条件的将状态设置为RUNNING，不满足的保持现有状态不变
	 */
	public EntryState start();
	
	/**
	 * 取消计划项。
	 * 
	 * @return 满足条件的将状态设置为CANECLLED，不满足的保持现有状态不变
	 */
	public EntryState cancel();
	
	/**
	 * 结束计划项。
	 * 
	 * @return 满足条件的将状态设置为ENDED，不满足的保持现有状态不变
	 */
	public EntryState end();
	
	/**
	 * 阻塞计划项。
	 * 
	 * @return 满足条件的将状态设置为BLOCKED，不满足的保持现有状态不变
	 */
	public EntryState block();
	
	/**
	 * 重新启动计划项。
	 * 
	 * @return 满足条件的将状态设置为RUNNING，不满足的保持现有状态不变
	 */
	public EntryState restart();
}
