package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTests {

    public static final Long FIRST_CUSTOMER_ID = 1L;
    public static final String FIRST_CUSTOMER_EMAIL = "test@example.com";
    public static final String FIRST_CUSTOMER_NAME = "John Doe";
    public static final String FIRST_CUSTOMER_UPDATED_EMAIL = "test@better.example.com";
    public static final String FIRST_CUSTOMER_UPDATED_NAME = "Johnathan Doe";
    CustomerRepository customerRepository = new SimpleInMemoryCustomerRepository();

    @Test
    void testCRUD() {
        testRepoDoesNotHaveTheFirstCustomer();

        testCreateCustomer();

        testReadCustomerAfterCreating();

        testUpdateCustomer();

        testReadCustomerAfterUpdating();

        testRemoveCustomer();
    }

    private void testRepoDoesNotHaveTheFirstCustomer() {
        assertNull(customerRepository.findById(FIRST_CUSTOMER_ID), "The repo has to not have the first customer");
    }

    private void testCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setEmail(FIRST_CUSTOMER_EMAIL);
        newCustomer.setName(FIRST_CUSTOMER_NAME);
        Customer savedCustomer = customerRepository.save(newCustomer);

        assertEquals(FIRST_CUSTOMER_ID, savedCustomer.getId());
        assertEquals(newCustomer.getEmail(), savedCustomer.getEmail());
        assertEquals(newCustomer.getName(), savedCustomer.getName());
    }

    private void testReadCustomerAfterCreating() {
        Customer customer = customerRepository.findById(FIRST_CUSTOMER_ID);

        assertNotNull(customer, "Customer has to exist");
        assertEquals(FIRST_CUSTOMER_NAME, customer.getName());
        assertEquals(FIRST_CUSTOMER_EMAIL, customer.getEmail());
    }

    private void testUpdateCustomer() {
        Customer customerToUpdate = new Customer();
        customerToUpdate.setId(FIRST_CUSTOMER_ID);
        customerToUpdate.setEmail(FIRST_CUSTOMER_UPDATED_EMAIL);
        customerToUpdate.setName(FIRST_CUSTOMER_UPDATED_NAME);

        Customer updatedCustomer = customerRepository.save(customerToUpdate);

        assertNotNull(updatedCustomer, "Updated customer does not have to be null");
        assertEquals(customerToUpdate.getId(), updatedCustomer.getId(), "Updated entity has to have the same id");
        assertEquals(customerToUpdate.getEmail(), updatedCustomer.getEmail());
        assertEquals(customerToUpdate.getName(), updatedCustomer.getName());
    }

    private void testReadCustomerAfterUpdating() {
        Customer customer = customerRepository.findById(FIRST_CUSTOMER_ID);

        assertNotNull(customer, "Customer has to exist");
        assertEquals(FIRST_CUSTOMER_UPDATED_NAME, customer.getName());
        assertEquals(FIRST_CUSTOMER_UPDATED_EMAIL, customer.getEmail());
    }

    private void testRemoveCustomer() {
        customerRepository.deleteById(FIRST_CUSTOMER_ID);
        testRepoDoesNotHaveTheFirstCustomer();
    }
}
