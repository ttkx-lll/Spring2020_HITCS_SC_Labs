package exception;

/**
 * 降落时间中的日期与航班日期差距大于 1 天。
 * 
 * @author 梁书育
 *
 */
public class OverOneDayException extends Exception {

	private static final long serialVersionUID = 1L;

	public OverOneDayException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("降落时间中的日期与航班日期差距大于 1 天");
	}
}
