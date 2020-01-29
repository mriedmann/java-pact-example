package CustomerConsumer;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FinancialDetailsCliTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private Customer[] customers;

    @Mock
    CustomerClient clientMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        customers = new Customer[] {
                new Customer(1,"Patsy Miles", "patsy.miles@example.com", CustomerStatus.ACTIVE),
                new Customer(2,"Floyd Brock", "floyd.brock@example.com", CustomerStatus.INACTIVE),
                new Customer(3,"Inez Ray", "inez.ray@example.com", CustomerStatus.LOCKED),
                new Customer(4,"Terrell Guzman", "terrell.guzman@example.com", CustomerStatus.ACTIVE),
                new Customer(5,"Elias Hudson", "elias.hudson@example.com", CustomerStatus.INACTIVE)
        };
    }

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
    public void CliTest() throws Exception {
        when(clientMock.getAllCustomers()).thenReturn(customers);

        String expectedOutput = "1,Patsy Miles,patsy.miles@example.com,ACTIVE\n" +
                                "2,Floyd Brock,floyd.brock@example.com,INACTIVE\n" +
                                "3,Inez Ray,inez.ray@example.com,LOCKED\n" +
                                "4,Terrell Guzman,terrell.guzman@example.com,ACTIVE\n" +
                                "5,Elias Hudson,elias.hudson@example.com,INACTIVE\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("");

        verify(clientMock).getAllCustomers();
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }
}
