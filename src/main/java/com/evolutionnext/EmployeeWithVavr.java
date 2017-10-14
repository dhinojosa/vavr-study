package com.evolutionnext;

import javaslang.control.Option;

import java.util.Objects;

public class EmployeeWithVavr {
    private String firstName;
    private Option<String> middleName; //YAY!
    private String lastName;
    private int age;

    public EmployeeWithVavr(String firstName,
                            String lastName, int age) {
        System.out.format("firstName: %s, lastName: %s, age: %d", firstName, lastName, age);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = Option.none();
        this.age = age;
    }

    public EmployeeWithVavr(String firstName,
                            String middleName, String lastName,
                            int age) {
        System.out.format("firstName: %s, lastName: %s, age: %d", firstName, lastName, age);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = Option.of(middleName);
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public Option<String> getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeWithVavr employeeWithVavr = (EmployeeWithVavr) o;
        return Objects.equals(firstName, employeeWithVavr.firstName) &&
                Objects.equals(age, employeeWithVavr.age) &&
                Objects.equals(age, employeeWithVavr.age);
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
                middleName.map(x -> x + " ").getOrElse("")  + lastName;
    }
}
