package com.evolutionnext;

import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import static io.vavr.Patterns.*;

public class PatternMatchingTest {
    /*
     *  A switch works with the byte, short, char, and
     *  int primitive data types.
     *  It also works with enumerated types
     *  (discussed in Enum Types), the String class,
     *  and a few special classes that wrap certain primitive types:
     *  Character, Byte, Short, and Integer
     */
    @Test
    public void theProblemWithSwitch() throws Exception {
        String str = "January";
        int month;
        switch (str) {
            case "January":
                month = 1;
                break;
            case "February":
                month = 2;
                break;
            case "March":
                month = 3;
                break;
            default:
                month = 4;
                break;
        }
        assertThat(month).isEqualTo(1);
    }

    @Test
    public void testSimplePatternMatch() throws Exception {
        String month = "January";
        Integer result = Match(month).of(
                Case($(is("January")), 1),
                Case($(is("February")), 2),
                Case($(is("March")), 3),
                Case($(is("April")), 4),
                Case($(), 5));
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testPatternMatchOption() throws Exception {
        Option<String> middleName = Option.of("Lisa");
        String message = Match(middleName).of(
                Case($Some($()), x -> "Middle name is " + x),
                Case($None(), "No middle name here"));
        assertThat(message).isEqualTo("Middle name is Lisa");
    }

    @Test
    public void testPatternMatchTry() throws Exception {
        Try<Tuple2<Integer, String>> tryT2 = Try.of(() -> {
            throw new RuntimeException("Oops");
        });
        String message = Match(tryT2).of(
                Case($Success($()), x -> "Got an answer and it was: " + x),
                Case($Failure($()), x -> "Oh no we got: " + x.getMessage())
        );
        assertThat(message).isEqualTo("Oh no we got: Oops");
    }

    @Test
    public void testFuturePatternMatching() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Integer> future = Future.of(executorService, () -> {
            Thread.sleep(5000);
            return 50 + 10;
        }).map(x -> x * 100);

        future.onComplete(response ->
                Match(response).of(
                        Case($Success($(instanceOf(Integer.class))),
                                integer -> run(() ->
                                        System.out.println(integer))),
                        Case($Failure($()), error -> run(error::printStackTrace))
                ));
        Thread.sleep(6000);
    }

}
