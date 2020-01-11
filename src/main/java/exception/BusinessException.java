package exception;

/**
 * New  custom exception class for business exceptions
 */

public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
