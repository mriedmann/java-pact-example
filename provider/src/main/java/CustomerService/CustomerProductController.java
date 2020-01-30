package CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerProductController {
    private final CustomerProductRepository customerProductRepository;

    @Autowired
    public CustomerProductController(CustomerProductRepository customerProductRepository) {
        this.customerProductRepository = customerProductRepository;
    }

    @GetMapping("/customers/{id}/products")
    public List<CustomerProduct> getCustomerProducts(@PathVariable int id, @RequestParam(value="offset", required=false) Integer offset) {
        if(offset == null) offset = 0;

        return customerProductRepository
                .getAllByCustomerId(id).stream()
                .skip(offset).limit(50)
                .collect(Collectors.toList());
    }
}
