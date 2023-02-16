package exception;

/**
 * 在删除某位置的时候，有尚未结束的计划项会在该位置执行
 * 
 * @author 梁书育
 *
 */
public class LocationOccupiedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationOccupiedException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("有尚未结束的计划项在该位置执行");
	}
}
