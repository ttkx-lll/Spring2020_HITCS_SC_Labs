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
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	//    a map made up with message form vertices
	
	// Representation invariant:
	//	  vertices should not be NULL and contains no repeated Vertex<L>
	
	// Safety from rep exposure:
	//    use final keyword;
	//    use defensive copying;

	// TODO checkRep
	public void checkRep() {
		assert vertices != null;
		for(Vertex<L> v1 : vertices) {
			for(Vertex<L> v2 : vertices) {
				assert (v1 == v2) || (!v1.equals(v2));
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		for (Vertex<L> v : vertices) {
			if (v.getName().equals(vertex)) {
				return false;
			}
		}
		vertices.add(new Vertex<L>(vertex));
		checkRep();
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {

		boolean haveSource = false, haveTarget = false;
		Vertex<L> sour = null;
		Vertex<L> targ = null;
		int preWeight = 0;
		
		for (Vertex<L> v : vertices) {
			if (v.getName().equals(source)) {
				haveSource = true;
				sour = v;
			}
			if (v.getName().equals(target)) {
				haveTarget = true;
				targ = v;
			}
		}
		
		if (haveSource && haveTarget) {
			
			Map<L, Integer> setTarget = sour.getTargets();
			Map<L, Integer> setSource = targ.getSources();
			
			if (setTarget.containsKey(target)) {
				
				preWeight = setTarget.get(target);
				setTarget.remove(target);
				setSource.remove(source);
				
			} 
			
			if (weight > 0) {
				setTarget.put(target, weight);
				setSource.put(source, weight);
			}
			
			sour.setTargets(setTarget);
			targ.setSources(setSource);

		}

		checkRep();
		return preWeight;
	}

	@Override
	public boolean remove(L vertex) {

		boolean flag = false;

		for (Vertex<L> v : vertices) {
			this.set(v.getName(), vertex, 0);
			this.set(vertex, v.getName(), 0);

		}

		for (Vertex<L> v : vertices) {
			if (v.getName().equals(vertex)) {
				flag = true;
				vertices.remove(v);
			}
		}

		checkRep();
		return flag;
	}

	@Override
	public Set<L> vertices() {

		Set<L> vertice = new HashSet<L>();
		for (Vertex<L> v : vertices) {
			vertice.add(v.getName());
		}
		return vertice;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		for (Vertex<L> v : vertices) {
			if (v.getName().equals(target)) {
				Map<L, Integer> sourcesCopy = new HashMap<L, Integer>();
				sourcesCopy.putAll(v.getSources());
				return sourcesCopy;
			}
		}
		return new HashMap<L, Integer>();
	}

	@Override
	public Map<L, Integer> targets(L source) {
		for (Vertex<L> v : vertices) {
			if (v.getName().equals(source)) {
				Map<L, Integer> targetsCopy = new HashMap<L, Integer>();
				targetsCopy.putAll(v.getTargets());
				return targetsCopy;
			}
		}
		return new HashMap<L, Integer>();
	}

	// toString()
	@Override
	public String toString() {
		String tostring = "ConcreteVerticesGraph\n";
		for (Vertex<L> v : vertices) {
			tostring += v.toString();
		}
		return tostring;
	}

	public static void main(String[] args) {
		Graph<Integer> g = new ConcreteVerticesGraph<Integer>();
//    	g.add("1"); g.add("2"); g.add("3");
//    	g.set("1", "2", 3);
//    	g.set("2", "1", 2);
//    	g.set("1", "3", 1);
//    	g.set("1", "3", 0);
		g.add(1);
		g.add(2);
		g.add(3);
		g.set(1, 2, 3);
		g.set(2, 1, 2);
		g.set(1, 3, 1);
		g.set(1, 3, 0);
		System.out.println(g.toString());
	}

}

/**
 * Specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 */
class Vertex<L> {

	private final L name;
	private Map<L, Integer> sources = new HashMap<L, Integer>();
	private Map<L, Integer> targets = new HashMap<L, Integer>();

	// Abstraction function:
	// a vertex with the message of its name, sources and targets
	
	// Representation invariant:
	//    name should not be NULL
	//    sources contains this vertex's source vertices and weight (weight > 0)
	//    targets contains this vertex's target vertices and weight (weight > 0)
	
	// Safety from rep exposure:
	//    use final keyword;
	//    use defensive copying;
	//    all fields are private.
	

	// constructor
	public Vertex(L name) {
		this.name = name;
	}
	
	// TODO checkRep
	public void checkRep() {
		
	}

	// methods
	public Map<L, Integer> getSources() {
		Map<L, Integer> sourcesCopy = new HashMap<L, Integer>();
		sourcesCopy.putAll(sources);
		return sourcesCopy;
	}

	public Map<L, Integer> getTargets() {
		Map<L, Integer> targetsCopy = new HashMap<L, Integer>();
		targetsCopy.putAll(targets);
		return targetsCopy;
	}

	public L getName() {
		return name;
	}

	public void setSources(Map<L, Integer> sources) {
		this.sources = sources;
	}

	public void setTargets(Map<L, Integer> targets) {
		this.targets = targets;
	}

	// toString()
	@Override
	public String toString() {
		String tostring = "Vertex name=" + name + "\n";
		for (Map.Entry<L, Integer> source : sources.entrySet()) {
			tostring += "       source=" + source.getKey() + ", weight=" + source.getValue() + "\n";
		}
		for (Map.Entry<L, Integer> target : targets.entrySet()) {
			tostring += "       target=" + target.getKey() + ", weight=" + target.getValue() + "\n";
		}
		return tostring;
	}

}
