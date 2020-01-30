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

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMaxLike;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "app.baseurl:http://localhost:8080",
        classes = CustomerClient.class)
public class CustomerStatusViewerTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("customer_provider", "localhost", 8080, this);

    @Autowired
    private CustomerClient restClient;

    @Pact(consumer = "customer_consumer1")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("GetAllCustomersWithNameEmailAndStatus with offset 0")
                .path("/customers")
                .query("offset=0")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(50, (a) -> {
                    a.object(o -> o
                            .numberType("id", 1)
                            .stringType("name", "Patsy Miles")
                            .stringType("email", "patsy.miles@example.com")
                            .stringType("status","ACTIVE"));
                }).build())
                .toPact();
    }

    @PactVerification(fragment = "createFragment")
    @Test
    public void shouldGetAllCustomersWithNameEmailAndStatus() {
        Customer[] l = new Customer[] {
                new Customer(1,"Patsy Miles", "patsy.miles@example.com", CustomerStatus.ACTIVE)
        };
        Assert.assertArrayEquals(l, restClient.getAllCustomers(0));
    }
}
