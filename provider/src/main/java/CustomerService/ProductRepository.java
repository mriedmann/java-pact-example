package CustomerService;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final AtomicLong counter = new AtomicLong();
    private final Faker faker;

    private final Map<Long, Product> store = new HashMap<>();

    public ProductRepository(){
        faker = new Faker(new Random(1));
        for(int i = 0; i < 10; i++){
            Product p = create();
            store.put(p.getId(), p);
        }
    }

    private Product create() {
        return new Product(
                counter.incrementAndGet(),
                faker.commerce().productName(),
                BigDecimal.valueOf(faker.number().randomDouble(4, 0,1))
        );
    }

    public List<Product> getAll() {
        return new ArrayList<>(store.values());
    }

    public Product getOneById(Long id) {
        return store.get(id);
    }
}
