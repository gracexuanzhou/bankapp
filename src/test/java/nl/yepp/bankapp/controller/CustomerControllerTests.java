package nl.yepp.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.yepp.bankapp.model.BankAccount;
import nl.yepp.bankapp.model.Customer;
import nl.yepp.bankapp.service.CustomerService;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void list() throws Exception {
        given(this.customerService.findAll()).willReturn(Lists.newArrayList(bob(), alice()));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Bob")))
                .andExpect(jsonPath("$[0].email", Matchers.equalTo("bob@example.com")))
                .andExpect(jsonPath("$[0].bankAccounts[0].id", Matchers.equalTo(3)))
                .andExpect(jsonPath("$[0].bankAccounts[0].name", Matchers.equalTo("Bob's personal account")))
                .andExpect(jsonPath("$[0].bankAccounts[1].id", Matchers.equalTo(4)))
                .andExpect(jsonPath("$[0].bankAccounts[1].name", Matchers.equalTo("Family bank account")))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo("Alice")))
                .andExpect(jsonPath("$[1].email", Matchers.equalTo("alice@example.com")))
        ;
    }

    @Test
    public void create() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setEmail("new@example.com");
        newCustomer.setName("New Customer");

        Customer newCustomerWithId = new Customer();
        newCustomerWithId.setId(10L);
        newCustomerWithId.setEmail(newCustomer.getEmail());
        newCustomerWithId.setName(newCustomer.getName());
        given(this.customerService.createNew(newCustomer)).willReturn(newCustomerWithId);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(asJsonString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(10)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("New Customer")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("new@example.com")));
    }

    private Customer bob() {
        Customer bob = new Customer();
        bob.setId(1L);
        bob.setName("Bob");
        bob.setEmail("bob@example.com");
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(3L);
        bankAccount1.setName("Bob's personal account");
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(4L);
        bankAccount2.setName("Family bank account");
        bob.setBankAccounts(Lists.newArrayList(bankAccount1, bankAccount2));
        return bob;
    };

    private Customer alice() {
        Customer alice = new Customer();
        alice.setId(2L);
        alice.setName("Alice");
        alice.setEmail("alice@example.com");
        return alice;
    };

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
