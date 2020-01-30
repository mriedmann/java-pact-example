package CustomerConsumer;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMaxLike;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "app.baseurl:http://localhost:8080",
        classes = CustomerClient.class)
public class ProductTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("customer_provider", "localhost", 8080, this);

    @Autowired
    private CustomerClient restClient;

    @Pact(consumer = "customer_consumer3")
    public RequestResponsePact getProductsFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("GetAllProducts")
                .path("/products")
                .query("offset=0")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(50, (a) -> {
                    a.object(o -> o
                            .numberType("id", 1)
                            .stringType("name", "term deposit account")
                            .decimalType("interestRate", new BigDecimal("0.005")));
                }).build())
                .toPact();
    }

    @Test
    @PactVerification(fragment = "getProductsFragment")
    public void shouldGetAllProducts() {
        Product[] products = new Product[] {
                new Product(1, "term deposit account", 0.005),
        };
        Assert.assertArrayEquals(products, restClient.getAllProducts(0));
    }
}
