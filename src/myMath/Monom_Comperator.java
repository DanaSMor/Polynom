package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {
	
	// ******** add your code below *********

	@Override
	public int compare(Monom o1, Monom o2) {
		return (o1.get_power() - o2.get_power());
	}


}
