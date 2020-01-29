package CustomerConsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerClient {

    private final RestTemplate restTemplate;

    public CustomerClient(@Value("${app.baseurl}") String baseUrl) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        this.restTemplate = new RestTemplate();
        this.restTemplate.setUriTemplateHandler(uriBuilderFactory);
    }
    
    public Customer[] findCustomers(String name, CustomerStatus status){
        Map<String, String> vars = new HashMap<>();
        vars.put("name", name);
        vars.put("status", status.toString());

        return restTemplate.getForObject("/customers?name={name}&status={status}",
                Customer[].class, vars);
    }

    public Product[] getAllProducts() {
        return restTemplate.getForObject("/products", Product[].class);
    }
}
