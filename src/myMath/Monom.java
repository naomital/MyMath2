
package myMath;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and b is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Adi and Naomi.
 *@since 01/11/2018
 *@version num:1;
 *@param  _coefficient,gives the coefficient of the Monom, the number can be real number,from form double.
 *@param _power,gives the power of the Monom, the number can be integer, from form int.
 *
 */
public class Monom implements function{

	private double _coefficient;
	private int _power;
	/**
	 * this function are constructor Which gets variables from form int and double.
	 * @param a,is the number that the user gives to the coefficient.
	 * @param b,is the number that the user gives to the power.
	 */
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * this function are the empty constructor, that Enters zero into the variables.
	 */
	public Monom(){
		this.set_coefficient(0);
		this.set_power(0);
	}
	/**
	 * this function are Constructor Copier.
	 * @param ot, Which is another monom that we will copy deeply into our monom.
	 */

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	/**
	 * this function are Constructor Which gets variables from form String
	 * and Turns the string into numbers and puts them in mono values.
	 * @param str, is the string that the user give and present the monom.
	 * @exception try and catch, it will throw exeption if the user will try to insert string that not representative monom like the form.
	 * 
	 */

	public Monom(String str) {

		try {
			str = str.toLowerCase();
			if(str.contains("x")) {
				int index = str.indexOf("x");

				if(str.length() - 1 == index) { // there is no power
					this.set_power(1);
					if(index == 1) {
						if(str.charAt(0) == '-') this.set_coefficient(-1);
						else if(str.charAt(0) == '+') this.set_coefficient(1);
						else { // for example: a*x
							this.set_coefficient(Double.parseDouble(str.substring(0, index)));
						}

					}
					else if(index == 0) {
						this.set_coefficient(1);
					}
					else { // for example: a*x
						this.set_coefficient(Double.parseDouble(str.substring(0, index)));
					}
				}
				else if(str.charAt(index+1) == '^') { //for example: x^b
					if(str.charAt(index+2)=='-') {
						throw new Exception();
					}
					if(index == 1) {
						if(str.charAt(0) == '-') this.set_coefficient(-1);
						else if(str.charAt(0)=='+') this.set_coefficient(1);
					}
					else if(index == 0) this.set_coefficient(1);
					else this.set_coefficient(Double.parseDouble(str.substring(0, index)));
					this.set_power(Integer.parseInt(str.substring(index+2)));	
				}
				else {//for example: ax^b
					if(str.charAt(index+2)=='-') {
						throw new Exception();
					}
					String[] s = str.split("x");
					s[1] = s[1].substring(1);
					this._coefficient = Double.parseDouble(s[0]);
					this._power = Integer.parseInt(s[1]);
				}
			}
			else {
				this._coefficient = Double.parseDouble(str);
				this._power = 0;
			}
		}
		catch(Exception e) {
			System.err.println("the input is not valid, please try again.");
		}
	}
	/**
	 * the function derivative calculate the derivative of our monom,
	 * For example: (ax^b)'=a*bx^b-1.
	 * @return the derivative of this Monom.
	 */

	public Monom derivative() {
		if(this.get_power() == 0) return new Monom(0,0);
		Monom derivative = new Monom(_coefficient * _power, _power - 1);
		return derivative;

	}
	/**
	 * 
	 * @return the value_power, without the ability to change the value.
	 */
	public int get_power() {
		return this._power;
	}
	/**
	 * 
	 * @return the value_coefficient, without the ability to change the value.
	 */
	public double get_coefficient() {
		return this._coefficient;
	}
	/**
	 * function set_coefficient gives the ability to change the value of the variable outside the class.
	 * @param a are the new value of _coefficient in this Monom.
	 */
	public void set_coefficient(double a){
		this._coefficient = a;
	}
	/**
	 * function set_coefficient gives the ability to change the value of the variable outside the class. 
	 * @param p are the new value of _power in this Monom.
	 */
	public void set_power(int p) {
		this._power = p;
	}
	/**
	 * function f Calculates Monos Y.
	 *@param x  it gets a value-x inserted into X.
	 * @return the calculation of the mono with the value given by the user.
	 */
	@Override
	public double f(double x) {
		x = _coefficient * Math.pow(x, _power);
		// TODO Auto-generated method stub
		return x;
	} 
	/**
	 * function add Makes a connection between two monomers.
	 * @param other, the other monom That connect to our monom
	 * @exception exception if the monom that trying to connect is not as power as this mono. 
	 */
	public void add(Monom other){
		try {
			if(this._power == other._power){
				this._coefficient += other._coefficient;}
			else throw new Exception();
		}catch(Exception e) {
			System.err.println("Exponents must match in order to add");
		}
	}
	/**
	 * function multiply Calculates multiplication between two monomers.
	 * @param other the other monom that Multiplied with this mono.
	 */
	public void multiply(Monom other){
		this._coefficient *= other._coefficient;
		this._power += other._power;
	}
	/**
	 * function toString  Turns the monom into a string that can be printed.
	 */
	@Override
	public String toString() {
		String ans = "";
		if(this._coefficient == 0.0) return "0";
		if(this._coefficient>0)ans="+";
		if(_coefficient == 1 && _power>0) ans+="";
		else if(_coefficient == -1 && _power>0) ans+="-";
		else {
			if(_coefficient % 1 == 0) ans+=""+(int) _coefficient;
			else ans += ""+ _coefficient;
		}
		if(_power==1)ans+="x";
		if(_power>1)ans+="x^"+_power;


		return ans;
	}

}
