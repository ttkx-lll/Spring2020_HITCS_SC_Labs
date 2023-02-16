package common.element;

/**
 * ENDED状态，为计划项运行结束的状态。
 * 
 * @author 梁书育
 *
 */
public class ENDED implements EntryState {

	static ENDED instance = new ENDED();
	private ENDED() { };
	
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
