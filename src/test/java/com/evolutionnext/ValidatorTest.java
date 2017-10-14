package com.evolutionnext;


import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Test
    public void validateEmployee() throws Exception {
        EmployeeValidator validator = new EmployeeValidator();
        Validation<Seq<String>, EmployeeWithVavr> employeeValidation = validator
                .validatePerson("Nate", "Schutta", 44);
        assertThat(employeeValidation.isValid()).isEqualTo(true);
    }

    @Test
    public void validateEmployeeBad() throws Exception {
        EmployeeValidator validator = new EmployeeValidator();
        Validation<Seq<String>, EmployeeWithVavr> employeeValidation =
                validator.validatePerson("N8", "Schutta", -2);
        assertThat(employeeValidation.isValid()).isEqualTo(false);

        Validation<String, EmployeeWithVavr> employees = employeeValidation
                .mapError(strings -> strings.reduceLeft((total, next) -> total + ", " + next));
        System.out.println(employees.getError());
    }
}
