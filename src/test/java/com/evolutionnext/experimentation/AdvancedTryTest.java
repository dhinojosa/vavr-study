package com.evolutionnext.experimentation;

import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.function.Function;

import static javaslang.API.For;

public class AdvancedTryTest {

    @Test
    public void testSimpleTryLongWay() throws Exception {
        Try<Connection> connectionTry = Try.of(() -> {
            Class.forName("com.mysql.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/test?" +
                    "user=minty&password=greatsqldb");
        });
        Try<PreparedStatement> preparedStatements = connectionTry.flatMap(connection -> Try.of(() -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account values (?, ?)");
            preparedStatement.setString(1, "Foo");
            preparedStatement.setString(2, "Bar");
            return preparedStatement;
        }));
        Try<Boolean> updated = preparedStatements.flatMap(preparedStatement -> Try.of(preparedStatement::execute));

        updated.onSuccess(b -> System.out.println("Updated"));
        updated.onFailure(t -> System.out.println("Failed with" + t.getCause()));
    }

    public Try<Connection> getConnection(String user, String db, String pass) {
        return Try.of(() -> {
            Class.forName("com.mysql.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/test?" +
                    "user=minty&password=greatsqldb");
        });
    }

    public Try<PreparedStatement> getPreparedStatement(Connection connection) {
        return Try.of(() -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account values (?, ?)");

            return preparedStatement;
        });
    }

    public Try<Boolean> execute(PreparedStatement ps, String arg1, String arg2) {
        return Try.of(() -> {
            ps.setString(1, arg1);
            ps.setString(2, arg2);
            return ps.execute();
        });
    }

    public Try<Boolean> testSimpleTryForComprehension() {
        return For(getConnection("bob", "foo", "pass"), connection ->
                For(getPreparedStatement(connection), ps ->
                        For(execute(ps, "Foo", "Bar")).yield(Function.identity()))).toTry();
    }


    public Option<Boolean> testSimpleTryForComprehensionSimplified() {
        return For(getConnection("bob", "foo", "pass"), connection ->
                For(getPreparedStatement(connection), ps ->
                        For(execute(ps, "Foo", "Bar")).yield(Function.identity()))).toOption();
    }

    public Try<Boolean> testSimpleTryForComprehensionSimplifiedWithTry() {
        return For(getConnection("bob", "foo", "pass"), connection ->
                For(getPreparedStatement(connection), ps ->
                        For(execute(ps, "Foo", "Bar")).yield(Function.identity()))).toTry();
    }
}
