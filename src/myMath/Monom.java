
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{

	public Monom(double a, int b){
		if(b<0) throw new RuntimeException("Unable to insert negative power, Please change it and try again");
		this.set_coefficient(a);
		this.set_power(b);
	}
	
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	// ***************** add your code below **********************

	public int get_power() {
		return _power;
	}
	
	public double get_coefficient() {
		return _coefficient;
	}

	@Override
	public double f(double x) {
		return _coefficient*Math.pow(x, _power);
	}

	public void derivative() {
		if(_power==0) _coefficient=0;
		else {
			_coefficient *=_power;
			_power--;
		}
	}

	public void add (Monom a) {
		if(a._power!=_power) throw new RuntimeException("Please insert monom with similar power for adding: "+_power);
		else _coefficient +=a.get_coefficient();
	}

	public void multiply(Monom a) {
		_power +=a._power;
		_coefficient *= a.get_coefficient();
	}

	public String toString() {
		if(_power==0) return ""+_coefficient;
		else if(_power==1) {
			if(_coefficient==1) return "x";
			else if(_coefficient==-1) return "-x";
			return _coefficient+"x";
		}
		else if(_coefficient==1) return "x"+"^"+_power;
		else if(_coefficient==-1) return "-x"+"^"+_power;
		return _coefficient+"x"+"^"+_power;
	}
	
	//****************** Private Methods and Data *****************
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	
	private void set_power(int p) {
		this._power = p;
	}
	
	private double _coefficient; 
	private int _power;
}
