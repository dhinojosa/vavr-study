package com.evolutionnext;

import javaslang.CheckedFunction1;
import javaslang.CheckedFunction2;
import javaslang.CheckedFunction3;
import javaslang.test.Arbitrary;
import javaslang.test.Gen;
import javaslang.test.Property;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class PropertyTest {

    // Source : http://deron.meranda.us/data/popular-both-first.txt
    // Source : http://deron.meranda.us/data/popular-last.txt

    @Before
    public void setUp() throws IOException {
        URL url = new URL("http://deron.meranda.us/data/popular-both-first.txt");
        url.openConnection();
    }


    @Test
    public void testSimplePropertyOfIntegersCommutative() throws Exception {
        Arbitrary<Integer> arbitraryIntegers = Arbitrary.integer();
        Property.def("a + b == b + a")
                .forAll(arbitraryIntegers, arbitraryIntegers)
                .suchThat((o, o2) -> {
                    System.out.format("o: %d, o2: %d\n", o, o2);
                    return o + o2 == o2 + o;
                 })
                .check()
                .assertIsSatisfied();
    }


    @Test
    public void testSimplePropertyOfIntegersNonCommutative() throws Exception {
        Arbitrary<Integer> arbitraryIntegers = Arbitrary.integer();
        Property.def("a - b == b - a")
                .forAll(arbitraryIntegers, arbitraryIntegers)
                .suchThat(new CheckedFunction2<Integer, Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer o, Integer o2) throws Throwable {
                        return o - o2 == o2 - o;
                    }
                }).check().assertIsSatisfied();
    }

    @Test
    public void testFirstNameAndLastNameConjunction() throws Exception {
        Gen<Character> minAToZ = Gen.choose((char)97, (char)122);
        Gen<Character> capAToZ = Gen.choose((char)65, (char)90);
        Arbitrary<Integer> integers = Arbitrary.integer();
        Arbitrary<String> alphaStrings = Arbitrary.string(Gen.oneOf(minAToZ, capAToZ));
        Property.def("Employee(firstName, lastName).fullName() == firstName + ' ' + lastName")
                .forAll(alphaStrings, alphaStrings, integers)
                .suchThat(new CheckedFunction3<String, String, Integer, Boolean>() {
                    @Override
                    public Boolean apply(String s, String s2, Integer i) throws Throwable {
                        Employee employee = new Employee(s, s2, i);
                        return employee.getFullName().equals(s + " " + s2);
                    }
                }).check().assertIsSatisfied();
    }


    //tell venkat about sandwich
    //.sout
}
