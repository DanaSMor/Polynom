import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myMath.Monom;
import myMath.Polynom;

class PolynomTest {
	String Ok[];
	String NotOk[];
	Polynom od, od1, od2;

	@BeforeEach
	void setUp() throws Exception {
		Ok = new String [] {"x^2", "3x^2+5-3x","25*x^7-3*x^2+3","x","-5","5x-x^3-5-x"};
		NotOk = new String[] {"+x","5a, xx, x^-2", "8**2, 9x--6x^2", "6x^"};
	}

	@Test
	void testPolynom() {
		od = new Polynom();
		assertTrue(od.isZero());
		assertEquals("0",od.toString());
	}

	@Test
	void testPolynomPolynom() {
		od = new Polynom("3x^2+5-3x");
		Polynom odCopy = new Polynom(od);

		assertNotEquals(od, odCopy);
		assertEquals(od.toString(),odCopy.toString());
		assertEquals(od.f(5),odCopy.f(5));
	}

	@Test
	void testPolynomString() {
		for(int i=0; i<Ok.length; i++) {
			try {
				od = null;
				od = new Polynom(Ok[i]);
				assertNotNull(od);
			}

			catch(Exception e) {
				fail("Should not get exception!!!");
			}
		}
		for(int i=0; i<NotOk.length; i++) {
			try {
				od = null;
				od = new Polynom(NotOk[i]);
				fail("Should get exception!!!");
			}

			catch(Exception e) {
				assertNull(od);
				assertTrue("Ok it's fail",true);
			}
		}
	}

	@Test
	void testF() {
		od = new Polynom("x^2+5-x^3");
		double x,check;
		int neg=1;
		for(int i=1; i<20; i++) {
			x=(Math.floor(10000*Math.random())/100)*neg;
			neg=-neg;
			check=Math.pow(x, 2)+5-Math.pow(x, 3);
			assertEquals(check, od.f(x));
		}

	}

	@Test
	void testAddPolynom_able() {
		//adding polynom
		od = new Polynom("5x^3-4x^2-3x+20");
		od1 = new Polynom("3x^2+5-3x");
		od2	= new Polynom("5x^3+15-7x^2");
		od1.add(od2);
		assertEquals(""+od,""+od1);
		//Adding empty polynom - checks if it's the same polynom
		od = new Polynom("-5x^3-15+7x^2");
		od1 = new Polynom("-5x^3-15+7x^2");
		od2	= new Polynom();
		od1.add(od2);
		assertEquals(""+od,""+od1);
		//Adding of the opposite polynom - checks if it equals to zero
		od = new Polynom();
		od1 = new Polynom("-5x^3-15+7x^2");
		od2	= new Polynom("5x^3+15-7x^2");
		od1.add(od2);
		assertEquals(""+od,""+od1);
		assertEquals("0",""+od1);

	}

	@Test
	void testAddMonom() {
		od = new Polynom("x^2-4");
		od.add(new Monom(5,2));
		assertEquals("-4.0 +6.0x^2",od.toString());
		double x;
		int neg=-1;
		for(int i=0; i<20; i++) {
			try {
				x=(Math.floor(10000*Math.random())/100)*neg;
				neg=-neg;
				od.add(new Monom(x,i));
			}
			catch (Exception e) {
				fail("Should Not get exception!!!");
			}
		}
	}

	@Test
	void testSubstract() {
		//Subtraction of the same polynom - checks if it equals to zero
		od = new Polynom();
		od1 = new Polynom("-5x^3-15+7x^2");
		od2	= new Polynom("-5x^3-15+7x^2");
		od1.substract(od2);
		assertEquals(""+od,""+od1);
		assertEquals("0",""+od1);
		//Subtraction of empty polynom - checks if it's the same polynom
		od = new Polynom("-5x^3-15+7x^2");
		od1 = new Polynom("-5x^3-15+7x^2");
		od2	= new Polynom();
		od1.substract(od2);
		assertEquals(""+od,""+od1);
		//sub polynom
		od = new Polynom("5x^3-4x^2-3x+20");
		od1 = new Polynom("3x^2+5-3x");
		od2	= new Polynom("5x^3+15-7x^2");
		od.substract(od1);
		assertEquals(""+od,""+od2);

	}

