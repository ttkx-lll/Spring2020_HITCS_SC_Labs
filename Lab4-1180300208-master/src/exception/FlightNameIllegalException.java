package exception;

/**
 * 航班名称要求2位大写字母和2-4位数字但却使用了非大写字母或小于2位或超过4位数字
 * @author 梁书育
 *
 */
public class FlightNameIllegalException extends Exception {

	private static final long serialVersionUID = 1L;

	public FlightNameIllegalException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("航班名称使用了非大写字母或数字数小于2位或超过4位");
	}
}
