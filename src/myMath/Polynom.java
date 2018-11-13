package myMath;

import java.util.ArrayList;
import java.util.Iterator;

import Graph.GraphPoints;
import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Or Avital and Dana Morhaim
 *
 */
public class Polynom implements Polynom_able{
	private Monom_Comperator compare = new Monom_Comperator();
	private ArrayList<Monom> List;

	// ********** add your code below ***********

	public Polynom() {               // Empty constructor just open a new empty ArrayList  of Monom's
		List = new ArrayList<>();
	}
	public Polynom(Polynom pol) {    // Copy constructor
		List = new ArrayList<>();
		Iterator<Monom> it = pol.iteretor();
		while(it.hasNext()) {
			Monom s = new Monom(it.next());
			List.add(s);
		}
	}
	public Polynom(String s) {       // String constructor getting a function from string and open ArrayList of them
		List = new ArrayList<>();
		convert(s);                  // Call to convert(convert from string to Monom's)
		List.sort(compare);          // After the insert sort the array of Monom's
		merge();                     // Perform merge of all the double Monom's on the array list with the same power
		cleanZero();
	}

	/**
	 * getting String polynom from the string constructor 
	 * start to break down into singles of monoms and send it into 'addList' function with string of a*x^b with a sign
	 * @param s
	 */
	private void convert(String s) {
		s=s.toLowerCase();
		if(s.contains("**")||s.contains("xx"))
			throw new RuntimeException("Error in the next Monom: "+"'"+s+"'" +" Not properly written, Please check again and insert right form of: 'a*x^b' or 'ax^b'");
		s=s.replace('*', ' ');       //Remove all '*' in the string
		s=s.replaceAll(" ", "");     //Remove all the spaces in the string
		int sign=1,start=0;          // Sign = if is it Minus= -1 or Plus = 1, init=1, start the index of the substring start
		for(int i=0; i < s.length(); i++) {
			if(i==0 && s.charAt(0)=='-') {      // First check if there is minus on the start if yes change the init of sign and start
				sign=-1;
				start=1;
			}
			//This if, stop include only the operators(+ or -)
			if(i!=0 && (s.charAt(i)=='-' || s.charAt(i)=='+' || i==s.length()-1)) {    // if we found another operator(+ or -) push the all string numbers before the operator
				if(i==s.length()-1) addList(sign,s.substring(start, i+1));            // if it's the last operator just insert it into addList with i+1
				else addList(sign,s.substring(start, i));                         // else it's not the last operator so insert to addList normal 
				if(s.charAt(i)=='-') sign=-1;                                 // Now check the current operator  is - or + and determine the sign
				else sign=1;
				start=i+1;                                                  // The next substring will start on i+1
			}
			else if(s.length()==1) addList(1,s); // If there is a single 'x' just insert it in his rule
		}
	}

	/**Getting s and sign from convert function
	 * use with split function that separate the power and coefficient to different spot in the array
	 * able to get form of a*x^b or ax^b 
	 * finally adding the result into the list array of monoms 
	 * 
	 * @param sign
	 * @param s
	 */
	private void addList(int sign ,String s) {                        // get the sign and correct substring of the monom and insert it into the list
		try {
			String split[] = s.split("x");                           // Split according 'x'
			double a;
			int b;
			if(split.length==0) {                                   // if the array is empty so it's form of single 'x'
				a=sign; // Add it is single 'x'
				b=1;
			}
			else if(split.length==1) {                             // form of 1
				if(s.contains("x")) {                             // first check if the original string include 'x' inside
					a=Double.parseDouble(split[0])*sign;         // just convert to double with the correct sign and power of 1
					b=1;
				}
				else {
					a=Double.parseDouble(split[0])*sign;        // else there is not 'x' on string so the power is '0'
					b=0;
				}
			}
			else {
				if(split[0].length()==0) {                    // if it's empty so was there only x, add it with the correct sign
					a=sign;
					b=Integer.parseInt(split[1].substring(1));
				}
				else {                                     // else it's of the form example: 3x^4 or 3*x^4
					a=Double.parseDouble(split[0])*sign;
					b=Integer.parseInt(split[1].substring(1));
				}
			}
			List.add(new Monom(a,b));
		}
		catch(NumberFormatException e) {
			throw new RuntimeException("Error in the next Monom: "+"'"+s+"'" +" Not properly written, Please check again and insert right form of: 'a*x^b' or 'ax^b'");
		}
	}

	/**
	 * the merge function able to to discover all the same power and to combine them together
	 * into one Monom elements
	 * finally use 'cleanZero' function to discover the monoms that reset to zero
	 */
	private void merge() {                                             // Merge all the same power of Monom's
		for(int i=0; i<List.size()-1; i++) {
			if(List.get(i).get_power()==List.get(i+1).get_power()) {   // if we find the same power, combine them into 1
				List.set(i, new Monom(List.get(i).get_coefficient()+List.get(i+1).get_coefficient(),List.get(i).get_power()));
				List.remove(i+1);                        // we set a new Monom with the new correct value and now we can to remove the next one
				i--;                                    // and start again maybe there is another one with the same power
			}
		}
		cleanZero();
	}

