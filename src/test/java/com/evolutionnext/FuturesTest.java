package com.evolutionnext;

import io.vavr.CheckedFunction0;
import io.vavr.concurrent.Future;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                Thread.sleep(5000);
                return 5 + 3;
            }
        };

        System.out.println("In test:" +
                Thread.currentThread().getName());
        java.util.concurrent.Future<Integer> future =
                fixedThreadPool
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
                Thread.sleep(4000);
                return 5 + 3;
            }
        };

        java.util.concurrent.Future<Integer> future =
                executorService
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
        ExecutorService executorService =
                Executors.newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService,
                () -> {
                    Thread.sleep(5000);
                    return 50 + 10;
                });

        assertThat(future.map(x -> x + 100).get()) //block
                .isEqualTo(160);
    }

    @Test
    public void testFutureSuccessFailureAsync() throws Exception {
        ExecutorService executorService = Executors
                .newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService,
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(5000);
                    return 50 + 10;
                });
        future.onSuccess(x -> {
            System.out.println("In Success Block");
            System.out.println(Thread.currentThread().getName());
            assertThat(x).isEqualTo(60);
        });
        future.onFailure(t -> {
            System.out.println("In Failure Block");
            System.out.println(Thread.currentThread().getName());
            fail(t.getMessage());
        });

        System.out.println("Proved Asynchronous");
        Thread.sleep(6000);
    }
}
