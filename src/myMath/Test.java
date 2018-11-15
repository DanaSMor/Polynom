package myMath;


/**
 * This test checks all system functionality for in-depth inspection

 * @author OrAvital && DanaMorHaim
 *
 */
public class Test 
{

	public static void main(String[] args)  {


		//////Monom section//////
		Monom m = new Monom(23,6);
		Monom m2 = new Monom(1,6);

		System.out.println("Before adding: "+m);

		m.add(m2);
		System.out.println("After adding: "+m);

		System.out.println("Before multiply: "+m);
		m.multiply(m2);;
		System.out.println("After multiply: "+m);		


		System.out.println("Before derivative: "+m);
		m.derivative();
		System.out.println("After derivative: "+m);		

		/////Polynom section//////
		System.out.println();
		Polynom dana = new Polynom("2x^2-5");//"15x^3 +12x -x^1+7-15*x^4"

		Polynom or = new Polynom("4x^5-3x");//"17x^2-33x^5+2x^2"
		System.out.println("Polynom 1: "+dana); 
		System.out.println("Polynom 2: "+or);  // Show the input of the Polynom

		Polynom_able copyOr = or.copy(); // Deep copy of arrayList
		System.out.println(copyOr);
		System.out.println(copyOr.equals(or)); // Check logic equal

		System.out.println("\nBefore adding Monom: \npolynom 1:"+or+"\nMonom:"+m);
		or.add(m);
		System.out.println("\nAfter adding Monom:\n"+or);

		System.out.println("\nBefore multiplying: \npolynom 1:"+or+"\nPolynom 2:"+dana);
		or.multiply(dana); // Lets Multiply them (x^2-2)^2 = 4 -4x^2 +x^4
		System.out.println("\nAfter multiplying:\n"+or); 

		// Lets Add them 
		System.out.println("\nBefore adding Polynom: \npolynom 1:"+or+"\nPolynom 2:"+dana);
		or.add(dana);
		System.out.println("\nAfter adding Polynom:\n"+or); 

		// Lets Sub them 
		System.out.println("\nBefore substraction: \npolynom 1:"+or+"\nPolynom 2:"+dana);
		or.substract(dana);
		System.out.println("\nAfter substraction:\n"+or); 

		//Derivative 
		Polynom_able pA=or.derivative();
		System.out.println("\nBefore derivative:\n"+or+"\nAfter derivative:\n"+pA);

		//Area calculating
		double a=-1,b=5,eps=0.0001;
		System.out.println("\nCalculating area of"+dana+" from "+a+" to "+b+", eps "+eps+":");
		System.out.println(dana.area(a, b, eps));

		//root calculation
		System.out.println("\nCalculating root of "+dana+" from "+a+" to "+b+", eps "+eps+":");
		System.out.println(dana.root(a, b, eps));

		dana = new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5");
		//dana = new Polynom("x^3");
		System.out.println(dana);		
		dana.Graph(-2,6);
		
	}
}
