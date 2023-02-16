package exception;

/**
 * 同一个航班号，虽然日期不同，但其出发或到达机场、 出发或到达时间有差异
 * 
 * @author 梁书育
 *
 */
public class FlightMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public FlightMessageException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("同一个航班号，日期不同，但其出发或到达机场、出发或到达时间有差异");
	}
}
