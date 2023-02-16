package debug;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FlightClientTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testPlaneAllocation1() {

		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();
		FlightClient client = new FlightClient();

		Plane p1 = new Plane();
		p1.setPlaneNo("a");
		p1.setSeatsNum(10);
		p1.setPlaneType("a");
		planes.add(p1);
		Flight f1 = new Flight();
		Calendar a1 = Calendar.getInstance();
		a1.set(2020, 5, 20, 8, 0);
		Calendar d1 = Calendar.getInstance();
		d1.set(2020, 5, 20, 9, 0);
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 5, 20);
		f1.setArrivalTime(d1);
		f1.setDepartTime(a1);
		f1.setFlightDate(date1);
		f1.setFlightNo("aa");
		Flight f2 = new Flight();
		Calendar a2 = Calendar.getInstance();
		a2.set(2020, 5, 20, 8, 30);
		Calendar d2 = Calendar.getInstance();
		d2.set(2020, 5, 20, 9, 30);
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 5, 20);
		f2.setArrivalTime(d2);
		f2.setDepartTime(a2);
		f2.setFlightDate(date2);
		f2.setFlightNo("bb");
		flights.add(f1);
		flights.add(f2);
		assertFalse(client.planeAllocation(planes, flights));
	}

	@Test
	public void testPlaneAllocation2() {

		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();
		FlightClient client = new FlightClient();

		Plane p1 = new Plane();
		p1.setPlaneNo("a");
		p1.setSeatsNum(10);
		p1.setPlaneType("a");
		Plane p2 = new Plane();
		p2.setPlaneNo("b");
		p2.setSeatsNum(20);
		p2.setPlaneType("b");
		planes.add(p1);
		planes.add(p2);
		Flight f1 = new Flight();
		Calendar a1 = Calendar.getInstance();
		a1.set(2020, 5, 20, 8, 0);
		Calendar d1 = Calendar.getInstance();
		d1.set(2020, 5, 20, 9, 0);
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 5, 20);
		f1.setArrivalTime(d1);
		f1.setDepartTime(a1);
		f1.setFlightDate(date1);
		f1.setFlightNo("aa");
		Flight f2 = new Flight();
		Calendar a2 = Calendar.getInstance();
		a2.set(2020, 5, 20, 8, 30);
		Calendar d2 = Calendar.getInstance();
		d2.set(2020, 5, 20, 9, 30);
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 5, 20);
		f2.setArrivalTime(d2);
		f2.setDepartTime(a2);
		f2.setFlightDate(date2);
		f2.setFlightNo("bb");
		flights.add(f1);
		flights.add(f2);
		assertTrue(client.planeAllocation(planes, flights));
	}

	@Test
	public void testPlaneAllocation3() {

		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();
		FlightClient client = new FlightClient();

		Flight f1 = new Flight();
		Calendar a1 = Calendar.getInstance();
		a1.set(2020, 5, 20, 8, 0);
		Calendar d1 = Calendar.getInstance();
		d1.set(2020, 5, 20, 9, 0);
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 5, 20);
		f1.setArrivalTime(d1);
		f1.setDepartTime(a1);
		f1.setFlightDate(date1);
		f1.setFlightNo("aa");
		Flight f2 = new Flight();
		Calendar a2 = Calendar.getInstance();
		a2.set(2020, 5, 20, 8, 30);
		Calendar d2 = Calendar.getInstance();
		d2.set(2020, 5, 20, 9, 30);
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 5, 20);
		f2.setArrivalTime(d2);
		f2.setDepartTime(a2);
		f2.setFlightDate(date2);
		f2.setFlightNo("bb");
		flights.add(f1);
		flights.add(f2);
		assertFalse(client.planeAllocation(planes, flights));
	}

	@Test
	public void testPlaneAllocation4() {

		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();
		FlightClient client = new FlightClient();

		Plane p1 = new Plane();
		p1.setPlaneNo("a");
		p1.setSeatsNum(10);
		p1.setPlaneType("a");
		planes.add(p1);
		Flight f1 = new Flight();
		Calendar a1 = Calendar.getInstance();
		a1.set(2020, 5, 20, 8, 0);
		Calendar d1 = Calendar.getInstance();
		d1.set(2020, 5, 20, 9, 0);
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 5, 20);
		f1.setArrivalTime(d1);
		f1.setDepartTime(a1);
		f1.setFlightDate(date1);
		f1.setFlightNo("aa");
		Flight f2 = new Flight();
		Calendar a2 = Calendar.getInstance();
		a2.set(2020, 5, 20, 10, 30);
		Calendar d2 = Calendar.getInstance();
		d2.set(2020, 5, 20, 11, 30);
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 5, 20);
		f2.setArrivalTime(d2);
		f2.setDepartTime(a2);
		f2.setFlightDate(date2);
		f2.setFlightNo("bb");
		flights.add(f1);
		flights.add(f2);
		assertTrue(client.planeAllocation(planes, flights));
	}
}
