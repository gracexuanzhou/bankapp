package nl.yepp.bankapp.service;

import nl.yepp.bankapp.InvalidPropertyState;
import nl.yepp.bankapp.model.Customer;
import nl.yepp.bankapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createNew(Customer customer) {
        if (customer.getId() != null) {
            throw new InvalidPropertyState("id does not have to be set for new customer");
        }
        return customerRepository.save(customer);
    }

    @Override
    public void update(Customer customer) {
        if (customer.getId() == null) {
            throw new InvalidPropertyState("id has to be set");
        }
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void deleteById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
