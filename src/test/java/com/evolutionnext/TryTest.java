package com.evolutionnext;

import javaslang.Function1;
import javaslang.concurrent.Future;
import javaslang.control.Try;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class TryTest {

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testTryWithArithmeticException() {
        Try<Integer> tryOf = Try.of(() -> 12 / 0);
        Integer answer = tryOf.getOrElse(-1);
        assertThat(answer).isEqualTo(-1);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testTryWithThreadSleepSmell() {

        Function1<Integer, Integer> f1 = x -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return x + 1;
        };

        Integer result = f1.apply(4);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void testTryWithThreadCleaner() {
        Function1<Integer, Try<Integer>> fun = x -> Try.of(() -> {
            Thread.sleep(5000);
            return x + 1000;
        });

        Integer result = fun.apply(4).getOrElse(-1);
        //Not at all consistent since interrupts can occur randomly
        assertThat(result).isEqualTo(1004);
    }


    @Test
    public void testFutureWithTry() throws Exception {
        ExecutorService executorService =
                Executors.newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService,
                () -> {
                    Thread.sleep(5000);
                    return 50 + 10;
                });
        future.onComplete(new Consumer<Try<Integer>>() {
            @Override
            public void accept(Try<Integer> t) {
                assertThat
                        (t.getOrElse(-1)).isEqualTo(60);
            }
        });
        Thread.sleep(6000);
    }
}
