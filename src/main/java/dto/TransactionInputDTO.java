package dto;

import java.math.BigDecimal;

/**
 * This class is for handling transaction input JSON request during a new transaction creation
 */
public class TransactionInputDTO {

    private BigDecimal amount;
    private long fromAccountId;
    private long toAccountId;

    public BigDecimal getAmount() {
        return amount;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }
}
