package com.evolutionnext;


import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TuplesFunctionsTest {
    @Test
    public void testTupleMappable() throws Exception {
        Tuple2<String, Integer> tuple2 =
                new Tuple2<>("Foo", 4);
        Tuple2<String, Integer> result = tuple2.map((i, j) ->
                Tuple.of(i + "Bar", j + 4));
        assertThat(result).isEqualTo(Tuple.of("FooBar", 8));
    }
}
