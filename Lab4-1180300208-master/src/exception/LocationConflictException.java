package exception;

/**
 * 在为某计划项变更位置的时候，变更后会导致与已有的其他计划项 产生“位置独占冲突”。
 *  
 * @author 梁书育
 *
 */
public class LocationConflictException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LocationConflictException() {
		super();
	}
	
	public void printMessage() {
		System.out.println("位置独占冲突");
	}
}
