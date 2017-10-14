package com.evolutionnext;

import io.vavr.CheckedFunction2;
import io.vavr.CheckedFunction3;
import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import io.vavr.test.Property;
import org.junit.Test;

public class PropertyTest {

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
                .suchThat((CheckedFunction2<Integer, Integer, Boolean>)
                        (o, o2) -> o - o2 == o2 - o).check().assertIsSatisfied();
    }

    @Test
    public void testFirstNameAndLastNameConjunction() throws Exception {
        Gen<Character> minAToZ = Gen.choose((char)97, (char)122);
        Gen<Character> capAToZ = Gen.choose((char)65, (char)90);
        Arbitrary<Integer> integers = Arbitrary.integer();
        Arbitrary<String> alphaStrings = Arbitrary.string(Gen.oneOf(minAToZ, capAToZ));
        Property.def("EmployeeWithVavr(firstName, lastName).fullName() == firstName + ' ' + lastName")
                .forAll(alphaStrings, alphaStrings, integers)
                .suchThat((CheckedFunction3<String, String, Integer, Boolean>) (s, s2, i) -> {
                    EmployeeWithVavr employeeWithVavr = new EmployeeWithVavr(s, s2, i);
                    return employeeWithVavr.getFullName().equals(s + " " + s2);
                }).check().assertIsSatisfied();
    }
}
