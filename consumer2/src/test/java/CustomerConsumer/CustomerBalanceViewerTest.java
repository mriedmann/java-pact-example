package CustomerConsumer;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArray;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMaxLike;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "app.baseurl:http://localhost:8080",
        classes = CustomerClient.class)
public class CustomerBalanceViewerTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("customer_provider", "localhost", 8080, this);

    @Autowired
    private CustomerClient restClient;

    @Pact(consumer = "customer_consumer2")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("GetAllCustomersBalances")
                .path("/customers")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(50, (a) -> {
                    a.object(o -> o
                            .numberType("id",1)
                            .stringType("name", "Patsy Miles"));
                    a.object(o -> o
                            .numberType("id",2)
                            .stringType("name", "Floyd Brock"));
                    a.object(o -> o
                            .numberType("id",3)
                            .stringType("name", "Inez Ray"));
                    a.object(o -> o
                            .numberType("id",4)
                            .stringType("name", "Terrell Guzman"));
                    a.object(o -> o
                            .numberType("id",5)
                            .stringType("name", "Elias Hudson"));
                }).build())
                .uponReceiving("GetCustomerProducts 1")
                .path("/customers/1/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(100, (a) -> {
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("100.10")));
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("900.01")));
                }).build())
                .uponReceiving("GetCustomerProducts 2")
                .path("/customers/2/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(100, (a) -> {
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("11000.20")));
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("9000.02")));
                }).build())
                .given("test state")
                .uponReceiving("GetCustomerProducts 3")
                .path("/customers/3/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(100, (a) -> {
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("210000.30")));
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("90000.03")));
                }).build())
                .uponReceiving("GetCustomerProducts 4")
                .path("/customers/4/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(100, (a) -> {
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("3100000.40")));
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("900000.04")));
                }).build())
                .given("test state")
                .uponReceiving("GetCustomerProducts 5")
                .path("/customers/5/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArrayMaxLike(100, (a) -> {
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("41000000.50")));
                    a.object(o -> o
                            .decimalType("balance", new BigDecimal("9000000.05")));
                }).build())
                .toPact();
    }

    @Test
    @PactVerification
    public void shouldGetAllCustomersBalances() {
        CustomerBalance[] l = new CustomerBalance[] {
                new CustomerBalance(1,"Patsy Miles", new BigDecimal("1000.11")),
                new CustomerBalance(2,"Floyd Brock", new BigDecimal("20000.22")),
                new CustomerBalance(3,"Inez Ray", new BigDecimal("300000.33")),
                new CustomerBalance(4,"Terrell Guzman", new BigDecimal("4000000.44")),
                new CustomerBalance(5,"Elias Hudson", new BigDecimal("50000000.55"))
        };
        Assert.assertArrayEquals(l, restClient.getAllBalances());
    }
}
