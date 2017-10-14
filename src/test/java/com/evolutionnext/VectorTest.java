package com.evolutionnext;

import io.vavr.collection.Vector;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VectorTest {
    @Test
    public void testVectorAdd() {
        Vector<Integer> vector = Vector.of(3,1,5);
        Vector<String> result = vector.append(10).map(integer -> "aaa" + integer);
        assertThat(result).isEqualTo(Vector.of("aaa3", "aaa1", "aaa5", "aaa10"));
    }
}
