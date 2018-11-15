package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myMath.Monom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
class MonomTest {
	static Monom m1;
	static Monom m2;
	@Test
	void constractor_test() {

		m1=new Monom("x");
		m2=new Monom("-x");
		Monom m3=new Monom("x");
		Monom m4=new Monom("3*x");
		Monom m5=new Monom("3*X");
		Monom m6=new Monom("0x^0");
		assertNotNull("not absordeb in the monom",m1);
		assertNotNull("not absordeb in the monom",m2);
		assertNotNull("not absordeb in the monom",m3);
		assertNotNull("not absordeb in the monom",m4);
		assertNotNull("not absordeb in the monom",m5);
		assertNotNull("not absordeb in the monom",m6);
	}
	@Test
	void constractor_badCase_test() throws IllegalArgumentException {
		try {
			m1=new Monom("+");
			m2=new Monom("-");
			Monom m3=new Monom("hello");
			Monom m4=new Monom("2x^76474554748*");
			Monom m5=new Monom("2x^76474554748*");
		}
		catch(Exception e) {
			assertEquals("the input is not valid, please try again.",e);
		}
	}
	@Test
	void derivative_test() {
		m1=new Monom("x");
		m1= m1.derivative();
		Monom exep= new Monom("+1");
		assertThat(m1.toString(), is(equalTo(exep.toString())));
		m2=new Monom("-x^5");
		m2= m2.derivative();
		Monom exep2= new Monom("-5x^4");
		assertThat(m2.toString(), is(equalTo(exep2.toString())));
	}
	@Test
	void get_power_test() {
		m1=new Monom ("3x");
		assertThat(m1.get_power(), is(equalTo(1)));
		m2=new Monom ("1000X^6");
		assertThat(m2.get_power(), is(equalTo(6)));
	}
	@Test
	void get_coefficient_test() {
		m1=new Monom ("0x");
		assertThat(m1.get_coefficient(), is(equalTo(0.0)));
		m2=new Monom ("0.9X^6");
		assertThat(m2.get_coefficient(), is(equalTo(0.9)));
	}
	@Test
	void set_coefficient_test() {
		m1=new Monom ("6x");
		m1.set_coefficient(8);
		assertThat(m1.get_coefficient(), is(equalTo(8.0)));
		m2=new Monom ("0.9X^6");
		m2.set_coefficient(0);
		assertThat(m2.get_coefficient(), is(equalTo(0.0)));
	}
	@Test
	void set_power_test() {
		m1=new Monom ("6x");
		m1.set_power(7);
		assertThat(m1.get_power(), is(equalTo(7)));
		m2=new Monom ("0.9X^6");
		m2.set_power(0);
		assertThat(m2.get_power(), is(equalTo(0)));	
	}
	@Test
	void f_test() {
		m1=new Monom ("6x");
		double ans=	m1.f(2);
		assertThat(ans, is(equalTo(12.0)));
		m2=new Monom ("0.9X^6");
		ans=m2.f(2);
		assertThat(ans, is(equalTo(57.6)));
	}
	@Test
	void multiply_test() {
		m1=new Monom ("6x");
		m2=new Monom ("0.9X^6");
		m1.multiply(m2);
		assertThat(m1.toString(), is(equalTo("+5.4x^7")));
	}
	@Test
	void add_test() {
		m1=new Monom ("6x");
		m2=new Monom ("0.9X");
		m1.add(m2);
		assertThat(m1.toString(), is(equalTo("+6.9x")));
	}
	@Test
	void add_badCase_test() {
		try {
			m1=new Monom ("6x");
			m2=new Monom ("0.9X^3");
			m1.add(m2);
		}
		catch(Exception e) {
			assertEquals("Exponents must match in order to add",e);
		}
	}
	@Test
	void toString_test() {
		m1=new Monom ("6x");
		m2=new Monom ("0x");
		assertThat(m1.toString(), is(equalTo("+6x")));
		assertThat(m2.toString(), is(equalTo("0")));
	}
}
