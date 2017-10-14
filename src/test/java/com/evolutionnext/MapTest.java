package com.evolutionnext;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.junit.Test;

public class MapTest {

    @Test
    public void testMap() throws Exception {
        Map<Integer, String> nums = HashMap.of(1, "One",
                2, "Two", 3, "Three");
        Option<String> stringOption = nums.get(10);
        stringOption.getOrElse("Unknown");
        stringOption.getOrElse("Unknown");

    }
}
