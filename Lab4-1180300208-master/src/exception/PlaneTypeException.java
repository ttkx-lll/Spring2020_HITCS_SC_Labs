package exception;

/**
 * 机型信息中含有除大小写字母和数字以外的其他字符。
 * 
 * @author 梁书育
 *
 */
public class PlaneTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlaneTypeException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("机型信息中含有除大小写字母和数字以外的其他字符");
	}
}
