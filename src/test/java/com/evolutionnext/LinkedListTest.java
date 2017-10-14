package com.evolutionnext;

import javaslang.collection.List;
import javaslang.collection.Queue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkedListTest {
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
        assertThat(singleLinkedList).isEqualTo(List.of(1,2,3));
        assertThat(result).isEqualTo(List.of(0,1,2,3));
    }

    @Test
    public void testAppend() {
        List<Integer> original = List.of(1, 2, 3);
        List<Integer> result = original.append(4);
        assertThat(result).isEqualTo(List.of(1,2,3,4));
    }

    @Test
    public void testAppendWithFoldRight() {
        List<Integer> original = List.of(1, 2, 3);
        List<Integer> foldRight = original.foldRight(List.of(4), (integer, integers) -> {
            List<Integer> result = integers.prepend(integer);
            System.out.format("next: %2d; list: %-13s; result: %s\n", integer, integers, result);
            return result;
        });

        assertThat(foldRight).isEqualTo(List.of(1,2,3,4));
    }

}
