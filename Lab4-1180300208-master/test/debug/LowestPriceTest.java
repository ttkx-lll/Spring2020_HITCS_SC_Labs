package debug;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LowestPriceTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testShoppingOffers1() {
		LowestPrice lp = new LowestPrice();
		List<Integer> price = new ArrayList<>();
		price.add(2);
		price.add(5);
		List<Integer> offer1 = new ArrayList<>();
		offer1.add(3);
		offer1.add(0);
		offer1.add(5);
		List<Integer> offer2 = new ArrayList<>();
		offer2.add(1);
		offer2.add(2);
		offer2.add(10);
		List<List<Integer>> special = new ArrayList<>();
		special.add(offer1);
		special.add(offer2);
		List<Integer> needs = new ArrayList<>();
		needs.add(3);
		needs.add(2);
		assertEquals(14, lp.shoppingOffers(price, special, needs));
	}

	@Test
	public void testShoppingOffers2() {
		LowestPrice lp = new LowestPrice();
		List<Integer> price = new ArrayList<>();
		price.add(2);
		price.add(3);
		price.add(4);
		List<Integer> offer1 = new ArrayList<>();
		offer1.add(1);
		offer1.add(1);
		offer1.add(0);
		offer1.add(4);
		List<Integer> offer2 = new ArrayList<>();
		offer2.add(2);
		offer2.add(2);
		offer2.add(1);
		offer2.add(9);
		List<List<Integer>> special = new ArrayList<>();
		special.add(offer1);
		special.add(offer2);
		List<Integer> needs = new ArrayList<>();
		needs.add(1);
		needs.add(2);
		needs.add(1);
		assertEquals(11, lp.shoppingOffers(price, special, needs));
	}

	@Test
	public void testShoppingOffers3() {
		LowestPrice lp = new LowestPrice();
		List<Integer> price = new ArrayList<>();
		price.add(2);
		price.add(5);
		price.add(7);
		ArrayList<Integer> offer1 = new ArrayList<Integer>();
		offer1.add(3);
		offer1.add(0);
		offer1.add(0);
		offer1.add(5);
		ArrayList<Integer> offer2 = new ArrayList<Integer>();
		offer2.add(1);
		offer2.add(2);
		offer2.add(0);
		offer2.add(10);
		List<List<Integer>> special = new ArrayList<>();
		special.add(offer1);
		special.add(offer2);
		List<Integer> needs = new ArrayList<>();
		needs.add(3);
		needs.add(2);
		needs.add(1);
		assertEquals(21, lp.shoppingOffers(price, special, needs));
	}

	@Test
	public void testShoppingOffers4() {
		LowestPrice lp = new LowestPrice();
		List<Integer> price = new ArrayList<>();
		price.add(1);
		price.add(2);
		ArrayList<Integer> offer1 = new ArrayList<Integer>();
		offer1.add(2);
		offer1.add(1);
		offer1.add(3);
		ArrayList<Integer> offer2 = new ArrayList<Integer>();
		offer2.add(1);
		offer2.add(2);
		offer2.add(4);
		List<List<Integer>> special = new ArrayList<>();
		special.add(offer1);
		special.add(offer2);
		List<Integer> needs = new ArrayList<>();
		needs.add(3);
		needs.add(0);
		assertEquals(3, lp.shoppingOffers(price, special, needs));
	}
}
