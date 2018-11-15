package myMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * @param polynom is an array of monomers.
 * @param Monom_Comperator
 * @author Adi and Naomi
 *@since 01/11/2018
 *@version 1
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> polynom;
	private Monom_Comperator compByPower = new Monom_Comperator();

	/**
	 *  constructor that creates a zero polynomial
	 */

	public Polynom() {
		polynom = new ArrayList(0);
	}

	/**
	 * this function are Constructor Which gets variables from form String
	 * and Turns the string into numbers and puts them in array list of monoms.
	 * Sorting for monomets is done so that a function divides the sting into a pre-split array of + and - and 
	 * then sends the mono strings to the mono constructor.
	 *  The function deals with cases in which the input is recorded in the following forms example:
	 * ax^b+aX-a +-ax^b -a*x^b.
	 * @param str, is the string that the user give and present the monom.
	 * @param coeffList, array list of char that Which preserves the signs of the monomers because the split that will be done later will erase them.
	 * @param s, array list of string that Which keeps the sting divided into monomes.
	 * @exception try and catch, it will throw exeption if the user will try to insert string that not representative monom like the form.
	 * 
	 */
	public Polynom(String str) {
		this(); // call to the empty constructor.
		try {
			ArrayList<Character> coeffList = new ArrayList<Character>();
			str = str.replaceAll("\\s", "");
			str = str.replaceAll("\\*", "");
			if(str.charAt(0)=='-') { //  if the first Monom is negative - start from the second monom.
				coeffList.add('-');
				str = str.substring(1);
			}
			else {
				coeffList.add('+');
				if(str.charAt(0)=='+') str = str.substring(1);
			}
			for(int i=0; i<str.length(); i++) {
				if(str.charAt(i) == '+') {
					coeffList.add('+');
				}
				else if(str.charAt(i) == '-') coeffList.add('-');
			}
			String[] s = str.split("\\+|\\-");
			for(int i=0; i<coeffList.size(); i++) {
				if(coeffList.get(i) == '-') s[i] = "-" + s[i];
				else s[i] = "+" + s[i];
			}
			for(int i=0;i<s.length;i++) {
				if(s[i].length() == 1) { // for case:"+-" OR "-+"
					if(s[i].equals("-")) {
						if(s[i+1].charAt(0) == '-') s[i+1].replace('-', '+');
						else s[i+1].replace('+', '-');
					}
					continue;
				}
				Monom m =new Monom(s[i]);
				this.add(m);
			}
		} catch (Exception e) {
			System.err.println("the input is not valid, please try again.");
		}
	}
	/**
	 * this function are constructor Which initializes the array list of Polynom.
	 * @param size the size of the array list of the monoms.
	 */
	public Polynom(int size) {
		polynom = new ArrayList<Monom>(size);
	} 
	/**
	 * function f Calculates polynoms Y.
	 * @param x it gets a value-x inserted into X.
	 * @return the calculation of the polynom with the value given by the user.
	 */
	@Override
	public double f(double x) {
		double y = 0; 
		for(int i=0; i<polynom.size();i++) {
			y += polynom.get(i).f(x);
		}
		return y;
	}
	/**
	 * function add Combines two polynomials together
	 * @param p1 the other Polynom_able that Combines together whit this polynom.
	 * The function passes the second polynomial by iterator and sends it to the function add of the kind of monom.
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext())
			this.add(it.next());
	}
	/**
	 * functiom add Adds a monomonic to a polynomial in the event that there are two in the same power - connects them.
	 * @param m1 are monom we want to Put in this polynom.
	 */
	@Override
	public void add(Monom m1) {
		boolean flag = true;
		//if the polynom is empty or if the biggest power is less then m1 power
		if(polynom.size() == 0) { 
			if(m1.get_coefficient()!=0) {
				polynom.add(m1);
				//this.polynom.remove(m1);
			}
			flag = false;
		}
		//if the powers are equals
		else if(polynom.get(0).get_power() >= m1.get_power()) {
			int i = 0;
			while(i<=polynom.size()-1 && (m1.get_power() <= polynom.get(i).get_power()) && flag) {
				if(m1.get_power() == polynom.get(i).get_power()) {
					polynom.get(i).add(m1);
					if(polynom.get(i).get_coefficient()==0) {
						this.polynom.remove(polynom.get(i));
					}
					flag = false;
				}
				i++;
			}
		}
		if(flag) {
			polynom.add(m1);
		}

		Collections.sort(this.polynom, new Monom_Comperator());
	}


	/**
	 * function that Subtracting from this polynomial another polynomial.
	 * @param p2 the other Polynom_able.
	 */
	@Override
	public void substract(Polynom_able p2) {

		Iterator<Monom> it = p2.iteretor();
		while(it.hasNext()) {
			Monom current = it.next();
			current.set_coefficient(current.get_coefficient()*(-1));
			this.add(current);
		}
		Collections.sort(this.polynom, new Monom_Comperator());
	}
	/**
	 * function that Multiplies this polynomial with another polynomial able.
	 * @param p1 the other Polynom_able.
	 */
	@Override
	public void multiply(Polynom_able p1) { 
		Iterator<Monom> it = p1.iteretor();
		Polynom_able mul = new Polynom();
		while(it.hasNext()) {
			Monom current = it.next();
			for(int i=0; i<polynom.size(); i++) {
				Monom m1 = new Monom(polynom.get(i).get_coefficient() * current.get_coefficient(),polynom.get(i).get_power() + current.get_power());
				mul.add(m1);
			}
		}
		Iterator<Monom> it2 = mul.iteretor();
		polynom.removeAll(polynom);
		while(it2.hasNext()) {
			Monom current = it2.next();
			Monom m1 = new Monom(current.get_coefficient(),current.get_power());
			polynom.add(m1);
		}

	}
	/**
	 * function equal Checks whether this polynomial is equal to the next polynomial.
	 * @param p1 are the other Polynom_able.
	 * @return flg, Returns true if polynomials are equal and returns false if they are not equal
	 */
	public boolean equals(Polynom_able p1) {
		boolean flg=true;
		Iterator<Monom> it = p1.iteretor();
		for(int i=0;i<polynom.size()&&it.hasNext()&&flg;i++) {
			flg=false;
			if(polynom.get(i).equals(it.next()))flg=true;
		}
		return flg;
	}
	/**
	 *function that checks whether the polynomial is empty means that the entire array is filled only with values of 0
	 *@return ok If an empty polynomial sends a true if it is not empty sends a false.
	 */
	@Override
	public boolean isZero() { 
		boolean ok = true;
		Iterator<Monom> it = iteretor();
		while(it.hasNext()) {
			Monom term = it.next();
			if(term.get_coefficient() != 0)
				ok = false;
		}
		return ok;
	}
	/**
	 * function root The function makes approximations between two points on a polynomial.
	 *  The X must be approximately-in-must be one positive and one negative otherwise will throw an error.
	 *  @param x0, the first X.
	 *  @param x0, The second X.
	 *  @param eps the possible deviation.
	 *  @exception if we have not received one positive X and one negative X
	 *  @return Returns the X that is closest to the X axis.
	 */
	@Override
	public double root(double x0, double x1, double eps){
		double flg=(x0+x1)/2;
		if(f(x0)*f(x1)>0)	{
			throw new RuntimeException("The function root must have one positive value and one negative value.");
		}
		while(Math.abs(f(flg))>eps) {
			if(f(flg)>0) {
				if(f(x0)>0)x0=flg;
				else x1=flg;
			}
			else {
				if(f(x1)<0)x1=flg;
				else x0=flg;
			}
			flg=(x0+x1)/2;
		}
		return flg;
	}
	/**
	 * function copy Deeply copies this polynomial to the polynomial that we will insert into it.
	 * @return the new  Polynom_able.
	 */
	@Override
	public Polynom_able copy() {
		Polynom_able newPol = new Polynom();
		Iterator<Monom> it = iteretor();
		while(it.hasNext()) newPol.add(it.next());
		return newPol;
	}
	/**
	 *the function derivative calculate the derivative of our polynom,
	 * For example: (ax^b)'=a*bx^b-1.
	 * @return the derivative of this polynom.
	 */
	@Override
	public Polynom_able derivative() {
		Polynom_able DerivPol = new Polynom();
		Iterator<Monom> it = iteretor();
		while(it.hasNext()) DerivPol.add(it.next().derivative());
		return DerivPol;
	}
	/**
	 * function area Makes an integral of the polynomial according to the formula of Riemann integral.
	 * see more about the Riemann integral in the link -https://en.wikipedia.org/wiki/Riemann_integral.
	 * @param x0 the first x.
	 * @param x1 the secen x.
	 * @param eps the  length of the rectangle.
	 * @return count, The amount of the integral
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		if(x0>x1) {
			return 0;
		}
		double length=Math.abs(x1-x0);
		double count=0;
		while(length>0) {
			if(f(x0)>0) {
				count+=eps*f(x0);
			}
			else {
				count+=eps*(Math.abs(f(x0)));
			}
			x0=x0+eps;
			length=length-eps;
		}
		return count;
	}
	/**
	 * Iterator of monom. 
	 * @return A variable of iterator. 
	 */
	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return polynom.iterator();
	}
	/**
	 * function toString  Turns the polynom the array list of monos into a string that can be printed.
	 * He builds the shape of the polynomial but the shape of the monomers is sent to the tostring of the monos.
	 */
	public String toString()
	{
		if(isZero()) return "0";
		String ans = "";
		Iterator<Monom> it = this.polynom.iterator();
		while(it.hasNext())
		{
			Monom s = it.next();
			ans +=s.toString();
		}
		if(ans.charAt(0) == '+')
			ans = ans.substring(1);
		return ans;
	}






}