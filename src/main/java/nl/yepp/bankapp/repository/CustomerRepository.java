package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer findById(Long firstCustomerId);

    void deleteById(Long firstCustomerId);

    List<Customer> findAll();
}
