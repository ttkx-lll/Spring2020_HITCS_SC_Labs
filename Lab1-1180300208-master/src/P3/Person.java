package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String name;
	private List<Person> friends = new ArrayList<Person>();
	
	public Person(String name) {
		this.name = name;
	}
	
	public void addFriend(Person friend) {
		if(friends.contains(friend)) {
			System.out.println(name + "已经添加过该朋友！");
		}
		else {
			friends.add(friend);
		}
	}

	public String getName() {
		return name;
	}
	
	public List<Person> getFriendList(){
		return friends;
	}
	
}
