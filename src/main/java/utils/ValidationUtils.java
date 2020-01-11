package utils;

import java.math.BigDecimal;

/**
 * Utility methods for input validation
 */
public class ValidationUtils {

    /**
     * Validate for empty, negative and non-numeric account/transaction ID's strings
     * @param strId - The ID string passed in the request
     */
    public static void validateId(String strId){

        try {
            validateNumber(Long.parseLong(strId));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID value is not a number");
        }

    }

    /**
     * Validate for account/transaction number ID's.
     * @param id - The ID in number format
     */
    public static void validateNumber(long id){
        if (id <= 0){
            throw new IllegalArgumentException("ID value should be positive");
        }
    }

    /**
     * Validate for account balance/transaction amount
     * @param balance - The value of balance/amount
     */
    public static void validateBalance(BigDecimal balance){
        if (balance == null) {
            throw new IllegalArgumentException("Balance/Amount value is required");
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Balance/Amount should be positive");
        }
    }
}
