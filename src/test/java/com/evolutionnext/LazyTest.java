package com.evolutionnext;

import javafx.util.converter.LocalDateStringConverter;
import javaslang.Lazy;
import javaslang.collection.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class LazyTest {
    @Test
    public void testLazy() throws InterruptedException {
        Lazy<LocalDateTime> lazyInt = Lazy.of(LocalDateTime::now);
        System.out.println("At the time of this line evaluation: " + LocalDateTime.now());
        Thread.sleep(1000);
        System.out.println("What we got from our lazy value: " + lazyInt.get());
        System.out.println("What we got from our lazy value: " + lazyInt.get()); //memoized
    }

    @Test
    public void testMonadicLazyStructures() {
        Lazy<Integer> lazyInt = Lazy.of(LocalDateTime::now).map(LocalDateTime::getMinute);
        assertThat(lazyInt.isEvaluated()).isFalse();
        lazyInt.out(System.out);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLazyDanger() {
        int divisor = 0;
        Lazy<Integer> lazyInt = Lazy.of(() -> 54/divisor);
        assertTrue(true);

        thrown.expect(ArithmeticException.class);
        lazyInt.map(x -> x + 15).forEach(System.out::println);
    }

    @Test
    public void testLazyToList() {
        Lazy<Integer> lazyInt = Lazy.of(() -> 4);
        List<Integer> integers = lazyInt.toList();
        assertThat(integers).hasSize(1);
    }
}
