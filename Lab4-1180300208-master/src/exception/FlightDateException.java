package exception;

/**
 * 第一行出现的航班日期与内部出现的起飞时间中的日期不一致
 * 
 * @author 梁书育
 *
 */
public class FlightDateException extends Exception {

	private static final long serialVersionUID = 1L;

	public FlightDateException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("第一行出现的航班日期与内部出现的起飞时间中的日期不一致");
	}
}
