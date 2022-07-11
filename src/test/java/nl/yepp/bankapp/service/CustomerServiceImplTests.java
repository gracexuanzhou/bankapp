package nl.yepp.bankapp.service;

import nl.yepp.bankapp.InvalidPropertyState;
import nl.yepp.bankapp.model.Customer;
import nl.yepp.bankapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplTests {

    CustomerServiceImpl testee = new CustomerServiceImpl();

    @Test
    public void createNewCustomer() {
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setEmail("foo@example.com");
        savedCustomer.setName("Foo");
        testee.setCustomerRepository(new DummyCustomerRepository() {
            @Override
            public Customer save(Customer customer) {
                return savedCustomer;
            }
        });

        Customer customer = new Customer();
        Customer newCustomer = testee.createNew(customer);
        assertEquals(savedCustomer, newCustomer);
    }

    @Test
    public void createNewCustomerWithPresetId() {
        Customer customer = new Customer();
        customer.setId(1L);
        assertThrows(
                InvalidPropertyState.class,
                () -> testee.createNew(customer));
    }

    @Test
    public void updateCustomer() {
        testee.setCustomerRepository(new DummyCustomerRepository());

        Customer customer = new Customer();
        customer.setId(1L);
        testee.update(customer);
    }

    @Test
    public void updateCustomerWithoutId() {
        Customer customer = new Customer();
        customer.setId(null);
        assertThrows(
                InvalidPropertyState.class,
                () -> testee.update(customer));
    }
}

class DummyCustomerRepository implements CustomerRepository {

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long customerId) {

    }

    @Override
    public List<Customer> findAll() {
        return null;
    }
}
