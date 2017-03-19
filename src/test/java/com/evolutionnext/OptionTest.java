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
    public void testOptionMonadic() {
        Option<Integer> option1 = Option.of(100);
        Option<Integer> option2 = Option.of(0);

        Option<Integer> aFor = For(option1, v1 ->
                                     For(option2).yield(v2 -> v1 + v2)).toOption();

        assertThat(aFor.getOrElse(-1)).isEqualTo(100);
    }
}

