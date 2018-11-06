import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import myMath.Monom;
class MonomTest {

	Monom m1;
	Monom m2;
	Monom m3;



	@Test
	void testMonomDoubleInt() {
		double a=0;
		int b=0;
		for(int i=0; i<20; i++) { // Ok for
			try {
				m1 = new Monom(a,b);
				assertEquals(b,m1.get_power());
				assertEquals(a,m1.get_coefficient());
				a = Math.floor(Math.random()*1000)/100;
				b = (int)(Math.random()*10);
			}
			catch(Exception e) {
				fail("Should not het exception!!!");
			}
		}
		try {
			m1 = new Monom(-5,-5);
			fail("Should get exception!!!");
		}
		catch(Exception e) {

		}
	}

	@Test
	void testMonomMonom() {
		m1 = new Monom(5,3);
		m2 = new Monom(m1);
		assertNotEquals(m1,m2);
		assertEquals(m1.get_coefficient(),m1.get_coefficient());
		assertEquals(m1.get_power(),m1.get_power());


	}

	@Test
	void testGet_power() {
		Monom m1 = new Monom(-5,3);
		assertEquals(3, m1.get_power());
	}

	@Test
	void testGet_coefficient() {
		Monom m1 = new Monom(-5,3);
		assertEquals(-5, m1.get_coefficient());
	}

	@Test
	void testF() {
		Monom m1 = new Monom(-5,3);
		assertEquals(-5, m1.f(1));
	}

	@Test
	void testDerivative() {
		Monom m1 = new Monom(-5,3);
		Monom m2 = new Monom(-15,2);
		m1.derivative();
		assertEquals(""+m2,""+m1);

		m1 = new Monom(-5,0);
		m1.derivative();
		assertEquals("0.0",""+m1);
	}

	@Test
	void testAdd() {
		Monom m1 = new Monom(-5,3);
		Monom m2 = new Monom(-15,2);
		try {
			m1.add(m2);
			fail("not the same power!");
		}
		catch(Exception e)
		{
			assertTrue(true);
		}
		//checking monoms with the same power
		m1 = new Monom(-5,6);
		m2 = new Monom(-15,6);
		Monom m3 = new Monom(-20,6);
		m1.add(m2);
		assertEquals(""+m3,""+m1);
	}

	@Test
	void testMultiply() {
		//multiplying
		Monom m1 = new Monom(-5,3);
		Monom m2 = new Monom(4,2);
		Monom m3 = new Monom(-20,5);
		m1.multiply(m2);
		assertEquals(""+m3,""+m1);
		
		//multiplying zero monom
		m1 = new Monom(0,0);
		m2.multiply(m1);
		assertEquals("0.0",""+m1);
		
	}

	@Test
	void testToString() {
		Monom m1 = new Monom(-5,3);
		String s = ""+m1;
		assertEquals(s, m1.toString());
	}


}
