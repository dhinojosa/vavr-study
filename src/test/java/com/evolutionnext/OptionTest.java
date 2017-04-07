package com.evolutionnext;

import javaslang.control.Option;
import org.junit.Test;

import static javaslang.API.For;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionTest {

    @Test
    public void testOption() throws Exception {
        Option<String> option1 = Option.none();
        Option<String> option2 = Option.of("Foo");

        assertThat(option1.getOrElse("None")).isEqualTo("None");
        assertThat(option2.getOrElse("None")).isEqualTo("Foo");
    }

    @Test
    public void testOptionMap() throws Exception {
        Option<Integer> option2 = Option.of("Foo").map(x -> x.length());
        assertThat(option2.getOrElse(-1)).isEqualTo(3);
    }
}