	/**
	 * responsible for clean the all zero monoms that worth nothing
	 */
	private void cleanZero() {                          // Scan the Polynom and search for zero Monom's that equal to nothing and clean it
		for(int i=0; i<List.size(); i++) {
			if(List.get(i).get_coefficient()==0) List.remove(i);
		}
	}

	@Override
	public double f(double x) {
		double sum=0;
		Iterator<Monom> it = List.iterator();
		while(it.hasNext()) {                          // Scan the array list with the iterator and start to calculate the Monom's
			Monom current =it.next();                  // We use the function that we wrote on the Monom class
			sum +=current.f(x);                        // Add it to sum variable
		}
		return sum;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {                          // Scan all the Monom's in p1
			add(it.next());                          // Add every single of Monom in p1 and use the add function that can to add, sort and merge
		}
	}

	@Override
	public void add(Monom m1) {
		List.add(m1);                              // Add a single Monom that we get
		List.sort(compare);                       // Sort the our ArrayList with comperator that we build
		merge();                                 // Merge all the same power
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {                 // Scan all the Monom's in p1
			Monom pol = it.next();
			add(new Monom(-pol.get_coefficient(),pol.get_power()));  // Perform substruct on every single Monom and add it to our List with our add function
		}
	}

	@Override
	public void multiply(Polynom_able p1) {
		Polynom_able temp = copy();                 // Save our List on temp elements
		Iterator<Monom> p = temp.iteretor();
		List = new ArrayList<>();                  // Create a new ArrayList with empty elements
		while(p.hasNext()) {
			Iterator<Monom> it = p1.iteretor();
			Monom mult = (p.next());
			while(it.hasNext()) {                // Start to multiply (x1 x2 x3)(y1 y2 y3) start with 'x' and just move the pointer on 'y'
				Monom mult1 = new Monom(it.next());
				mult1.multiply(mult);
				List.add(mult1);                // Perform the calculate of the multiplying
			}
		}
		List.sort(compare);                   // Perform again sort
		merge();                             // And then merge
	}

	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> p = List.iterator();
		Iterator<Monom> it = p1.iteretor();
		boolean flag =true;               // Let's assume they are equal
		while(p.hasNext() && it.hasNext() && flag) {
			Monom mult = p.next();
			Monom mult1 = it.next();
			flag=false;                 // To continue we have to get same coefficient and the same power of every single Monom's
			if(mult1.get_coefficient()==mult.get_coefficient() && mult1.get_power()==mult.get_power()) flag =true;
		}
		return flag;
	}

	@Override
	public boolean isZero() {
		return List.size()==0;        // If the list is empty so there is a Zero plynom
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if(f(x0)*f(x1)>0 || this.isZero())
			throw new RuntimeException("invalid parameters: f(x0) and f(x1) should from oposite sides of x line");
		if(Math.abs(f(x0))<eps) return x0;
		if(Math.abs(f(x1))<eps) return x1;
		double M=(x0+x1)/2;                 // Save the middle
		while(Math.abs(f(M))>=eps) {          // While the error margin still bigger than eps continue to close		
			if(f(M)*f(x0)<0) x1=M;            // According the formula
			else x0=M;
			M =(x0+x1)/2;
		}
		return M;
	}

	@Override
	public Polynom_able copy() {                // Make a deep copy of our array use the constructor
		return new Polynom(this);
	}

	@Override
	public Polynom_able derivative() {         // We use the derivative that we build on the Monom's class
		Polynom  p = (Polynom) copy();
		Iterator<Monom> it = p.iteretor();
		while(it.hasNext()) {                 // Scan our list
			Monom current = it.next(); 
			current.derivative();            // Perform derivative
		}
		p.cleanZero();
		return p;
	}

	@Override
	//  Calculate the area of given a polynom
	public double area(double x0, double x1, double eps) {
		double sumOfArea=0; //sums the area
		for (double i = x0; i < x1; i+=eps) {
			if(this.f(i)>0) sumOfArea+=this.f(i)*eps;		//  Sum up the area of the rectangle
		}		
		return Math.abs(sumOfArea); //  Return the sum of the area
	}
	
	//  Calculate the Under area of given a polynom
	public double areaUnder(double x0, double x1, double eps) {
		double sumOfArea=0; // Sums the area
		for (double i = x0; i < x1; i+=eps) {
			if(this.f(i)<0) sumOfArea+=this.f(i)*eps;		//  Sum up the area of the rectangle
		}		
		return Math.abs(sumOfArea); //  Return the sum of the area
	}

	@Override
	public Iterator<Monom> iteretor() {
		return List.iterator();//  Creates iterator
	}

	/**
	 * Draw a Polynom in a specified range of user decision
	 * @param a - x0
	 * @param b - x1
	 */
	public void Graph(double a, double b) {
		GraphPoints frame = new GraphPoints(this,a,b); // Call Graphic class
		frame.setVisible(true); // Set frame to be visible
	}

	public String toString() {
		if(List.size()==0) return ""+0;
		String s =List.get(0).toString();
		for(int i=1; i<List.size(); i++) {
			if(List.get(i).get_coefficient()>=0) s+= " +"+List.get(i).toString();
			else s+=" "+List.get(i).toString();
		}
		return s;
	}

}