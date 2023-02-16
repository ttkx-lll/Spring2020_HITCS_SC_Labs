package exception;

/**
 * 在为某计划项分配某资源的时候，分配后会导致与已有的其他计划 项产生“资源独占冲突”
 *  
 * @author 梁书育
 *
 */
public class ResourceConflictException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceConflictException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("资源独占冲突");
	}
}
