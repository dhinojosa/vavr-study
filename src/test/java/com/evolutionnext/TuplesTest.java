package com.evolutionnext;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TuplesTest {
    @Test
    public void testTuple2() throws Exception {
        Tuple2<String, Integer> tuple2 =
                new Tuple2<>("Foo", 4);
        assertThat(tuple2._1()).isEqualTo("Foo");
        assertThat(tuple2._2()).isEqualTo(4);
    }

    @Test
    public void testTuple2CreationAlternative() throws Exception {
        Tuple2<String, Integer> tuple2 =
                Tuple.of("Foo", 4);
        assertThat(tuple2._1()).isEqualTo("Foo");
        assertThat(tuple2._2()).isEqualTo(4);
    }

    @Test
    public void testTuple3() throws Exception {
        Tuple3<String, Integer, Double> tuple3 =
                Tuple.of("Foo", 30, 303.00);
        assertThat(tuple3._1).isEqualTo("Foo");
        assertThat(tuple3._2).isEqualTo(30);
        assertThat(tuple3._3).isEqualTo(303.00);
    }
}
