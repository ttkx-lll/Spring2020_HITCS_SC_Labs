/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = Graph.empty();

	// Abstraction function:
	//     A map made up with words in coherent sentences, which can insert 
	//     bridge words in a given sentences.
	
	// Representation invariant:
	//     graph should not be NULL
	
	// Safety from rep exposure:
	//     use final keywords;
	//     graph is private.
	
	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus text file from which to derive the poet's affinity graph
	 * @throws IOException if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(corpus), "UTF-8"));
			String lineTxt = "";

			while ((lineTxt = br.readLine()) != null) {
				String[] words = lineTxt.split(" ");
				words[0] = words[0].toLowerCase();
				graph.add(words[0]);
				for (int i = 1; i < words.length; i++) {
					words[i] = words[i].toLowerCase();
					graph.add(words[i]);
					graph.set(words[i - 1], words[i], graph.set(words[i - 1], words[i], 0) + 1);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// checkRep
	public void checkRep() {
		assert !graph.vertices().isEmpty();
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		String[] inputWords = input.split(" ");
		String output = "";
		String insert;
		int sourceWeight;
		for (int i = 0; i < inputWords.length - 1; i++) {
			insert = "";
			sourceWeight = 0;
			for(String corpusWord : graph.vertices()) {
				if(graph.sources(corpusWord).keySet().contains(inputWords[i].toLowerCase()) 
						&& graph.targets(corpusWord).keySet().contains(inputWords[i+1].toLowerCase())) {
					if(graph.sources(corpusWord).get(inputWords[i].toLowerCase()) > sourceWeight) {
						sourceWeight = graph.sources(corpusWord).get(inputWords[i].toLowerCase());
						insert = corpusWord + " ";
					}
				}
			}
			output += inputWords[i] + " " + insert;
		}
		output += inputWords[inputWords.length-1];
		
		return output;
	}

	// toString()
	@Override
	public String toString() {
		return "GraphPoet\n" + graph.toString();
	}

}
