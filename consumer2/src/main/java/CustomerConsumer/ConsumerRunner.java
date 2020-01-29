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
        CustomerBalance[] balances = client.getAllBalances();
        for (CustomerBalance balance : balances){
            System.out.printf("%d,%s,%f\n",balance.getCustomerId(), balance.getCustomerName(), balance.getAggregatedBalance());
        }
    }
}
