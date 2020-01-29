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

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArray;

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
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/customers")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((a) -> {
                    a.object(o -> o
                            .numberValue("id", 1)
                            .stringValue("name", "Patsy Miles")
                            .stringValue("email", "patsy.miles@example.com")
                            .stringValue("status","ACTIVE"));
                    a.object(o -> o
                            .numberValue("id", 2)
                            .stringValue("name", "Floyd Brock")
                            .stringValue("email", "floyd.brock@example.com")
                            .stringValue("status", "INACTIVE"));
                    a.object(o -> o
                            .numberValue("id", 3)
                            .stringValue("name", "Inez Ray")
                            .stringValue("email", "inez.ray@example.com")
                            .stringValue("status","LOCKED"));
                    a.object(o -> o
                            .numberValue("id", 4)
                            .stringValue("name", "Terrell Guzman")
                            .stringValue("email", "terrell.guzman@example.com")
                            .stringValue("status", "ACTIVE"));
                    a.object(o -> o
                            .numberValue("id", 5)
                            .stringValue("name", "Elias Hudson")
                            .stringValue("email", "elias.hudson@example.com")
                            .stringValue("status", "INACTIVE"));
                }).build())
                .toPact();
    }

    @PactVerification(fragment = "createFragment")
    @Test
    public void shouldGetAllCustomersWithNameEmailAndStatus() {
        Customer[] l = new Customer[] {
                new Customer(1,"Patsy Miles", "patsy.miles@example.com", CustomerStatus.ACTIVE),
                new Customer(2,"Floyd Brock", "floyd.brock@example.com", CustomerStatus.INACTIVE),
                new Customer(3,"Inez Ray", "inez.ray@example.com", CustomerStatus.LOCKED),
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
                new Customer(5,"Elias Hudson", "elias.hudson@example.com", CustomerStatus.INACTIVE)
        };
        Assert.assertArrayEquals(l, restClient.getAllCustomers());
    }
}
