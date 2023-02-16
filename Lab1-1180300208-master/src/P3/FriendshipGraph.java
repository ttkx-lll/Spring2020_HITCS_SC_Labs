package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class FriendshipGraph {

	private List<Person> people = new ArrayList<Person>();
	private List<String> names = new ArrayList<String>();
	
	public List<Person> addVertex(Person person) {
		if(names.contains(person.getName())) {
			System.out.println("列表中已经包含" + person.getName() + "！");
			System.exit(0);
			
		}
		else {
			people.add(person);
			names.add(person.getName());
		}
		return people;
	}
	
	public List<Person> addEdge(Person p1, Person p2) {
		p1.addFriend(p2);
		return p1.getFriendList();
	}
	
	public int getDistance(Person p1, Person p2) {
		
		if(p1 == p2) {
			return 0;
		}
		
		Queue<Person> queue = new LinkedList<>();
		Map<Person, Integer> dis = new HashMap<>();
		Person thisPerson;
		int level;
		
		queue.offer(p1);
		dis.put(p1, 0);
		
		while(!queue.isEmpty()) {
			thisPerson = queue.poll();
			level = dis.get(thisPerson);
			for(Person p : thisPerson.getFriendList()) {
				if(!dis.containsKey(p)) {
					queue.offer(p);
					dis.put(p, level + 1);
					if(p == p2) {
						return dis.get(p2);
					}
				}
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1

	}

}
