package com.evolutionnext;

import javaslang.concurrent.Future;
import javaslang.control.Try;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javaslang.API.*;
import static javaslang.Patterns.Failure;
import static javaslang.Patterns.Success;
import static javaslang.Predicates.instanceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class FuturesTest {

    @Test
    public void testBasicFuture() throws ExecutionException,
            InterruptedException {
        ExecutorService fixedThreadPool =
                Executors.newFixedThreadPool(5);

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Inside ze future: " +
                        Thread.currentThread().getName());
                System.out.println("Future priority: " +
                        Thread.currentThread()
                              .getPriority());
                Thread.sleep(10000);
                return 5 + 3;
            }
        };

        System.out.println("In test:" +
                Thread.currentThread().getName());
        System.out.println("Main priority" +
                Thread.currentThread().getPriority());
        java.util.concurrent.Future<Integer> future = fixedThreadPool
                .submit(callable);

        //This will block
        Integer result = future.get(); //block
        System.out.println("result = " + result);
    }


    /**
     * Demo 2 : Async the Old Way
     */
    @Test
    public void testBasicFutureAsync() throws ExecutionException,
            InterruptedException {
        ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 5 + 3;
            }
        };

        java.util.concurrent.Future<Integer> future = executorService
                .submit(callable);

        //This will not block
        while (!future.isDone()) {
            System.out.println("I am doing something" +
                    " else on thread: " +
                    Thread.currentThread().getName());
        }

        Integer result = future.get(); //Is done already
        System.out.println("result = " + result);
    }

    @Test
    public void testFuture() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService,
                () -> {
                    Thread.sleep(5000);
                    return 50 + 10;
                });

        assertThat(future.get()).isEqualTo(60);
    }

    @Test
    public void testFutureSuccessFailureAsync() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService,
                () -> {
                    Thread.sleep(5000);
                    return 50 + 10;
                });

        assertThat(future.get()).isEqualTo(60);
        future.onSuccess(x -> assertThat(x).isEqualTo(60));
        future.onFailure(t -> fail(t.getMessage()));
        Thread.sleep(6000);
    }
}
