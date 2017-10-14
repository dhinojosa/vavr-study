package com.evolutionnext;


import io.vavr.collection.Seq;
import io.vavr.control.Validation;

public class EmployeeValidator {

    public Validation<Seq<String>, EmployeeWithVavr> validatePerson(
            String firstName,String lastName, int age) {
        return Validation.combine(
                validateName(firstName),
                validateName(lastName),
                validateAge(age)).ap(EmployeeWithVavr::new);
    }

    private Validation<String, String> validateName(String name) {
        String invalidChars = name.replaceAll("[a-zA-Z ]", "");
        return invalidChars.isEmpty() ?
                Validation.valid(name)
                : Validation.invalid("Name Error:" + invalidChars);
    }

    private Validation<String, Integer> validateAge(int age) {
        return age < 0 ? Validation.invalid("Age Error")
                : Validation.valid(age);
    }
}
