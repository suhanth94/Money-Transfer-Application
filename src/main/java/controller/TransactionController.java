package controller;

import com.google.gson.Gson;
import dto.TransactionInputDTO;
import org.eclipse.jetty.http.HttpStatus;
import service.TransactionService;
import spark.Route;
import utils.ValidationUtils;

import java.math.BigDecimal;

/**
 * This class handles the controls for transaction REST API calls and processes
 * request/response accordingly
 */
public class TransactionController {

    private TransactionService transactionService;
    private Gson gsonInstance  = new Gson();

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    public Route getTransactionById(){

        return (request, response) -> {
            String strTrxId  = request.params(":id");
            ValidationUtils.validateId(strTrxId);
            long transactionId = Long.parseLong(strTrxId);
            response.status(HttpStatus.OK_200);
            return gsonInstance.toJsonTree(transactionService.getTransaction(transactionId));
        };
    }

    public Route postTransaction(){
        return (request, response) -> {
            TransactionInputDTO transactionInput = gsonInstance.fromJson(request.body(), TransactionInputDTO.class);
            BigDecimal inputAmount = transactionInput.getAmount();
            ValidationUtils.validateBalance(inputAmount);
            long fromAccountId = transactionInput.getFromAccountId();
            ValidationUtils.validateNumber(fromAccountId);
            long toAccountId = transactionInput.getToAccountId();
            ValidationUtils.validateNumber(toAccountId);
            response.status(HttpStatus.CREATED_201);
            return gsonInstance.toJsonTree(
                    transactionService.createTransaction(inputAmount, fromAccountId, toAccountId));
        };

    }

    public Route getAllTransactions(){
        return (request, response) -> {
            response.status(HttpStatus.OK_200);
            return gsonInstance.toJsonTree(transactionService.getAllTransactions());
        };

    }
}
