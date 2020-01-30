package CustomerConsumer;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllProductsAndFindCustomersCliTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Mock
    CustomerClient clientMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void getAllProductsCliTest() throws Exception {
         Product[] products = new Product[] {
                new Product(1, "term deposit account", 0.005),
                new Product(2, "home loan", 0.02),
                new Product(3, "vehicle loan", 0.04),
                new Product(4, "pension backed loan", 0.01),
                new Product(5, "student loan", 0.003)
        };
        when(clientMock.getAllProducts(0)).thenReturn(products);

        String expectedOutput = "1,term deposit account,0.005000\n" +
                                "2,home loan,0.020000\n" +
                                "3,vehicle loan,0.040000\n" +
                                "4,pension backed loan,0.010000\n" +
                                "5,student loan,0.003000\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("products", "0");

        verify(clientMock).getAllProducts(0);
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }

    @Test
    public void findCustomersCliTest1() throws Exception {
        Customer[] customers = new Customer[] {
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
                new Customer(5,"Elias Hudson", "elias.hudson@example.com", CustomerStatus.INACTIVE)
        };
        when(clientMock.findCustomers("el", CustomerStatus.ANY)).thenReturn(customers);

        String expectedOutput =
                "4,Terrell Guzman,terrell.guzman@example.com,ACTIVE\n" +
                "5,Elias Hudson,elias.hudson@example.com,INACTIVE\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("customers", "el", "ANY");

        verify(clientMock).findCustomers("el", CustomerStatus.ANY);
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }

    @Test
    public void findCustomersCliTest2() throws Exception {
        Customer[] customers = new Customer[] {
                new Customer(5,"Elias Hudson", "elias.hudson@example.com", CustomerStatus.INACTIVE)
        };
        when(clientMock.findCustomers("el", CustomerStatus.INACTIVE)).thenReturn(customers);

        String expectedOutput =
                "5,Elias Hudson,elias.hudson@example.com,INACTIVE\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("customers", "el", "INACTIVE");

        verify(clientMock).findCustomers("el", CustomerStatus.INACTIVE);
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }

    @Test
    public void findCustomersCliTest3() throws Exception {
        Customer[] customers = new Customer[] {
                new Customer(1,"Patsy Miles", "patsy.miles@example.com", CustomerStatus.ACTIVE),
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
        };
        when(clientMock.findCustomers("", CustomerStatus.ACTIVE)).thenReturn(customers);

        String expectedOutput =
                "1,Patsy Miles,patsy.miles@example.com,ACTIVE\n" +
                "4,Terrell Guzman,terrell.guzman@example.com,ACTIVE\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("customers", "", "ACTIVE");

        verify(clientMock).findCustomers("", CustomerStatus.ACTIVE);
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }
}
