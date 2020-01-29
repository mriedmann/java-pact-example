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

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArray;

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
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((a) -> {
                    a.object(o -> o
                            .numberValue("id", 1)
                            .stringValue("name", "term deposit account")
                            .stringValue("interestRate", "0.005"));
                    a.object(o -> o
                            .numberValue("id", 2)
                            .stringValue("name", "home loan")
                            .stringValue("interestRate", "0.02"));
                    a.object(o -> o
                            .numberValue("id", 3)
                            .stringValue("name", "vehicle loan")
                            .stringValue("interestRate", "0.04"));
                    a.object(o -> o
                            .numberValue("id", 4)
                            .stringValue("name", "pension backed loan")
                            .stringValue("interestRate", "0.01"));
                    a.object(o -> o
                            .numberValue("id", 5)
                            .stringValue("name", "student loan")
                            .stringValue("interestRate", "0.003"));
                }).build())
                .toPact();
    }

    @Test
    @PactVerification(fragment = "getProductsFragment")
    public void shouldGetAllProducts() {
        Product[] products = new Product[] {
                new Product(1, "term deposit account", new BigDecimal("0.005")),
                new Product(2, "home loan", new BigDecimal("0.02")),
                new Product(3, "vehicle loan", new BigDecimal("0.04")),
                new Product(4, "pension backed loan", new BigDecimal("0.01")),
                new Product(5, "student loan", new BigDecimal("0.003"))
        };
        Assert.assertArrayEquals(products, restClient.getAllProducts());
    }
}
