package exception;

/**
 * 在删除某资源的时候，有尚未结束的计划项正在占用该资源
 * 
 * @author 梁书育
 *
 */
public class ResourceOccupiedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceOccupiedException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("有尚未结束的计划项正在占用该资源");
	}
}
