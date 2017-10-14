package com.evolutionnext;

import io.vavr.Tuple;
import io.vavr.collection.Stream;
import org.junit.Test;

public class StreamTest {
    @Test
    public void testStream() throws Exception {
        Stream.from(1).map(x -> Tuple.of(x, x + 1, x + 2)).take(4);
    }
}
