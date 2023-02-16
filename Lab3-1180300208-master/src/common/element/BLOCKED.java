package common.element;

/**
 * BLOCKED状态，为计划项被阻塞的状态。
 * 
 * @author 梁书育
 */
public class BLOCKED implements EntryState {

	static BLOCKED instance = new BLOCKED();
	private BLOCKED() { }

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
		return instance;
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
		return RUNNING.instance;
	}
}
