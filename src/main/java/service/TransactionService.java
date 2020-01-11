package service;

import exception.BusinessException;
import model.Account;
import model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

/**
 * This service class is responsible for creating, managing transactions and transfers between
 * the accounts
 */
public class TransactionService {

    /**
     * Transaction ID generator
     */
    AtomicLong transactionIdGenerator  = new AtomicLong();

    /**
     * A map to store all the transactions
     */
    Map<Long, Transaction> transactionMap = new ConcurrentHashMap<>();

    /**
     * Account service for account related operations
     */
    AccountService accountService;

    public TransactionService(AccountService accountService){
        this.accountService = accountService;
    }

    /**
     * Creates a new transaction based on the parameters provided
     * @param amount - The transaction amount
     * @param fromAccountId - The source account ID
     * @param toAccountId - The destination account ID
     * @return - the newly created transaction object
     */
    public Transaction createTransaction(BigDecimal amount, long fromAccountId, long toAccountId){
        Account fromAccount = accountService.getAccountById(fromAccountId);
        Account toAccount  = accountService.getAccountById(toAccountId);
        transfer(fromAccount, toAccount, amount);
        long transactionId = transactionIdGenerator.incrementAndGet();
        Transaction newTransaction = new Transaction(transactionId, amount, fromAccountId, toAccountId);
        transactionMap.put(transactionId, newTransaction);
        fromAccount.getDebits().add(newTransaction);
        toAccount.getCredits().add(newTransaction);
        return newTransaction;


    }

    /**
     * Fetches a given transaction based on the ID
     * @param transactionId - The transaction ID
     * @return - The required transaction object
     */
    public Transaction getTransaction(long transactionId){
        Transaction transaction = transactionMap.get(transactionId);
        if(transaction == null){
            throw new BusinessException("Transaction ID not found");
        }
        return transaction;
    }

    /**
     * Fetches all the transactions from the storage
     * @return  - List of all transaction objects
     */
    public List<Transaction> getAllTransactions(){
        return transactionMap.values().stream().collect(Collectors.toList());
    }

    /**
     * This method is responsible for performing transfer of amount between the accounts
     * @param fromAccount - The source account object
     * @param toAccount - THe destination account object
     * @param amount - The transaction amount
     */
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount){

        //Validate if accounts objects exist
        if(fromAccount == null){
            throw new BusinessException("Source account not found");
        } else if(toAccount == null){
            throw new BusinessException("Destination account not found");
        }else {
            long fromAccountId = fromAccount.getId();
            long toAccountId = toAccount.getId();

            //Do nothing if source and destination accounts are same
            if (fromAccountId == toAccountId) {
                throw new BusinessException("Source and Destination Account ID's cannot be same");
            }

            //Check if the source account has sufficient balance to perform the transfer
            if (amount.compareTo(fromAccount.getBalance()) <= 0) {

                //All good, proceed to transfer

                //Apply locks in sequential order - to avoid deadlocks - fetch lock objects based on the ID

                //lock of lesser account ID - lock1 object
                ReadWriteLock lock1 = fromAccountId < toAccountId ? fromAccount.getLock() : toAccount.getLock();

                //lock of greater account ID - lock2 object
                ReadWriteLock lock2 = fromAccountId < toAccountId ? toAccount.getLock() : fromAccount.getLock();

                //Acquire Locks in order
                lock1.writeLock().lock();
                lock2.writeLock().lock();

                try {
                    //Perform transfer operations
                    fromAccount.withdrawAmount(amount);
                    toAccount.depositAmount(amount);
                } finally {
                    //Release locks
                    lock1.writeLock().unlock();
                    lock2.writeLock().unlock();
                }

            } else {
                throw new BusinessException("Source account balance is not sufficient to perform transfer");
            }
        }

    }
}
