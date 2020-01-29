package CustomerConsumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
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
public class CustomerSearchTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("customer_provider", "localhost", 8080, this);

    @Autowired
    private CustomerClient restClient;

    @Pact(consumer = "customer_consumer3")
    public RequestResponsePact getCustomersByNameFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/customers")
                .query("name=el&status=ANY")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((a) -> {
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

    @Pact(consumer = "customer_consumer3")
    public RequestResponsePact getCustomersByNameAndStatusFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/customers")
                .query("name=el&status=ACTIVE")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((a) -> {
                    a.object(o -> o
                            .numberValue("id", 4)
                            .stringValue("name", "Terrell Guzman")
                            .stringValue("email", "terrell.guzman@example.com")
                            .stringValue("status", "ACTIVE"));
                }).build())
                .toPact();
    }

    @Pact(consumer = "customer_consumer3")
    public RequestResponsePact getCustomersByStatusFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/customers")
                .query("name=&status=ACTIVE")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((a) -> {
                    a.object(o -> o
                            .numberValue("id", 1)
                            .stringValue("name", "Patsy Miles")
                            .stringValue("email", "patsy.miles@example.com")
                            .stringValue("status", "ACTIVE"));
                    a.object(o -> o
                            .numberValue("id", 4)
                            .stringValue("name", "Terrell Guzman")
                            .stringValue("email", "terrell.guzman@example.com")
                            .stringValue("status", "ACTIVE"));
                }).build())
                .toPact();
    }

    @Test
    @PactVerification(fragment = "getCustomersByNameFragment")
    public void shouldFindAllCustomersByName() {
        Customer[] l = new Customer[] {
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
                new Customer(5,"Elias Hudson", "elias.hudson@example.com", CustomerStatus.INACTIVE)
        };
        Assert.assertArrayEquals(l, restClient.findCustomers("el", CustomerStatus.ANY));
    }

    @Test
    @PactVerification(fragment = "getCustomersByNameAndStatusFragment")
    public void shouldFindAllCustomersByNameAndStatus() {
        Customer[] l = new Customer[]{
                new Customer(4, "Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
        };
        Assert.assertArrayEquals(l, restClient.findCustomers("el", CustomerStatus.ACTIVE));
    }

    @Test
    @PactVerification(fragment = "getCustomersByStatusFragment")
    public void shouldFindAllCustomersByStatus() {
        Customer[] l = new Customer[]{
                new Customer(1,"Patsy Miles", "patsy.miles@example.com", CustomerStatus.ACTIVE),
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
        };
        Assert.assertArrayEquals(l, restClient.findCustomers(null, CustomerStatus.ACTIVE));
    }
}
