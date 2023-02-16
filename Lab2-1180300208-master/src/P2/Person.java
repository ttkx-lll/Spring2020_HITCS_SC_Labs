package P2;

public class Person {
	
	private final String name;
	
	// Abstraction function:
	// 	  a person and his or her name.
	
	// Representation invariant:
	//    name should not be NULL
	
	// Safety from rep exposure:
	//    use final keyword;
	//    name is a immutable type.
	
	public void checkRep() {
		assert !(name == null);
		assert !name.equals("");
	}
	
	public Person(String name) {
		this.name = name;
		checkRep();
	}
	
	public String getName() {
		checkRep();
		return name;
	}
	
}
