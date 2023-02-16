/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	// Abstraction function:
	// 	  a map made up with message from vertices and edges
	
	// Representation invariant:
	//    vertices should not be NULL and contains no repeated L
	//    edges should not be NULL and contains no repeated Edge
	
	// Safety from rep exposure:
	//    use final keyword;
	//    use defensive copying.
	
	// checkRep
	public void checkRep() {
		assert vertices != null;
		assert edges != null;
		for(Edge<L> e1 : edges) {
			for(Edge<L> e2 : edges) {
				assert (e1 == e2) || (!e1.equals(e2));
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		return vertices.add(vertex);
	}

	@Override
	public int set(L source, L target, int weight) {

		boolean haveSource = false, haveTarget = false;

		for (L vertex : vertices) {
			if (vertex.equals(source))
				haveSource = true;
			if (vertex.equals(target))
				haveTarget = true;
		}

		if (haveSource && haveTarget) {
			for (Edge<L> e : edges) {
				if (e.getSource().equals(source) && e.getTarget().equals(target)) {
					int preWeight = e.getWeight();
					edges.remove(e);
					if (weight > 0) {
						edges.add(new Edge<L>(source, target, weight));
					}
					checkRep();
					return preWeight;
				}
			}
			if (weight > 0) {
				edges.add(new Edge<L>(source, target, weight));
			}
		}

		checkRep();
		return 0;
	}

	@Override
	public boolean remove(L vertex) {

		if (!vertices.contains(vertex))
			return false;

		for(L v : this.vertices) {
			set(v, vertex, 0);
			set(vertex, v, 0);
		}
		
		vertices.remove(vertex);

		checkRep();
		return true;

	}

	@Override
	public Set<L> vertices() {
		Set<L> verticesCopy = new HashSet<L>();
		verticesCopy.addAll(vertices);
		return verticesCopy;
	}

	@Override
	public Map<L, Integer> sources(L target) {

		Map<L, Integer> sourcesMap = new HashMap<L, Integer>();

		for (Edge<L> e : edges) {
			if (e.getTarget().equals(target)) {
				sourcesMap.put(e.getSource(), e.getWeight());
			}
		}

		return sourcesMap;
	}

	@Override
	public Map<L, Integer> targets(L source) {

		Map<L, Integer> targetsMap = new HashMap<L, Integer>();

		for (Edge<L> e : edges) {
			if (e.getSource().equals(source)) {
				targetsMap.put(e.getTarget(), e.getWeight());
			}
		}

		return targetsMap;
	}

	// toString()
	@Override
	public String toString() {
		String tostring = "ConcreteEdgesGraph\n";
		for (Edge<L> e : edges) {
			tostring += e.toString();
		}
		return tostring;
	}

	public static void main(String[] args) {
		Graph<String> g = new ConcreteEdgesGraph<String>();
		g.add("1");
		g.add("2");
		g.add("3");
		g.set("1", "2", 3);
		g.set("2", "1", 2);
		g.set("1", "3", 1);
		g.set("1", "3", 0);
		System.out.println(g.toString());
	}
}

/**
 * Specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 */
class Edge<L> {

	private final L source;
	private final L target;
	private final int weight;

	// Abstraction function:
	//    an edge of a map from source to target with weight "weight"
	
	// Representation invariant:
	// 	  source shouldn't be NULL
	//	  target shouldn't be NULL
	//	  weight > 0
	
	// Safety from rep exposure:
	// 	  use final keyword;
	//    all fields are private.

	// constructor
	public Edge(L source, L target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	// checkRep
	public void checkRep() {
		assert source != null;
		assert target != null;
	}

	// methods
	public L getSource() {
		checkRep();
		return source;
	}

	public L getTarget() {
		checkRep();
		return target;
	}

	public int getWeight() {
		checkRep();
		return weight;
	}

	// toString()
	@Override
	public String toString() {
		return "Edge [source=" + source.toString() + ", target=" + target.toString() + ", weight=" + weight + "]"
				+ "\n";
	}
}
