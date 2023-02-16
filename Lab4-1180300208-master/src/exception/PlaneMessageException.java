package exception;

/**
 * 在不同航班计划项中出现编号一样的飞机，但飞机的类型、座位数或机龄却不一致。
 * 
 * @author 梁书育
 *
 */
public class PlaneMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlaneMessageException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("在不同航班计划项中出现编号一样的飞机，但飞机的类型、座位数或机龄却不一致");
	}
}
