package CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers(@RequestParam(value="status", required=false) CustomerStatus status) {
        return customerRepository.getAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerByName(@PathVariable("id") Long id) {
        return customerRepository.getOneById(id);
    }

}