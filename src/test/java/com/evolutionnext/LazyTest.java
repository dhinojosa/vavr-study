package com.evolutionnext;

import javaslang.Lazy;
import javaslang.collection.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LazyTest {
    @Test
    public void testLazy() throws InterruptedException {
        Lazy<LocalDateTime> lazyLocalDateTime =
                Lazy.of(() -> LocalDateTime.now());

        System.out.println("At the time of this line evaluation: "
                + LocalDateTime.now());

        Thread.sleep(1000);

        System.out.println("What we got from our lazy value: "
                + lazyLocalDateTime.get());

        Thread.sleep(1000);

        System.out.println("What we got from our lazy value: "
                + lazyLocalDateTime.get()); //memoized

        Thread.sleep(1000);
    }

    @Test
    public void testMonadicLazyStructures() {
        Lazy<Integer> lazyInt = Lazy.of(LocalDateTime::now)
                                     .map(LocalDateTime::getMinute);
        assertThat(lazyInt.isEvaluated()).isFalse();
        lazyInt.out(System.out);
        assertThat(lazyInt.isEvaluated()).isTrue();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLazyDanger() throws InterruptedException {
        int divisor = 0;
        Lazy<Integer> lazyInt = Lazy.of(() -> 54 / divisor);
        //thrown.expect(ArithmeticException.class);
        Lazy<Integer> integerLazy = lazyInt.map(x -> x + 15);

        System.out.println("So far so good");
        Thread.sleep(1000);

        integerLazy.forEach(System.out::println);
    }

    @Test
    public void testLazyToList() {
        Lazy<Integer> lazyInt = Lazy.of(() -> 4);
        List<Integer> integers = lazyInt.toList();
        assertThat(integers).hasSize(1).containsOnly(4);
    }
}
