package exception;

/**
 * 最多1位小数但使用了2位小数，范围不在[0, 30]内。
 * 
 * @author 梁书育
 *
 */
public class PlaneAgeException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlaneAgeException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("机龄数据使用了2位及以上小数或者范围不在[0, 30]内");
	}
}
