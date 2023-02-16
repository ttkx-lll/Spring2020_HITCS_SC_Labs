/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph<String>();
	}

	/*
	 * Testing ConcreteEdgesGraph...
	 */

	/*
	 * Testing strategy for ConcreteEdgesGraph.toString() test actually output and
	 * expected output
	 */
	@Test
	public void testConcreteEdgeGraphToString() {
		Graph<String> g = emptyInstance();
		String str = "ConcreteEdgesGraph\n";
		g.add("1");
		g.add("2");
		g.add("3");
		g.set("1", "2", 3);
		str += "Edge [source=1, target=2, weight=3]\n";
		assertEquals(str, g.toString());
		g.set("2", "1", 2);
		str += "Edge [source=2, target=1, weight=2]\n";
		g.set("1", "3", 1);
		str += "Edge [source=1, target=3, weight=1]\n";
		assertEquals(str, g.toString());
		g.set("1", "3", 0);
		str = "ConcreteEdgesGraph\n" + "Edge [source=1, target=2, weight=3]\n"
				+ "Edge [source=2, target=1, weight=2]\n";
		assertEquals(str, g.toString());
	}

	/*
	 * Testing Edge...
	 */

	/*
	 * Testing strategy for Edge test getters and toString
	 */

	// TODO tests for operations of Edge
	@Test
	public void testEdgeGetter() {
		Edge<String> e = new Edge<String>("1", "2", 3);
		assertEquals("1", e.getSource());
		assertEquals("2", e.getTarget());
		assertEquals(3, e.getWeight());
	}

	@Test
	public void testEdgeToString() {
		Edge<String> e = new Edge<String>("1", "2", 3);
		String str = "Edge [source=1, target=2, weight=3]\n";
		assertEquals(str, e.toString());
	}

}
