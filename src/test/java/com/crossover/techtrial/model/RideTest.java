package com.crossover.techtrial.model;


import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class RideTest {
	
	private static final Class<Ride> rideClass = Ride.class;

	@Test
	public void test() {
		Assertions.assertPojoMethodsFor(rideClass).testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER).areWellImplemented();
        Assertions.assertPojoMethodsFor(rideClass).testing(Method.HASH_CODE).areWellImplemented();
        Assertions.assertPojoMethodsFor(rideClass).testing(Method.EQUALS).areWellImplemented();

	}

}
