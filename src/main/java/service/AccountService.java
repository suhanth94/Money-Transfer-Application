package service;

import exception.BusinessException;
import model.Account;
import model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * This service class is responsible for creating and managing accounts
 */
public class AccountService {


    /**
     * Account ID generator
     */
    AtomicLong accountIdGenerator  = new AtomicLong();

    /**
     * A map to store all the created accounts
     */
    Map<Long, Account> accountMap = new ConcurrentHashMap<>();

    /**
     * This method creates a new account and assigns an ID
     * @param balance - The initial balance in the account
     * @return - the newly created account object
     */
    public Account createAccount(BigDecimal balance){
        long newAccountId = accountIdGenerator.incrementAndGet();
        Account newAccount = new Account(newAccountId, balance);
        accountMap.put(newAccountId, newAccount);
        return newAccount;

    }

    /**
     * Fetches the required account object
     * @param accountId - The account ID
     * @return - the selected account object
     */
    public Account getAccountById(long accountId){
        Account account  = accountMap.get(accountId);
        if(account == null){
            throw new BusinessException("Account ID not found");
        }
        return account;
    }


    /**
     * Fetches all the credits of given an account ID
     * @param accountId - The account ID
     * @return - list of credit transactions
     */
    public List<Transaction> getAccountCreditsById(long accountId){
        return accountMap.get(accountId).getCredits();
    }

    /**
     * Fetches all the debits of given an account ID
     * @param accountId - The account ID
     * @return - list of debit transactions
     */
    public List<Transaction> getAccountDebitsById(long accountId){
        return accountMap.get(accountId).getDebits();
    }

    /**
     * Fetches all the accounts and their information
     * @return - list of all accounts
     */
    public List<Account> getAllAccounts(){
        return accountMap.values().stream().collect(Collectors.toList());
    }


}
