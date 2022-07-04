package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long customerId);

    void deleteById(Long customerId);

    List<Customer> findAll();
}
