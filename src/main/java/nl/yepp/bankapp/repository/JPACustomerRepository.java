package nl.yepp.bankapp.repository;

import nl.yepp.bankapp.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class JPACustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Customer save(Customer customer) {
        Long customerId = customer.getId();
        if (customerId == null) {
            entityManager.persist(customer);
        } else {
            entityManager.merge(customer);
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return Optional.ofNullable(entityManager.find(Customer.class, customerId));
    }

    @Override
    public void deleteById(Long customerId) {
        Optional<Customer> customer = findById(customerId);
        if (!customer.isEmpty()) {
            entityManager.remove(customer.get());
        }
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("select c from Customer c").getResultList();
    }
}
