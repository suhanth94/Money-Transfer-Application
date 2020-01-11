package service;

import exception.BusinessException;
import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransactionServiceTest {

    private AccountService accountService;

    private TransactionService transactionService;

    private Account[] testData = new Account[3];

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
        transactionService = new TransactionService(accountService);
        testData[0] = new Account(1, new BigDecimal(25.24));
        testData[1] = new Account(2, new BigDecimal(35.24));
        testData[2] = new Account(3, new BigDecimal(102.24));

    }

    @Test
    public void testNormalTransfer(){
        BigDecimal transactionAmt = new BigDecimal(15.43);
        BigDecimal initialBalanceSrc = testData[0].getBalance();
        BigDecimal initialBalanceDest = testData[1].getBalance();
        transactionService.transfer(testData[0], testData[1], transactionAmt);
        Assertions.assertEquals(testData[0].getBalance(),initialBalanceSrc.subtract(transactionAmt));
        Assertions.assertEquals(testData[1].getBalance(),initialBalanceDest.add(transactionAmt));
    }

    @Test
    public void testMultipleTransfer(){
        BigDecimal transactionAmt = new BigDecimal(15.43);
        BigDecimal initialBalanceSrc = testData[0].getBalance();
        BigDecimal initialBalanceDest = testData[1].getBalance();
        transactionService.transfer(testData[0], testData[1], transactionAmt);
        transactionService.transfer(testData[1], testData[2], transactionAmt);
        Assertions.assertEquals(testData[0].getBalance(),initialBalanceSrc.subtract(transactionAmt));
        Assertions.assertEquals(testData[1].getBalance(),initialBalanceDest);
    }

    @Test
    public void testTransferWithSameSourceDestinationAccount(){
        Assertions.assertThrows(BusinessException.class, () -> {
            BigDecimal transactionAmt = new BigDecimal(15.43);
            transactionService.transfer(testData[0],testData[0],transactionAmt);
        });
    }

    @Test
    public void testTransferWithNotEnoughBalance(){
        Assertions.assertThrows(BusinessException.class, () -> {
            BigDecimal transactionAmt = new BigDecimal(155.43);
            transactionService.transfer(testData[0],testData[0],transactionAmt);
        });
    }

}