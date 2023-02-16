package exception;

/**
 * 范围为在[50,600]之外。
 *  
 * @author 梁书育
 *
 */
public class PlaneSeatNumberException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PlaneSeatNumberException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("飞机座位数在[50,600]之外");
	}
}
