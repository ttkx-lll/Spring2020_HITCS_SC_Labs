/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	/*
	 * Testing strategy
	 * 
	 * Partition the inputs as follows: vertex: none, the same name or not source:
	 * exist or not target: exist or not weight: 0, > 0
	 */

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// TODO you may use, change, or remove this test
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// TODO other tests for instance methods of Graph

	// covers vertex none, the same name or not
	@Test
	public void testAdd() {
		Set<String> vertices = new HashSet<String>();
		Graph<String> g = emptyInstance();
		assertEquals(vertices, g.vertices());
		vertices.add("1");
		g.add("1");
		assertEquals(vertices, g.vertices());
		g.add("1");
		assertEquals(vertices, g.vertices());
		vertices.add("2");
		g.add("2");
		assertEquals(vertices, g.vertices());
	}

	// covers source exist or not
	// target exist or not
	// weight 0, > 0
	@Test
	public void testSet() {
		Graph<String> g = emptyInstance();
		g.add("1");
		g.add("2");
		assertEquals(0, g.set("1", "2", 0));
		assertEquals(0, g.set("1", "2", 1));
		assertEquals(1, g.set("1", "2", 3));
		assertEquals(3, g.set("1", "2", 0));
		assertEquals(0, g.set("1", "3", 5));
	}

	// covers vertex exist or not
	@Test
	public void testRemove() {
		Graph<String> g = emptyInstance();
		Set<String> vertices = new HashSet<String>();
		g.add("1");
		g.add("2");
		g.add("3");
		vertices.add("1");
		vertices.add("2");
		vertices.add("3");
		g.set("1", "2", 3);
		g.set("2", "1", 2);
		g.set("1", "3", 1);
		assertEquals(false, g.remove("4"));
		assertEquals(vertices, g.vertices());
		assertEquals(true, g.remove("2"));
		vertices.remove("2");
		assertEquals(vertices, g.vertices());
	}

	@Test
	public void testVertices() {
		Graph<String> g = emptyInstance();
		Set<String> vertices = new HashSet<String>();
		assertEquals(vertices, g.vertices());
		g.add("1");
		g.add("2");
		g.add("3");
		vertices.add("1");
		vertices.add("2");
		vertices.add("3");
		assertEquals(vertices, g.vertices());
		g.vertices().remove("3");
		assertEquals(vertices, g.vertices());
		Set<String> s = g.vertices();
		for(String l : s) {
			if(l.equals("3")) {
				l.concat("4");
			}
		}
		assertEquals(vertices, g.vertices());
	}

	// covers target exist or not
	@Test
	public void testSources() {
		Graph<String> g = emptyInstance();
		Map<String, Integer> source = new HashMap<String, Integer>();
		g.add("1");
		g.add("2");
		g.add("3");
		assertEquals(source, g.sources("1"));
		assertEquals(source, g.sources("4"));
		g.set("1", "2", 3);
		g.set("2", "1", 2);
		g.set("3", "1", 1);
		source.put("2", 2);
		source.put("3", 1);
		assertEquals(source, g.sources("1"));
		g.sources("1").remove("2");
		assertEquals(source, g.sources("1"));
	}

	// covers source exist or not
	@Test
	public void testTargets() {
		Graph<String> g = emptyInstance();
		Map<String, Integer> target = new HashMap<String, Integer>();
		g.add("1");
		g.add("2");
		g.add("3");
		assertEquals(target, g.targets("1"));
		assertEquals(target, g.targets("4"));
		g.set("1", "2", 3);
		g.set("2", "1", 2);
		g.set("1", "3", 1);
		target.put("2", 3);
		target.put("3", 1);
		assertEquals(target, g.targets("1"));
		g.targets("1").remove("2");
		assertEquals(target, g.targets("1"));
	}

}
