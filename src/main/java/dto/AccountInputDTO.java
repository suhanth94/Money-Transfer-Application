package dto;

import java.math.BigDecimal;

/**
 * This class is for handling account input JSON request during a new account creation
 */
public class AccountInputDTO {

    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }
}
