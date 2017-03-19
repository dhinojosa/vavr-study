package com.evolutionnext;

import javaslang.Function1;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;

import static javaslang.API.For;
import static org.assertj.core.api.Assertions.assertThat;

public class ForComprehensionTest {
    @Test
    public void testForComprehensionTwoEither() {
        Either<Throwable, Integer> positiveAnswer = Either.right(10);
        Either<Throwable, Integer> positiveAnswer2 = Either.right(10);

        Either<String, Integer> result = For(positiveAnswer, i ->
                For(positiveAnswer2)
                        .yield(j -> i + j)).toRight("Nope");

        assertThat(result).isEqualTo(Either.right(20));
    }

    @Test
    public void testForComprehensionTwoOption() {
        Option<Integer> option1 = Option.of(4);
        Option<Integer> option2 = Option.of(10);

        Option<Integer> result = For(option1, o1 ->
                For(option2).yield(o2 -> o1 + o2)
        ).toOption();

        assertThat(result).isEqualTo(Option.of(14));
    }

    @Test
    public void testForComprehensionTwoTry() {
        Try<Integer> try1 = Try.of(() -> 12);
        Try<Integer> try2 = Try.of(() -> 6);
        Try<Integer> try3 = Try.of(() -> 6);

        Try<Integer> result =
                For(try1, a ->
                        For(try2, b ->
                                For(try3).yield(c -> (a / b) + c))).toTry();

        assertThat(result).isEqualTo(Try.of(() -> 8));
    }

    @Test
    public void testForComprehensionOneTryOneOption() {
        Try<Integer> try1 = Try.of(() -> 12);
        Option<Integer> try2 = Option.of(6);

        Option<Integer> option =
                For(try1, a ->
                        For(try2).yield(b -> a + b)
                ).toOption();

        assertThat(option).isEqualTo(Option.of(18));
    }

    @Test
    public void testForComprehensionOneTryOneBadOption() {
        Try<Integer> try1 = Try.of(() -> 12);
        Option<Integer> try2 = Option.none();

        Option<Integer> option =
                For(try1, a ->
                        For(try2).yield(b -> a + b)
                ).toOption();

        assertThat(option).isEqualTo(Option.none());
    }
}
