package nl.yepp.bankapp.contorller;

import nl.yepp.bankapp.model.Customer;
import nl.yepp.bankapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id does not have to be set for new customer");
        }
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id has to be set");
        }
        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteById(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
