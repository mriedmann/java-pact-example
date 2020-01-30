package CustomerService;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CustomerProductRepository {
    private final AtomicLong counter = new AtomicLong();
    private final Random rnd = new Random(1);
    private final Map<Long, CustomerProduct> store = new HashMap<>();

    @Autowired
    public CustomerProductRepository(CustomerRepository cRepo, ProductRepository pRepo){
        Faker faker = new Faker(rnd);
        for (Customer c : cRepo.getAll()) {
            List<Product> products = pRepo.getAll().stream().filter(p -> rnd.nextBoolean()).collect(Collectors.toList());
            for (Product p : products) {
                CustomerProduct cp = new CustomerProduct(
                        counter.getAndIncrement(), 
                        c.getId(), 
                        p.getId(),
                        BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000000)));
                store.put(cp.getId(), cp);
            }
        }
    }

    public List<CustomerProduct> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<CustomerProduct> getAllByCustomerId(long customerId) {
        return store.values().stream().filter(c -> c.getCustomerId() == customerId).collect(Collectors.toList());
    }
}
