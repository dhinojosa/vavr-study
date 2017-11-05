package com.evolutionnext;


import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;


import static io.vavr.API.For;
import static org.assertj.core.api.Assertions.assertThat;

public class ForComprehensionTest {

    @SuppressWarnings("Duplicates")
    @Test
    public void testEitherBefore() {
        Either<Throwable, Integer> positiveAnswer =
                Either.right(10);
        Either<Throwable, Integer> positiveAnswer2 =
                Either.right(10);

        Either<Throwable, Integer> eitherInteger =
                positiveAnswer.flatMap(x ->
                positiveAnswer2.map(y ->
                        x + y
                )
        );

        System.out.println(eitherInteger);
        Integer result = eitherInteger.getOrElse(-1);
        assertThat(result).isEqualTo(20);
    }


    @Test
    public void testForComprehensionEitherAfter() {
        Either<Throwable, Integer> positiveAnswer =
                Either.right(10);
        Either<Throwable, Integer> positiveAnswer2 =
                Either.right(10);

        Either<String, Integer> result =
                For(positiveAnswer, x ->
                For(positiveAnswer2)
                        .yield(y -> x + y))
                        .toRight("Nope");

        assertThat(result).isEqualTo(Either.right(20));
    }

    @Test
    public void testOptionBefore() throws Exception {
        Option<Integer> option1 = Option.of(4);
        Option<Integer> option2 = Option.none();
        Option<Integer> result = option1
                .flatMap(x -> option2
                        .map(y -> (x + y)));
        assertThat(result).isEqualTo(Option.none());
    }

    @Test
    public void testForComprehensionTwoOption() {
        Option<Integer> option1 = Option.of(4);
        Option<Integer> option2 = Option.none();

        Option<Integer> result =
                For(option1, o1 ->
                For(option2)
                                .yield(o2 -> o1 + o2))
                                .toOption();
        assertThat(result).isEqualTo(Option.none());
    }

    @Test
    public void testForComprehensionThreeTry() {
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
        Option<Integer> option1 = Option.of(6);
        Option<Integer> option =
                For(try1, a ->
                        For(option1).yield(b -> a + b)
                ).toOption();

        assertThat(option).isEqualTo(Option.of(18));
    }

    @Test
    public void testForComprehensionOneTryOneBadOption() {
        Try<Integer> try1 = Try.of(() -> 12);
        Option<Integer> badOption = Option.none();

        Option<Integer> option =
                For(try1, a ->
                        For(badOption).yield(b -> a + b)
                ).toOption();

        assertThat(option).isEqualTo(Option.none());
    }
}
