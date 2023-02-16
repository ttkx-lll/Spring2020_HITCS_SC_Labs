package common.element;

/**
 * ALLOCATED状态，为计划项资源分配后的状态。
 * 
 * @author 梁书育
 */
public class ALLOCATED implements EntryState {

	static ALLOCATED instance = new ALLOCATED();
	private ALLOCATED() { }
	
	@Override
	public EntryState create() {
		return instance;
	}
	@Override
	public EntryState allocate() {
		return instance;
	}
	@Override
	public EntryState start() {
		return RUNNING.instance;
	}
	@Override
	public EntryState cancel() {
		return CANCELLED.instance;
	}
	@Override
	public EntryState end() {
		return instance;
	}
	@Override
	public EntryState block() {
		return instance;
	}
	@Override
	public EntryState restart() {
		return instance;
	}

	
}
