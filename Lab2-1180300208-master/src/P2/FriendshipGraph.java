package P2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.*;


public class FriendshipGraph {

	private final Graph<Person> people = Graph.empty();
	
	// Abstraction function:
	// 	  a graph of friendship
	
	// Representation invariant:
	//    people should not be NULL
	
	// Safety from rep exposure:
	//    use final keyword;
	//    use defensive copying.
	
	public void checkRep() {
		assert !(people == null);
	}
	
	public Set<Person> addVertex(Person person) {
		Set<Person> list = new HashSet<Person>();
		for(Person p : people.vertices()) {
			if(p.getName().equals(person.getName())) {
				System.out.println("列表中已经包含" + person.getName() + "!");
				System.exit(0);
			}
		}
		people.add(person);
		list.addAll(people.vertices());
		checkRep();
		return list;
	}
	
	public Set<Person> addEdge(Person p1, Person p2) {
		Set<Person> list = new HashSet<Person>();
		people.set(p1, p2, 1);
		list.addAll(people.targets(p1).keySet());
		checkRep();
		return list;
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
			for(Map.Entry<Person, Integer> p : people.targets(thisPerson).entrySet()) {
				if(!dis.containsKey(p.getKey())) {
					queue.offer(p.getKey());
					dis.put(p.getKey(), level + 1);
					if(p.getKey() == p2) {
						checkRep();
						return dis.get(p2);
					}
				}
			}
		}

		checkRep();
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
