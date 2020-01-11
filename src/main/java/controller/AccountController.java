package controller;

import com.google.gson.Gson;
import dto.AccountInputDTO;
import org.eclipse.jetty.http.HttpStatus;
import service.AccountService;
import spark.Route;
import utils.ValidationUtils;

import java.math.BigDecimal;

/**
 * This class handles the controls for account REST API calls and processes
 * request/response accordingly
 */
public class AccountController {

    private AccountService accountService;
    private Gson gsonInstance  = new Gson();

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    public Route getAccountById(){
        return (request, response) ->{

            String strAcctId  = request.params(":id");
            ValidationUtils.validateId(strAcctId);
            long accountId = Long.parseLong(strAcctId);
            response.status(HttpStatus.OK_200);
            return gsonInstance.toJsonTree(accountService.getAccountById(accountId));

        };
    }

    public Route getAllAccounts(){
        return (request, response) ->{
            response.status(HttpStatus.OK_200);
            return gsonInstance.toJsonTree(accountService.getAllAccounts());

        };

    }

    public Route getAccountCreditsById(){
        return (request, response) ->{

            String strAcctId  = request.params(":id");
            ValidationUtils.validateId(strAcctId);
            long accountId = Long.parseLong(strAcctId);
            response.status(HttpStatus.OK_200);
            response.type("application/json");
            return gsonInstance.toJsonTree(accountService.getAccountCreditsById(accountId));

        };
    }

    public Route getAccountDebitsById(){
        return (request, response) ->{

            String strAcctId  = request.params(":id");
            ValidationUtils.validateId(strAcctId);
            long accountId = Long.parseLong(strAcctId);
            response.status(HttpStatus.OK_200);
            return gsonInstance.toJsonTree(accountService.getAccountDebitsById(accountId));

        };
    }


    public Route postAccount(){
        return (request, response) -> {
            AccountInputDTO newAccountRequest = gsonInstance.fromJson(request.body(), AccountInputDTO.class);
            BigDecimal inputBalance = newAccountRequest.getBalance();
            ValidationUtils.validateBalance(inputBalance);
            response.status(HttpStatus.CREATED_201);
            return gsonInstance.toJsonTree(accountService.createAccount(inputBalance));

        };
    }
}
