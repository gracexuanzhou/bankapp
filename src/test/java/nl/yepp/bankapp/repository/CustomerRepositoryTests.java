package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTests {

    public static final Long FIRST_CUSTOMER_ID = 1L;
    public static final String FIRST_CUSTOMER_EMAIL = "test@example.com";
    public static final String FIRST_CUSTOMER_NAME = "John Doe";
    public static final String FIRST_CUSTOMER_UPDATED_EMAIL = "test@better.example.com";
    public static final String FIRST_CUSTOMER_UPDATED_NAME = "Johnathan Doe";

    public static final Long SECOND_CUSTOMER_ID = 2L;
    public static final String SECOND_CUSTOMER_EMAIL = "jan@domain.nl";
    public static final String SECOND_CUSTOMER_NAME = "Jan van Bos";
    CustomerRepository customerRepository = new SimpleInMemoryCustomerRepository();

    @Test
    void testCRUD() {
        testRepoDoesNotHaveTheFirstCustomer();

        testCreateTheFirstCustomer();

        testCreateTheSecondCustomer();

        testReadTheFirstCustomerAfterCreating();

        testReadAllCustomers();

        testUpdateTheFirstCustomer();

        testReadTheFirstCustomerAfterUpdating();

        testRemoveTheFirstCustomer();

        testReadAllCustomersAfterRemovingTheFirst();
    }

    private void testRepoDoesNotHaveTheFirstCustomer() {
        assertNull(customerRepository.findById(FIRST_CUSTOMER_ID), "The repo has to not have the first customer");
    }

    private void testCreateTheFirstCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setEmail(FIRST_CUSTOMER_EMAIL);
        newCustomer.setName(FIRST_CUSTOMER_NAME);
        Customer savedCustomer = customerRepository.save(newCustomer);

        assertEquals(FIRST_CUSTOMER_ID, savedCustomer.getId());
        assertEquals(newCustomer.getEmail(), savedCustomer.getEmail());
        assertEquals(newCustomer.getName(), savedCustomer.getName());
    }

    private void testCreateTheSecondCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setEmail(SECOND_CUSTOMER_EMAIL);
        newCustomer.setName(SECOND_CUSTOMER_NAME);
        Customer savedCustomer = customerRepository.save(newCustomer);

        assertEquals(SECOND_CUSTOMER_ID, savedCustomer.getId());
        assertEquals(newCustomer.getEmail(), savedCustomer.getEmail());
        assertEquals(newCustomer.getName(), savedCustomer.getName());
    }

    private void testReadTheFirstCustomerAfterCreating() {
        Customer customer = customerRepository.findById(FIRST_CUSTOMER_ID);

        assertNotNull(customer, "Customer has to exist");
        assertEquals(FIRST_CUSTOMER_NAME, customer.getName());
        assertEquals(FIRST_CUSTOMER_EMAIL, customer.getEmail());
    }

    private void testReadAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        assertNotNull(customers);
        assertEquals(2, customers.size(), "There has to be 2 customers at this point");
        assertEquals(FIRST_CUSTOMER_ID, customers.get(0).getId(), "The order of customers insertion has to be respected");
        assertEquals(SECOND_CUSTOMER_ID, customers.get(1).getId(), "We expect the id of the second customer");
    }

    private void testUpdateTheFirstCustomer() {
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

    private void testReadTheFirstCustomerAfterUpdating() {
        Customer customer = customerRepository.findById(FIRST_CUSTOMER_ID);

        assertNotNull(customer, "Customer has to exist");
        assertEquals(FIRST_CUSTOMER_UPDATED_NAME, customer.getName());
        assertEquals(FIRST_CUSTOMER_UPDATED_EMAIL, customer.getEmail());
    }

    private void testRemoveTheFirstCustomer() {
        customerRepository.deleteById(FIRST_CUSTOMER_ID);
        testRepoDoesNotHaveTheFirstCustomer();
    }

    private void testReadAllCustomersAfterRemovingTheFirst() {
        List<Customer> customers = customerRepository.findAll();

        assertNotNull(customers);
        assertEquals(1, customers.size(), "There has to be 1 customer after removing the first");
    }
}
