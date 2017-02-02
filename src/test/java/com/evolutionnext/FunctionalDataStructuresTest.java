package com.evolutionnext;

import javaslang.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalDataStructuresTest {
    @Test
    public void testSingleListCreation() {
        //Careful, this in the package javaslang.collection.List
        List<Integer> singleLinkedList = List.of(1, 2, 3, 4, 5);
        assertThat(singleLinkedList).hasSize(5);
    }

    @Test
    public void testPrepend() {
        List<Integer> singleLinkedList = List.of(1, 2, 3);
        List<Integer> result = singleLinkedList.prepend(0);
        assertThat(result).isEqualTo(List.of(0,1,2,3));
    }

    @Test
    public void testAppend() {
        List<Integer> original = List.of(1, 2, 3);
        List<Integer> result = original.append(4);
        assertThat(result).isEqualTo(List.of(1,2,3,4));
    }
}
