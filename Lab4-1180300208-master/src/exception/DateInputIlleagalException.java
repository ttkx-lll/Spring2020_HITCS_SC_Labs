package exception;

/**
 * 输入日期格式不合法。
 * 
 * @author 梁书育
 *
 */
public class DateInputIlleagalException extends Exception {

	private static final long serialVersionUID = 1L;

	public DateInputIlleagalException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("文件中日期格式不合法");
	}
}
