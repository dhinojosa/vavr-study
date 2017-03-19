package com.evolutionnext;

import javaslang.collection.List;
import javaslang.control.Validation;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Test
    public void validateEmployee() throws Exception {
        EmployeeValidator validator = new EmployeeValidator();
        Validation<List<String>, Employee> employeeValidation = validator
                .validatePerson("Nate", "Schutta", 44);
        assertThat(employeeValidation.isValid()).isEqualTo(true);
    }

    @Test
    public void validateEmployeeBad() throws Exception {
        EmployeeValidator validator = new EmployeeValidator();
        Validation<List<String>, Employee> employeeValidation =
                validator.validatePerson("N8", "Schutta", -2);
        assertThat(employeeValidation.isValid()).isEqualTo(false);
        Validation<String, Employee> employees = employeeValidation
                .leftMap(strings -> strings.reduceLeft((total, next) -> total + ", " + next));
        System.out.println(employees.getError());
    }
}
