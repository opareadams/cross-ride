package com.crossover.techtrial.model;


import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;


public class PersonTest {
	
	private static final Class<Person> personClass = Person.class;

	@Test
	public void test() {
		Assertions.assertPojoMethodsFor(personClass).testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER).areWellImplemented();
        Assertions.assertPojoMethodsFor(personClass).testing(Method.HASH_CODE).areWellImplemented();
        Assertions.assertPojoMethodsFor(personClass).testing(Method.EQUALS).areWellImplemented();

	}

}
