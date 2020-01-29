package CustomerService;

import java.math.BigDecimal;

public class Product {
    private final long id;
    private final String name;
    private final BigDecimal balance;
    private final BigDecimal interestRate;

    public Product(long id, String name, BigDecimal balance, BigDecimal interestRate) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
