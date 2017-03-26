package com.evolutionnext;

import javaslang.*;
import javaslang.collection.List;
import org.junit.Test;

import java.time.LocalDate;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionTest {

    @Test
    public void function0Test() throws Exception {
        //Aka Supplier
        Function0<LocalDate> function0 = LocalDate::now;
        List<LocalDate> listOfLocalDates = List.fill(10, function0);
        listOfLocalDates.forEach(System.out::println);
        assertThat(listOfLocalDates).hasSize(10);
    }

    @Test
    public void function1Test() throws Exception {
        List list = List.range(1, 10).map(integer -> integer * 10);
        assertThat(list.head()).isEqualTo(10);
    }



    @Test
    public void curryingTest() throws Exception {
        Function3<Integer, Integer, Integer, Integer> function3 =
                (integer, integer2, integer3) -> integer + integer2
                + integer3;
        Function1<Integer, Function1<Integer, Function1<Integer, Integer>>> curried =
                function3.curried();
        Function1<Integer, Integer> add7 = curried.apply(3).apply(4);
        assertThat(add7.apply(3)).isEqualTo(10);
    }

    @Test
    public void testTupledOfAFunction() throws Exception {
        Function2<Integer, Integer, String> f2 = (x, y) -> x + "foo " + y + "bar";
        Function1<Tuple2<Integer, Integer>, String> tupled = f2.tupled();
        String string = tupled.apply(Tuple.of(4, 1));
        assertThat(string).isEqualTo("4foo 1bar");
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testMemoizationOfAFunction() throws Exception {
        //In computing, memoization or memoisation is an optimization technique used primarily to speed up computer
        // programs by storing the results of expensive function calls and returning the cached result when the same
        // inputs occur again.

        Function1<Integer, Integer> f1 =  x -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            };
            return x + 1;};

        Function1<Integer, Integer> f2 = f1.memoized();

        long start = System.currentTimeMillis();
        f2.apply(4);
        long end = System.currentTimeMillis();
        long firstTime = end - start;

        System.out.format("First Time: %d", firstTime);

        start = System.currentTimeMillis();
        f2.apply(4);
        end = System.currentTimeMillis();
        long secondTime = end - start;

        System.out.format("Second Time: %d", secondTime);

        assertThat(secondTime).isLessThan(firstTime);
    }

    @Test
    public void functionComposition() throws Exception {
        //f(g(i))
        //g(i) = s
        //f(s) = d
        Function1<String, Double> f = (String s) -> s.length() * 1.0;
        Function1<Integer, String> g = (Integer i) -> i + "hello";
        Function1<Integer, Double> composedFunction = f.compose(g);
        assertThat(composedFunction.apply(30)).isEqualTo(7.0);
    }
}
