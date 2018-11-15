package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myMath.Polynom;
import myMath.Polynom_able;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

class PolynomTest {
	static Polynom_able p1;
	static Polynom_able p2;
	static Polynom_able expected;

	@Test
	void addTest() {

		//#1:
		p1 = new Polynom("5x^2");
		p2 = new Polynom("3x^2");
		Polynom expected = new Polynom("8x^2");
		p1.add(p2);
		assertThat(p1.toString(), is(equalTo(expected.toString())));

		//#2:
		p1 = new Polynom("-2x^3 + 2.2X - 0.5");
		p2 = new Polynom("+x^4 - 5X^3 - 3");
		expected = new Polynom("x^4-7x^3+2.2x-3.5");
		p1.add(p2);
		assertThat(p1.toString(), is(equalTo(expected.toString())));

		//#3:
		p1 = new Polynom("2x^3 + -2.2X - 6");
		p2 = new Polynom("x^4 + -5X^3 + 3");
		expected = new Polynom("x^4-3x^3-2.2x-3");
		p1.add(p2);
		assertThat(p1.toString(), is(equalTo(expected.toString())));

		//#4:
		p1 = new Polynom("2x^3");
		p2 = new Polynom("0");
		expected = new Polynom("2x^3");
		p1.add(p2);
		assertThat(p1.toString(), is(equalTo(expected.toString())));
	}

	@Test
	void validInputsTest() {
		//#1:
		p1 = new Polynom("0x^0");
		assertThat(p1.toString(), is(equalTo("0")));

		//#2:
		p1 = new Polynom("-1X^1");
		assertThat(p1.toString(), is(equalTo("-x")));


		//#3:
		p1 = new Polynom("+5x^2 + -3x");
		assertThat(p1.toString(), is(equalTo("5x^2-3x")));

	}

	@Test
	void failedInputsTest() {
		//#1:
		try {
		p1 = new Polynom("Hello");
		p1 = new Polynom("2x^-4");
		p1 = new Polynom("@#$%");
		p1 = new Polynom("2x^0.4");
		}catch(Exception e) {
			assertEquals("the input is not valid, please try again.", e);
		}
	}

	@Test
	void substractTest() {

		//#1:
		p1 = new Polynom("-3x^2+-3.2x-0.3");
		p2 = new Polynom("-6x^3+-1.2x-0.3");
		expected = new Polynom("6x^3-3x^2-2x");
		p1.substract(p2);
		assertThat(p1.toString(),is(equalTo(expected.toString())));

		//#2:
		p1 = new Polynom("-0.6");
		p2 = new Polynom("-0.4");
		expected = new Polynom("-0.19999999999999996");
		p1.substract(p2);
		assertThat(p1.toString(),is(equalTo(expected.toString())));

		//#3: in case of subtract that create the zero polynomial:
		p1 = new Polynom("-x^5+6x^3-2x+8");
		p2 = new Polynom("-x^5+6x^3-2x+8");
		p1.substract(p2);
		assertThat(p1.toString(),is(equalTo("0")));

	}


	@Test
	void multiplyTest() {
		//#1: case of multiply our polynomial by -1: 
		p1 = new Polynom("-x^5+6x^3-2x+8");
		p2 = new Polynom("-1");
		expected = new Polynom("x^5-6x^3+2x-8");
		p1.multiply(p2);
		assertThat(p1.toString(),is(equalTo(expected.toString())));

		//#2: case of multiply our polynomial by 0: 
		p1 = new Polynom("-x^5+6x^3-2x+8");
		p2 = new Polynom("0");
		p1.multiply(p2);
		assertThat(p1.toString(),is(equalTo("0")));

		//#3: case of multiply our polynomial by other polynomial: 
		p1 = new Polynom("-x^5+6x^3-2x+8");
		p2 = new Polynom("-2x^3");
		expected = new Polynom("2x^8-12x^6+4x^4-16x^3");
		p1.multiply(p2);
		assertThat(p1.toString(),is(equalTo(expected.toString())));

	}

	@Test
	void copyTest() {
		//#1: check Deep-Copy of p1 to p2: 
		p1 = new Polynom("-x^5+6x^3-2x+8.1");
		p2 = p1.copy();
		assertNotSame(p2,p1);

		//#2: check Deep-Copy of fractions in polynomial: 
		p1 = new Polynom("-0.4");
		p2 = p1.copy();
		assertNotSame(p2,p1);
	}

	@Test
	void derivativeTest() {
		//#1: 
		p1 = new Polynom("-x^5+6x^3-2x+8.1");
		p1 = p1.derivative();
		assertThat(p1.toString(),is(equalTo("-5x^4+18x^2-2")));

		//#2: case -x^1:
		p1 = new Polynom("-x^1");
		p1 = p1.derivative();
		assertThat(p1.toString(),is(equalTo("-1")));

		//#3: case -x^0:
		p1 = new Polynom("-x^0");
		p1 = p1.derivative();
		assertThat(p1.toString(),is(equalTo("0")));

		//#4: case that the power is negative --> error:
		try {
			p1 = new Polynom("-0.5x^-4");
			p1 = p1.derivative();
		} catch(Exception e) {
			assertEquals("the input is not valid, please try again.", e);
		}
	}
	
	@Test
	void fxTest() {
		//#1: in case that x = 1:
		p1 = new Polynom("-x^5+6x^3-2x+8.1");
		Double ans = p1.f(1);
		assertThat(ans,is(equalTo(11.1)));

		//#2: in case that x = 0:
		p1 = new Polynom("-x^5+6x^3-2x+8.1");
		ans = p1.f(0);
		assertThat(ans,is(equalTo(8.1)));


	}


	@Test
	void areaTest() {
		//**area**:
		double eps = 0.00001;

		p1 = new Polynom("x^3+2x^2-x");
		Double ans = p1.area(-2.5, 2, eps);
		assertTrue(ans > 11.2);
		assertTrue(ans<11.24);

		p1 = new Polynom("x^2+3x+1");
		ans = p1.area(-2.618, -0.382, eps);
		assertTrue(ans > 1.86);
		assertTrue(ans < 1.867);

		p1 = new Polynom("x^3+1");
		ans = p1.area(-2,2, eps);
		assertTrue(ans > 9.49);
		assertTrue(ans < 9.51);

		p1 = new Polynom("x^2+3x+1");
		ans = p1.area(0, 1, eps);
		assertTrue(ans > 2.83);
		assertTrue(ans < 2.84);

	}

	@Test
	void rootTest() {
		double eps = 0.00001;
		//#1:
		p1 = new Polynom("x^2+3x+1");
		Double ans = p1.root(-3, -1.5, eps);
		assertTrue(ans < -2.61);
		assertTrue(ans > -2.62);
		//#2:
		p1 = new Polynom("-8x^3+9x+1");
		ans = p1.root(-1.11,-0.6,0.000001);
		assertTrue(ans < -0.98);
		assertTrue(ans > -1.0);
	}



}
