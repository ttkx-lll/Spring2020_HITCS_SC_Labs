package exception;

/**
 * 在取消某计划项的时候，该计划项的当前状态不允许取消。
 * 
 * @author 梁书育
 *
 */
public class EntryCancelException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntryCancelException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("该计划项的当前状态不允许取消");
	}
}
