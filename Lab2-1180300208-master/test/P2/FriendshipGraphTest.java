package P2;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;


public class FriendshipGraphTest {

	/* Testing strategy
     * test the friendship graph according to common operation logic
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void addVertexTest() {
		
		FriendshipGraph graph = new FriendshipGraph();
		Set<Person> peopleList = new HashSet<>();
		
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		
		peopleList.add(rachel);
		assertEquals(peopleList, graph.addVertex(rachel));
		
		peopleList.add(ross);
		assertEquals(peopleList, graph.addVertex(ross));
		
		peopleList.add(ben);
		assertEquals(peopleList, graph.addVertex(ben));
		
		peopleList.add(kramer);
		assertEquals(peopleList, graph.addVertex(kramer));
	}
	
	@Test
	public void addEdgeTest() {
		
		FriendshipGraph graph = new FriendshipGraph();
		Set<Person> friendList = new HashSet<>();
		
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		
		friendList.add(rachel);
		assertEquals(friendList, graph.addEdge(ross, rachel));
		
		friendList.add(ben);
		assertEquals(friendList, graph.addEdge(ross, ben));
		
		friendList.clear();
		friendList.add(ross);
		assertEquals(friendList, graph.addEdge(ben, ross));
		
		friendList.clear();
		friendList.add(ross);
		assertEquals(friendList, graph.addEdge(rachel, ross));
		
		
	}
	
	@Test
	public void getDistanceTest() {
		
		FriendshipGraph graph = new FriendshipGraph();
		
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		
		assertEquals(-1, graph.getDistance(rachel, ross));
		assertEquals(-1, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
		
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		assertEquals(-1, graph.getDistance(rachel, ross));
		assertEquals(-1, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
		
		graph.addEdge(rachel, ross);
		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
		
		graph.addEdge(ben, kramer);
		graph.addEdge(kramer, ben);
		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(3, graph.getDistance(rachel, kramer));
	}

}
