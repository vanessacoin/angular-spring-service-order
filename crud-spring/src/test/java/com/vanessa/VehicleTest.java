package com.vanessa;

import com.vanessa.entities.Customer;
import com.vanessa.entities.Vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    
    private Validator validator;

    private Vehicle vehicle;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Inicializa os objetos de teste antes de cada teste
        vehicle = new Vehicle();
        customer = new Customer();
        
        // Configura o Customer com os dados necessários
        customer.setId(1L);
        customer.setName("John Doe");
    }

    @Test
    void testSetCustomer() {
        // Chama o método setCustomer e verifica se o customer foi setado corretamente
        vehicle.setCustomer(customer);
        
        // Verifica se o veículo agora está associado ao cliente correto
        assertNotNull(vehicle.getCustomer(), "O cliente não foi atribuído ao veículo.");
        assertEquals(customer.getId(), vehicle.getCustomer().getId(), "O ID do cliente não corresponde.");
        assertEquals("John Doe", vehicle.getCustomer().getName(), "O nome do cliente não corresponde.");
    }


    public VehicleTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testSaveNewVehicle() {
        vehicle = new Vehicle();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Supra mk4");
        vehicle.setPlate("JAR9E10");
        vehicle.setYear(1994);
        vehicle.setColor("Branco");
        vehicle.setCustomer(customer);

        assertEquals("Toyota", vehicle.getBrand());
        assertEquals("Supra mk4", vehicle.getModel());
        assertEquals("JAR9E10", vehicle.getPlate());
        assertEquals(1994, vehicle.getYear());
        assertEquals("Branco", vehicle.getColor());
        assertEquals(customer, vehicle.getCustomer());
    }
}
