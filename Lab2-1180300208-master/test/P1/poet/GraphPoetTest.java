/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    /* Testing strategy
     * use the given corpus text file and given input to test it.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void testGraphPoet() throws IOException {
    	final GraphPoet nimoy = new GraphPoet(new File("test/P1/Poet/mugar-omni-theater.txt"));
		final String input = "Test the system.";
		assertEquals("Test of the system.", nimoy.poem(input));
	}
}
