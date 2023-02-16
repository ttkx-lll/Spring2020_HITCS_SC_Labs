package common.operation;

import common.element.EntryState;
import common.element.WAITING;

/**
 * 计划项共性接口的实现类，实现了计划项中公用的方法。
 * 
 * @author 梁书育
 *
 * @param <R> 计划项用到的资源
 */
public abstract class CommonPlanningEntry<R> implements PlanningEntry<R> {

	protected EntryState state = WAITING.instance;
	private String name;
	
	// Abstraction function:
	// 	  一个计划项的基本操作的实现类。
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    使用了不可变类型的变量。
	
	/**
	 * 通过计划项名字创建一个计划项，并将
	 * 
	 * @param name
	 */
	public CommonPlanningEntry(String name) {
		this.state = state.create();
		this.name = name;
	}
	
	@Override
	public void allocate() {
		this.state = state.allocate();
	}
	
	@Override
	public void start() {
		this.state = state.start();
	}

	@Override
	public void cancel() {
		this.state = state.cancel();
	}

	@Override
	public void end() {
		this.state = state.end();
	}
	
	@Override
	public String getPlanningName() {
		return name;
	}

	@Override
	public abstract String getPresentStateName();

	@Override
	public abstract int compareTo(PlanningEntry<R> o);

}
