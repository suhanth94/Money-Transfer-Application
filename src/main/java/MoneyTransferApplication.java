import controller.AccountController;
import controller.TransactionController;
import service.AccountService;
import service.TransactionService;
import utils.CustomExceptionHandler;
import utils.ResponseInterceptor;

import static spark.Spark.*;

/**
 * This is the main class of the money transfer application which initializes the REST API and
 * injects its corresponding controllers
 */
public class MoneyTransferApplication {

    private AccountController accountController;
    private TransactionController transactionController;

    public MoneyTransferApplication(){
        AccountService accountService = new AccountService();
        this.accountController = new AccountController(accountService);
        this.transactionController = new TransactionController(new TransactionService(accountService));
    }

    /**
     * Initializes the REST API
     */
    public void initApi(){

        //Account API

        get("/accounts/:id", accountController.getAccountById());

        post("/accounts", accountController.postAccount());

        get("/accounts", accountController.getAllAccounts());

        get("/accounts/:id/credits", accountController.getAccountCreditsById());

        get("/accounts/:id/debits", accountController.getAccountDebitsById());

        //Transaction API

        get("/transactions/:id", transactionController.getTransactionById());

        post("/transactions", transactionController.postTransaction());

        get("/transactions", transactionController.getAllTransactions());

        //Response filter to append content type
        after(new ResponseInterceptor().getFilter());

        //Exception handler
        exception(Exception.class, new CustomExceptionHandler());

    }

    public static void main(String args[]){
        new MoneyTransferApplication().initApi();
    }
}
