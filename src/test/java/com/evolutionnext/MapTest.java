package com.evolutionnext;

import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.junit.Test;

public class MapTest {

    @Test
    public void testMap() throws Exception {
        Map<Integer, String> nums = HashMap.of(
                Tuple.of(1, "One"),
                Tuple.of(2, "Two"),
                Tuple.of(3, "Three"));
        Option<String> stringOption = nums.get(10);
        stringOption.getOrElse("Unknown");
        stringOption.getOrElse("Unknown");

    }
}
