package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CentralBankTest {

    @Test
    public void createAccountTest() throws InsufficientFundsException {
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.c", "abcdef1@", "checking",100);

        //EQ: 1 account created
        assertEquals(1, centralBank.getbankAccountsLength());

        //EQ: 100 accounts created
        for (int i = 1; i < 100; i++) centralBank.createAccount(i+""+"@mail.com", "abcdef1@","checking",100);
        assertEquals(100, centralBank.getbankAccountsLength());

        //EQ: 101 accounts created (exceeding limit - border case)
        centralBank.createAccount("b@c.d", "abcdef1@","checking",100);
        assertEquals(101, centralBank.getbankAccountsLength());

        // EQ: create an account with an existing id (border case)
        centralBank.createAccount("1@mail.com", "abcdef1@", "checking",100);
        assertEquals(101, centralBank.getbankAccountsLength());
    }

    @Test
    public void closeAccountTest() throws FrozenAccountException {
        CentralBank centralBank = new CentralBank();
        // fill bank with accounts
        for (int i = 1; i < 201; i++) centralBank.createAccount(i+""+"@mail.com", "abcdef1@", "checking",100);

        // EQ: close 1 account
        centralBank.closeAccount("1@mail.com");
        assertEquals(199, centralBank.getbankAccountsLength());
        assertEquals(1, centralBank.getClosedAccountsLength());

        // EQ: close 100 accounts
        for (int i = 2; i < 102; i++) centralBank.closeAccount(i+"@mail.com");
        assertEquals(99, centralBank.getbankAccountsLength());
        assertEquals(101, centralBank.getClosedAccountsLength());

        // EQ: close account with invalid id (border case)
        centralBank.closeAccount("abcdef@mail.com");
        assertEquals(99, centralBank.getbankAccountsLength());
        assertEquals(101, centralBank.getClosedAccountsLength());
    }



    @Test
    public void withdrawDepositTransferTest() throws FrozenAccountException, InsufficientFundsException {
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@mail.com","abcdef1@","checking", 500);
        centralBank.deposit("a@mail.com",500);
        assertEquals(1000,centralBank.checkBalance("a@mail.com"));
        centralBank.withdraw("a@mail.com",250);
        assertEquals(750,centralBank.checkBalance("a@mail.com"));
        centralBank.createAccount("b@mail.com","abcdef1@","checking", 500);
        centralBank.transfer("a@mail.com","b@mail.com",250);
        assertEquals(500,centralBank.checkBalance("a@mail.com"));
        assertEquals(750,centralBank.checkBalance("b@mail.com"));
    }

    @Test
    public void transactionHistoryTest() {
        CentralBank centralBank = new CentralBank();

        for (int i = 1; i < 10; i++) centralBank.createAccount(i+"@mail.com", "abcdef1@","checking", 1);

        // EQ: Non-existent account
        assertEquals(true, centralBank.transactionHistory("a@b.com").equals("No such account"));

        // EQ: Existing account
        String history = centralBank.transactionHistory("1@mail.com");
        assertEquals(true, !history.equals("No such account") && history.length() > 0);
    }

    @Test public void findAccountTest() {
        CentralBank centralBank = new CentralBank();

        for (int i = 1; i < 10; i++) centralBank.createAccount(i+"@mail.com", "abcdef1@","checking", 1);

        // EQ: Non-existent account
        assertNull(centralBank.findAccount("a@b.com"));

        // EQ: Existing account
        String history = centralBank.transactionHistory("1@mail.com");
        assertNotNull(centralBank.findAccount("1@mail.com"));

        //EQ: Customer with two accounts
        centralBank.createAccount("1@mail.com", "abcdef1@", "savings", 1);
        assertEquals("savings", centralBank.findAccount("1@mail.com", "savings"));
    }

    @Test
    void freezeTest() throws InsufficientFundsException, FrozenAccountException {
        CentralBank newBank = new CentralBank();
        newBank.createAccount("a@b.com", "abcdef1@", "checking", 1.00);
        //Basic freeze test for all methods.
        newBank.freezeAccount("a@b.com");
        assertThrows(FrozenAccountException.class, ()-> newBank.findAccount("a@b.com").withdraw(1));
        assertThrows(FrozenAccountException.class, ()-> newBank.findAccount("a@b.com").deposit(1));
        BankAccount bankAccount2 = new BankAccount("b@a.com", "abcdef1@","checking",2.00);
        assertThrows(FrozenAccountException.class, ()-> newBank.findAccount("a@b.com").transfer(1, bankAccount2));
        assertThrows(FrozenAccountException.class, ()->bankAccount2.transfer(1,  newBank.findAccount("a@b.com")));
        //Check that methods work after unfreezing.
        newBank.unfreezeAcct("a@b.com");
        newBank.findAccount("a@b.com").withdraw(1);
        assertEquals(0,  newBank.findAccount("a@b.com").getBalance());
        newBank.findAccount("a@b.com").deposit(2);
        assertEquals(2,  newBank.findAccount("a@b.com").getBalance());
        newBank.findAccount("a@b.com").transfer(2, bankAccount2);
        assertEquals(0,  newBank.findAccount("a@b.com").getBalance());
        bankAccount2.transfer(2,  newBank.findAccount("a@b.com"));
        assertEquals(2,  newBank.findAccount("a@b.com").getBalance());
    }

    @Test
    void suspActTest() throws InsufficientFundsException, FrozenAccountException {
        //Basic test
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", "abcdef1@","checking",2.00);
        for (int i = 0; i < 150; i++){
            centralBank.getBankAccounts().get(0).withdraw(0.01);
        }
        Collection<String> testCollect = new ArrayList<String>();
        testCollect = centralBank.findAcctIdsWithSuspiciousActivity();
        assertEquals(false, testCollect.isEmpty());
        centralBank.getBankAccounts().get(0).dayPasses();
        testCollect = centralBank.findAcctIdsWithSuspiciousActivity();
        assertEquals(true, testCollect.isEmpty());
    }

    @Test
    void totalTest() {
        //Standard test
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", "abcdef1@", "checking",1.00);
        double checker = centralBank.calcTotalAssets();
        assertEquals(1.00, checker);
        centralBank.createAccount("b@a.com", "abcdef1@","checking",2.00);
        checker = centralBank.calcTotalAssets();
        assertEquals(3.00, checker);
        //0 Test
        CentralBank centralBank2 = new CentralBank();
        centralBank2.createAccount("a@b.com", "abcdef1@","checking",1.00);
        centralBank2.createAccount("b@a.com", "abcdef1@","checking",0.00);
        checker = centralBank2.calcTotalAssets();
        assertEquals(1.00, checker);
    }
}
