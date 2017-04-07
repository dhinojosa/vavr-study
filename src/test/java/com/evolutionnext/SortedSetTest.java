package com.evolutionnext;

import javaslang.collection.SortedSet;
import javaslang.collection.TreeSet;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SortedSetTest {

    @Test
    public void testSortedSet() throws Exception {
        SortedSet<String> sortedSet = TreeSet.of("foo", "bar", "baz", "qux", "quux", "quuz");
        SortedSet<String> newSortedSet = sortedSet.add("bam");
        assertThat(newSortedSet.size()).isEqualTo(7);
        assertThat(newSortedSet.contains("bam")).isEqualTo(true);
    }

    @Test
    public void testSortedSetMap() throws Exception {
        SortedSet<String> sortedSet = TreeSet.of("foo", "bar", "baz", "qux", "quux", "quuz");
        assertThat(sortedSet.map(String::length).filter(x -> x > 3).size()).isEqualTo(1);
    }
}
