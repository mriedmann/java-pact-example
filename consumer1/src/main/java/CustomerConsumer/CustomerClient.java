package CustomerConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Autowired
    public CustomerClient(@Value("${app.baseurl}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public Customer[] getAllCustomers(Integer offset){
        return restTemplate.getForObject("/customers?offset={offset}", Customer[].class, offset);
    }
}
