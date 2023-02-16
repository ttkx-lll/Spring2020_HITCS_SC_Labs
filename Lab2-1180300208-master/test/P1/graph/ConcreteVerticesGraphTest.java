/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph<>();
	}

	/*
	 * Testing ConcreteVerticesGraph...
	 */

	/*
	 * Testing strategy for ConcreteVerticesGraph.toString() test actually output
	 * and expected output
	 */
	@Test
	public void testConcreteEdgeGraphToString() {
		Graph<String> g = emptyInstance();
		String str = "";
		g.add("1");
		g.add("2");
		g.add("3");

		str = "ConcreteVerticesGraph\n" + "Vertex name=1\nVertex name=2\nVertex name=3\n";
		assertEquals(str, g.toString());

		g.set("1", "2", 3);
		str = "ConcreteVerticesGraph\n" + "Vertex name=1\n       target=2, weight=3\n"
				+ "Vertex name=2\n       source=1, weight=3\nVertex name=3\n";
		assertEquals(str, g.toString());

		g.set("2", "1", 2);
		str = "ConcreteVerticesGraph\n" + "Vertex name=1\n       source=2, weight=2\n       target=2, weight=3\n"
				+ "Vertex name=2\n       source=1, weight=3\n       target=1, weight=2\n" + "Vertex name=3\n";
		assertEquals(str, g.toString());

		g.set("1", "3", 1);
		str = "ConcreteVerticesGraph\n"
				+ "Vertex name=1\n       source=2, weight=2\n       target=2, weight=3\n       target=3, weight=1\n"
				+ "Vertex name=2\n       source=1, weight=3\n       target=1, weight=2\n"
				+ "Vertex name=3\n       source=1, weight=1\n";
		assertEquals(str, g.toString());

		g.set("1", "3", 0);
		str = "ConcreteVerticesGraph\n" + "Vertex name=1\n       source=2, weight=2\n       target=2, weight=3\n"
				+ "Vertex name=2\n       source=1, weight=3\n       target=1, weight=2\n" + "Vertex name=3\n";
		assertEquals(str, g.toString());
	}

	/*
	 * Testing Vertex...
	 */

	/*
	 * Testing strategy for Vertex test getters and toString
	 */
	@Test
	public void testVertexGetter() {
		Vertex<String> v = new Vertex<>("1");
		Map<String, Integer> source = new HashMap<String, Integer>();
		Map<String, Integer> target = new HashMap<String, Integer>();
		Map<String, Integer> setSource;
		Map<String, Integer> setTarget;

		assertEquals("1", v.getName());
		assertEquals(source, v.getSources());
		assertEquals(target, v.getTargets());

		setSource = v.getSources(); setSource.put("2", 12); v.setSources(setSource);
		setTarget = v.getTargets(); setTarget.put("3", 14); v.setTargets(setTarget);
		
		target.put("3", 14);
		source.put("2", 12);
		
		assertEquals(source, v.getSources());
		assertEquals(target, v.getTargets());
		
		setTarget = v.getTargets(); setTarget.remove("3", 14); v.setTargets(setTarget);
		target.remove("3");
		assertEquals(target, v.getTargets());

	}

	@Test
	public void testVertexToString() {
		Vertex<String> v = new Vertex<>("1");
		Map<String, Integer> setSource;
		Map<String, Integer> setTarget;
		
		String str = "Vertex name=1\n";
		assertEquals(str, v.toString());
		
		setSource = v.getSources(); setSource.put("2", 12); v.setSources(setSource);
		setTarget = v.getTargets(); setTarget.put("3", 14); v.setTargets(setTarget);
		
		str = "Vertex name=1\n       source=2, weight=12\n       target=3, weight=14\n";
		assertEquals(str, v.toString());
	}
}
