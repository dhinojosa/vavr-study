package com.evolutionnext;

import javaslang.control.Option;
import org.junit.Test;

import java.util.Optional;

import static javaslang.API.For;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionTest {

    @Test
    public void testOption() throws Exception {
        Option<String> option1 = Option.none();
        Option<String> option2 = Option.of("Foo");
        assertThat(option1.getOrElse("Nope"))
                .isEqualTo("Nope");
        assertThat(option2.getOrElse("Nope"))
                .isEqualTo("Foo");
    }

    @Test
    public void testOptionSerialization() throws Exception {
        EmployeeWithVavr emp = new EmployeeWithVavr("Harry", "S", "Truman", 60);
        EmployeeWithVavr emp2 = new EmployeeWithVavr("Daniel", "Hinojosa", 22);
        assertThat(emp.getMiddleName().getOrElse("No Middle Name")).isEqualTo("S");
        assertThat(emp2.getMiddleName().getOrElse("No Middle Name")).isEqualTo("No Middle Name");
    }

    @Test
    public void testOptionMap() throws Exception {
        Option<Integer> option2 = Option.of("Foo")
                                        .map(String::length);
        assertThat(option2.getOrElse(-1)).isEqualTo(3);
    }
}

