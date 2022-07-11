package nl.yepp.bankapp.service;

import nl.yepp.bankapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createNew(Customer customer);

    void update(Customer customer);

    Optional<Customer> findById(Long customerId);

    void deleteById(Long customerId);

    List<Customer> findAll();
}
