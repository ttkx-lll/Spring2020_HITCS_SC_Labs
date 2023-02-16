package exception;

/**
 * 输入的时间格式不合法。
 * 
 * @author 梁书育
 *
 */
public class TimeInputIlleagalException extends Exception {

	private static final long serialVersionUID = 1L;

	public TimeInputIlleagalException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("文件中时间格式不合法");
	}
}
