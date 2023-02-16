package common.element;

/**
 * RUNNING状态，为计划项在运行的状态。
 * 
 * @author 梁书育
 *
 */
public class RUNNING implements EntryState {

	static RUNNING instance = new RUNNING();
	private RUNNING() { }
	
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
		return instance;
	}
	@Override
	public EntryState end() {
		return ENDED.instance;
	}
	@Override
	public EntryState block() {
		return BLOCKED.instance;
	}
	@Override
	public EntryState restart() {
		return instance;
	}
}
