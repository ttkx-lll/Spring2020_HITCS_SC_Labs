package exception;

/**
 * 第一位不为 N 或 B，后面不是四位数字。
 * 
 * @author 梁书育
 *
 */
public class PlaneIDException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlaneIDException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("飞机编号第一位不为 N 或 B或者后面不是四位数字");
	}
}