	@Test
	void testMultiply() {
		//multiplying of empty polynom - checks if it equals to zero
		od = new Polynom();
		od1 = new Polynom("-5x^3-15+7x^2");
		od2	= new Polynom();
		od1.multiply(od2);
		assertEquals(""+od,""+od1);
		//multiplying zero's
		od1.multiply(od2);
		assertEquals(""+od,""+od1);
		//multiplying 2 polynoms
		od = new Polynom("-30x^5+18x^4-25x^2+15x");
		od1 = new Polynom("-5x^2+3x");
		od2	= new Polynom("6x^3+5");
		od1.multiply(od2);
		assertEquals(""+od,""+od1);
	}

	@Test
	void testEqualsPolynom_able() {
		od = new Polynom("3x^2-5+7");
		Polynom p = new Polynom("2+3x^2");
		assertTrue(p.equals(od));

		od.substract(new Polynom("x^2-4"));
		assertFalse(p.equals(od));
	}

	@Test
	void testIsZero() {
		od = new Polynom("x");
		assertFalse(od.isZero());
		od = new Polynom();
		assertTrue(od.isZero());
	}

	@Test
	void testRoot() {
		od = new Polynom("-5x^2+25x+5");
		//given dots from the same side of X
		try 
		{
			od.root(6, 8, 0.001);
			fail("same side of X");
		}
		catch(Exception e)
		{
			assertTrue(true);
		}
		//find root
		double x = od.root(4, 8, 0.001);
		assertEquals(5, Math.round(x));
		//if it is zero
		od = new Polynom();
		try 
		{
			od.root(6, 8, 0.001);
			fail("zero polynom");
		}
		catch(Exception e)
		{
			assertTrue(true);
		}

	}

	@Test
	void testCopy() {
		od = new Polynom("3x^2+5-3x");
		Polynom odCopy = (Polynom) od.copy();
		
		assertNotEquals(od, odCopy);
		assertEquals(od.toString(),odCopy.toString());
		assertEquals(od.f(5),odCopy.f(5));

	}

	@Test
	void testDerivative() {
		//derivative
		od = new Polynom("-15x^2+14x");
		od1 = new Polynom("-5x^3+7x^2-15");

		assertEquals(""+od,""+od1.derivative());
		//derivative of an empty polynom
		od = new Polynom();
		od1 = new Polynom();
		od1.derivative();
		assertEquals(""+od,""+od1);

		od = new Polynom();
		od1 = new Polynom("5+25+35+14");
		assertEquals(""+od,""+od1.derivative());
	}

	@Test
	void testArea() {
		od = new Polynom("4x^2+16x");
		//given graph above the X line
		assertEquals(108,Math.round(od.area(0, 3, 0.001)));
		//given graph above and below the X line
		assertEquals(108,Math.round(od.area(-7, -4, 0.001)));
		//given graph above and below the X line
		assertEquals(216,Math.round(od.area(-7, 3, 0.001)));
	}

	@Test
	void testIteretor() {
		od = new Polynom("3x^2");
		Iterator<Monom> it = od.iteretor();
		Monom p = it.next();
		assertEquals(3,p.get_coefficient());
		assertEquals(2,p.get_power());
	}


	@Test
	void testToString() {
		for(int i=0; i<Ok.length; i++) {
			try {
				od = new Polynom(Ok[i]);
				od1  = new Polynom(""+od);
				assertEquals(""+od, ""+od1);
			}
			catch(Exception e) {
				fail("Should Not get exception!!!");
			}
		}
	}

}