package CustomerConsumer;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FinancialDetailsCliTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private CustomerBalance[] balances;

    @Mock
    CustomerClient clientMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        balances = new CustomerBalance[] {
                new CustomerBalance(1, "Patsy Miles", new BigDecimal("1000.11")),
                new CustomerBalance(2, "Floyd Brock", new BigDecimal("20000.22")),
                new CustomerBalance(3, "Inez Ray", new BigDecimal("300000.33")),
                new CustomerBalance(4, "Terrell Guzman", new BigDecimal("4000000.44")),
                new CustomerBalance(5, "Elias Hudson", new BigDecimal("50000000.55"))
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
        when(clientMock.getAllBalances()).thenReturn(balances);

        String expectedOutput = "1,Patsy Miles,1000.110000\n" +
                                "2,Floyd Brock,20000.220000\n" +
                                "3,Inez Ray,300000.330000\n" +
                                "4,Terrell Guzman,4000000.440000\n" +
                                "5,Elias Hudson,50000000.550000\n";
        ConsumerRunner runner = new ConsumerRunner(clientMock);

        runner.run("");

        verify(clientMock).getAllBalances();
        Assert.assertEquals(expectedOutput, outContent.toString());
        Assert.assertEquals("", errContent.toString());
    }
}
