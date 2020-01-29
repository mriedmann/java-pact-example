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
        if(args.length < 1) return;
        if(args[0].equals("products")){
            Product[] products = client.getAllProducts();
            for (Product product : products) {
                System.out.printf("%d,%s,%f\n", product.getId(), product.getName(), product.getInterestRate());
            }
        }
        if(args.length < 2) return;
        if(args[0].equals("customers")){
            Customer[] customers = client.findCustomers(args[1], CustomerStatus.valueOf(args[2]));
            for (Customer customer : customers) {
                System.out.printf("%d,%s,%s,%s\n", customer.getId(), customer.getName(), customer.getEmail(), customer.getStatus().toString());
            }
        }
    }
}
