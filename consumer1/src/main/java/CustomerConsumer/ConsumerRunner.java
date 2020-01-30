package CustomerConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRunner implements CommandLineRunner {
    private CustomerClient client;

    @Autowired
    public ConsumerRunner(CustomerClient client){
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        Integer offset = 0;
        if(args.length > 0) offset = Integer.getInteger(args[0]);
        Customer[] customers = client.getAllCustomers(offset);
        for (Customer c : customers){
            System.out.printf("%d,%s,%s,%s\n", c.getId(), c.getName(), c.getEmail(), c.getStatus().name());
        }
    }
}
