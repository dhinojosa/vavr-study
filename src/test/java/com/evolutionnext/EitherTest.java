package com.evolutionnext;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EitherTest {

    @Test
    public void testEitherPositive() {
        Either<Throwable, Integer> positiveAnswer =
                Either.right(10);
        assertThat(positiveAnswer.isRight());
        assertThat(positiveAnswer.getOrElse(-10))
                .isEqualTo(10);
    }

    @Test
    public void testEitherNegative() {
        Either<String, Integer> negativeAnswer =
                Either.left("I'm hungry");
        assertThat(negativeAnswer.isLeft());
        assertThat(negativeAnswer.getOrElse(-4)).isEqualTo(-4);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testMonadicPositiveEither() {
        Either<Throwable, Integer> positiveAnswer =
                Either.right(10);
        Either<Throwable, Integer> positiveAnswer2 =
                Either.right(20);

        Either<Throwable, Integer> eitherInteger =
                positiveAnswer.flatMap(x ->
                        positiveAnswer2.map(y ->
                                x + y
                        )
                );

        System.out.println(eitherInteger);
        Integer result = eitherInteger.getOrElse(-1);
        assertThat(result).isEqualTo(30);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testMonadicNegativeEither() {
        Either<Throwable, Integer> positiveAnswer =
                Either.right(10);
        Either<Throwable, Integer> negativeAnswer =
                Either.left(new Throwable("Nope"));

        Either<Throwable, Integer> eitherInteger =
                positiveAnswer.flatMap(
                        x -> negativeAnswer.map(y ->
                                x + y
                        )
                );
        System.out.println("eitherInteger = " + eitherInteger);
        Integer result = eitherInteger.getOrElse(-1);
        assertThat(result).isEqualTo(-1);
    }
}
