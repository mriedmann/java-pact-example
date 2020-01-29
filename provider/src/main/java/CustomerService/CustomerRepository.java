package CustomerService;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {

    private final AtomicLong counter = new AtomicLong();
    private final Faker faker;

    private final Map<Long, Customer> store = new HashMap<>();
    private final Map<String, Customer> nameIndex = new HashMap<>();

    public CustomerRepository(){
        faker = new Faker(new Random(1));
        for(int i = 0; i < 25; i++){
            Customer c = create();
            store.put(c.getId(), c);
            nameIndex.put(c.getName(), c);
        }
    }

    private Customer create() {
        return new Customer(
                counter.incrementAndGet(),
                faker.name().fullName(),
                faker.address().fullAddress(),
                faker.date().birthday(),
                faker.internet().emailAddress(),
                faker.options().option(CustomerStatus.class)
        );
    }

    public List<Customer> getAllByStatus(CustomerStatus status) {
        return store
            .values()
            .stream()
            .filter(customer -> customer.getStatus() == status)
            .collect(Collectors.toList());
    }

    public Customer getOneByName(String name) {
        return nameIndex.get(name);
    }

    public Customer getOneById(Long id) {
        return store.get(id);
    }

    public List<Customer> getAll() {
        return new ArrayList<>(store.values());
    }
}
