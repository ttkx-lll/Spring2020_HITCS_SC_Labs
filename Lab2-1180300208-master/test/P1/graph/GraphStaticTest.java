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
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {

	// Testing strategy
	// empty()
	// no inputs, only output is empty graph
	// observe with vertices()

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testEmptyVerticesEmpty() {
		assertEquals("expected empty() graph to have no vertices", Collections.emptySet(), Graph.empty().vertices());
	}

	@Test
	public void testIntegerLabel() {
		Graph<Integer> g = Graph.empty();
		Set<Integer> set = new HashSet<Integer>();
		Map<Integer, Integer> source = new HashMap<>();
		Map<Integer, Integer> target = new HashMap<>();
		g.add(12);
		g.add(12);
		g.add(1);
		g.add(5);
		set.add(12);
		set.add(1);
		set.add(5);
		assertEquals(set, g.vertices());
		g.set(12, 1, 1);
		g.set(5, 1, 5);
		g.set(1, 5, 4);
		source.put(12, 1);
		source.put(5, 5);
		target.put(5, 4);
		assertEquals(source, g.sources(1));
		assertEquals(target, g.targets(1));
		assertEquals(true, g.remove(1));
		set.remove(1);
		assertEquals(set, g.vertices());

	}

}
