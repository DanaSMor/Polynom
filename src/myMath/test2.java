package myMath;

public class test2 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		Polynom dana = new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5");
		Polynom_able der=new Polynom();
//		for (int i = -50; i < 50; i++) {
//			der = dana.derivative();
//			if(der.f(i)==0)
//				System.out.println("x:"+i+" y: "+dana.f(i));
//			else
//				System.out.println(i);
//			
//		}
		
		for (double i = -50; i < 50; i=i+0.25) {
			der = dana.derivative();
			if((der.f(i)<0.5&&der.f(i)>-0.5))
				System.out.println("x:"+i+" y: "+dana.f(i));
			//else
				//System.out.println(i);
			
		}

	}

}
