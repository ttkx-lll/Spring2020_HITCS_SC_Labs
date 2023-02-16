package common.element;

/**
 * CANCELLED状态，为计划项被取消的状态。
 * 
 * @author 梁书育
 */
public class CANCELLED implements EntryState {

	static CANCELLED instance = new CANCELLED();
	private CANCELLED() { };
	
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
