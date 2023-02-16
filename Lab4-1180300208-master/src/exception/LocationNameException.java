package exception;

/**
 * 位置名称不合法，出现了非字母。
 * 
 * @author 梁书育
 *
 */
public class LocationNameException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationNameException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("位置名称出现非字母");
	}
}
