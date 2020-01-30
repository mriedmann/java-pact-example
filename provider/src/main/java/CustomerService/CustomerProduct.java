package CustomerService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProduct {
    private long id;
    private long customerId;
    private long productId;
    private BigDecimal balance;
}
