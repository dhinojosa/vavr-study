package com.evolutionnext;

import javaslang.control.Either;
import org.junit.Test;
import javaslang.API.*;

import static org.assertj.core.api.Assertions.assertThat;

public class EitherTest {

    @Test
    public void testEitherPositive() {
        Either<Throwable, Integer> positiveAnswer = Either.right(10);
    }

    @Test
    public void testEitherNegative() {
        Either<Throwable, Integer> negativeAnswer = Either.left(new IllegalArgumentException("Not liking that."));
    }

    @Test
    public void testMonadicPositiveEither() {
        Either<Throwable, Integer> positiveAnswer = Either.right(10);
        Either<Throwable, Integer> positiveAnswer2 = Either.right(10);

        Either<Throwable, Integer> eitherInteger = positiveAnswer.flatMap(x ->
                positiveAnswer2.map(y ->
                        x + y
                )
        );

        Integer result = eitherInteger.getOrElse(-1);
        assertThat(result).isEqualTo(Either.right(20));
    }

}
