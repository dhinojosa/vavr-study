package com.evolutionnext;


import io.vavr.Tuple2;
import io.vavr.collection.Queue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueueTest {

    @Test
    public void testSimpleQueue() throws Exception {
        Queue<Integer> queue = Queue.of(1, 2, 3, 4);
        Queue<Integer> newQueue = queue.enqueue(5,6,7,8);
        assertThat(newQueue).isEqualTo(Queue.of(1,2,3,4,5,6,7,8));
        assertThat(queue).isEqualTo(Queue.of(1,2,3,4));
    }

    @Test
    public void testSimpleDequeue() throws Exception {
        Queue<Integer> queue = Queue.of(1, 2, 3, 4);
        Queue<Integer> newQueue = queue.enqueue(5,6,7,8);
        Tuple2<Integer, Queue<Integer>> taken = newQueue.dequeue();
        assertThat(taken._1).isEqualTo(1);
        assertThat(taken._2).isEqualTo(Queue.of(2,3,4,5,6,7,8));

    }
}
