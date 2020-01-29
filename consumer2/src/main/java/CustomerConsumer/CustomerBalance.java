package CustomerConsumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBalance {
    private Integer customerId;
    private String customerName;
    private BigDecimal aggregatedBalance;
}
