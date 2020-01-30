package CustomerService;

import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Customer> getCustomers(@RequestParam(value="offset", required=false) Integer offset, @RequestParam(value="name", required=false) String name, @RequestParam(value="status", required=false) CustomerStatus status) {
        if(offset == null) offset = 0;

        Stream<Customer> customerStream = customerRepository.getAll().stream();

        if(name != null && !name.equals(""))
            customerStream = customerStream.filter(c ->  c.getName().contains(name));

        if(status != CustomerStatus.ANY)
            customerStream = customerStream.filter(c -> c.getStatus() == status);

        return customerStream.skip(offset).limit(50).collect(Collectors.toList());
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerByName(@PathVariable("id") Long id) {
        return customerRepository.getOneById(id);
    }

}