package com.evolutionnext;

import javaslang.concurrent.Future;
import javaslang.control.Try;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static javaslang.API.*;
import static javaslang.Predicates.*;
import static javaslang.Patterns.*;

public class FuturesTest {

    @Test
    public void testFutureMapping() throws Exception {
        Future<Integer> future = Future.of(Executors.newFixedThreadPool(4), new Try.CheckedSupplier<Integer>() {
            @Override
            public Integer get() throws Throwable {
                Thread.sleep(5000);
                return 50 + 10;
            }
        });

        Future<Integer> future2 = future.map(x -> x * 100);

        future2.onComplete((Try<Integer> integers) -> Match(integers).of(
                Case(Success($(instanceOf(Integer.class))), integer -> run(() -> System.out.println(integer))),
                Case(Failure($()), error -> run(error::printStackTrace))
        ));
        Thread.sleep(10000);
    }


}
