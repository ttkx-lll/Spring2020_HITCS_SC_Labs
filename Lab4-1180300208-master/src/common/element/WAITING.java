package common.element;

/**
 * WAITING状态，为创建了计划项但计划项仍在等待分配资源的状态。
 * 
 * @author 梁书育
 *
 */
public class WAITING implements EntryState {

	public static WAITING instance = new WAITING();
	private WAITING() { }
	
	@Override
	public EntryState create() {
		return instance;
	}
	@Override
	public EntryState allocate() {
		return ALLOCATED.instance;
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
		return instance;
	}
	
}