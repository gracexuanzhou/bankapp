package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimpleInMemoryCustomerRepository implements CustomerRepository {

    private LinkedHashMap<Long, Customer> customersById = new LinkedHashMap<>();

    @Override
    public Customer save(Customer customer) {
        Customer customerCopy = new Customer();
        customerCopy.setId(customer.getId() == null ? getNextId() : customer.getId());
        customerCopy.setEmail(customer.getEmail());
        customerCopy.setName(customer.getName());
        customersById.put(customerCopy.getId(), customerCopy);
        return customerCopy;
    }

    private Long getNextId() {
        Iterator<Map.Entry<Long, Customer>> iterator = customersById.entrySet().iterator();
        if (!iterator.hasNext()) {
            return 1L;
        }
        Map.Entry<Long, Customer> lastEntry = null;
        while (iterator.hasNext()) {
            lastEntry = iterator.next();
        };
        return lastEntry.getKey() + 1;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return Optional.ofNullable(customersById.get(customerId));
    }

    @Override
    public void deleteById(Long customerId) {
        customersById.remove(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList(customersById.values());
    }
}
