package com.vanessa;

import com.vanessa.entities.Customer;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class CustomerTest {

    private Validator validator;

    public CustomerTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNameValidation() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("Ana"); // Menos de 5 caracteres

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("o comprimento deve ser entre 5 e 100", 
                     violations.iterator().next().getMessage());
    }

    @Test
    void testValidName() {
        Customer customer = new Customer();
        customer.setName("Pedro"); // Nome válido

        assertEquals("Pedro", customer.getName());
    }
}
