package com.evolutionnext;

import javaslang.collection.SortedSet;
import javaslang.collection.TreeSet;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SortedSetTest {

    @Test
    public void createSortedSet() throws Exception {
        SortedSet<String> sortedSet = TreeSet.of("foo", "bar", "baz", "qux", "quux", "quuz");
        SortedSet<String> newSortedSet = sortedSet.add("bam");
        assertThat(newSortedSet.size()).isEqualTo(7);
    }
}
