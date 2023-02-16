package exception;

/**
 * 存在多个航班计划项的“日期,航班号”信息完全一样的情况。
 * 
 * @author 梁书育
 *
 */
public class SameLabelException extends Exception {

	private static final long serialVersionUID = 1L;

	public SameLabelException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("存在多个航班计划项的“日期,航班号”信息完全一样的情况");
	}
}
