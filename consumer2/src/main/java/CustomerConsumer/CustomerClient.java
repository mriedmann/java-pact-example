package CustomerConsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CustomerClient {

    private final RestTemplate restTemplate;

    public CustomerClient(@Value("${app.baseurl}") String baseUrl) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        this.restTemplate = new RestTemplate();
        this.restTemplate.setUriTemplateHandler(uriBuilderFactory);
    }
    
    public CustomerBalance[] getAllBalances() {
        Customer[] customers = restTemplate.getForObject("/customers", Customer[].class);
        ArrayList<CustomerBalance> balances = new ArrayList<>();
        for(Customer customer : customers) {
            Product[] products = restTemplate.getForObject("/customers/" + customer.getId().toString() + "/products", Product[].class);
            if (products == null) continue;
            BigDecimal aggregatedBalance = BigDecimal.ZERO;
            for (Product product : products) {
                aggregatedBalance = aggregatedBalance.add(product.getBalance());
            }
            balances.add(new CustomerBalance(customer.getId(), customer.getName(), aggregatedBalance));
        }
        return balances.toArray(new CustomerBalance[]{});
    }
}
