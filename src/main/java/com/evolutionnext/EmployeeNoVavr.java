package com.evolutionnext;

import javaslang.control.Option;

import java.util.Objects;
import java.util.Optional;

public class EmployeeNoVavr {
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;

    public EmployeeNoVavr(String firstName,
                          String lastName, int age) {
        System.out.format("firstName: %s, lastName: %s, age: %d", firstName, lastName, age);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = null;
        this.age = age;
    }

    public EmployeeNoVavr(String firstName, String middleName, String lastName, int age) {
        System.out.format("firstName: %s, lastName: %s, age: %d", firstName, lastName, age);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeNoVavr employee = (EmployeeNoVavr) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(age, employee.age) &&
                Objects.equals(age, employee.age);
    }

    public String getFirstName() {
        return firstName;
    }

    public Optional<String> getMiddleName() {
        return Optional.ofNullable(middleName);
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeWithVavr{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getFullName() {
        return firstName + " " +
                getMiddleName().map(x -> x + " ").orElse("")  + lastName;
    }
}
