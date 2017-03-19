package com.evolutionnext;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class TuplesFunctionsTest {
    @Test
    public void testTupleMappable() throws Exception {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Foo", 4);
        Tuple2<String, Integer> result = tuple2.map((i, j) -> Tuple.of(i + "Bar", j + 4));
        assertThat(result).isEqualTo(Tuple.of("FooBar", 8));
    }

    @Test
    public void testTupleTransform() throws Exception {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Foo", 4);
        List<Integer> transformation = tuple2.transform(new BiFunction<String, Integer, List<Integer>>() {
            @Override
            public List<Integer> apply(String s, Integer integer) {
                return List.of(s.length(), integer * 2);
            }
        });
        assertThat(transformation).isEqualTo(List.of(3, 8));
    }
}
