package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CentralBankTest {

    @Test
    public void createAccountTest() throws InsufficientFundsException {
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.c", 100);

        //EQ: 1 account created
        assertEquals(1, centralBank.getbankAccountsLength());

        //EQ: 100 accounts created
        for (int i = 1; i < 100; i++) centralBank.createAccount(i+""+"@mail.com", 100);
        assertEquals(100, centralBank.getbankAccountsLength());

        //EQ: 101 accounts created (exceeding limit - border case)
        centralBank.createAccount("b@c.d", 100);
        assertEquals(101, centralBank.getbankAccountsLength());

        // EQ: create an account with an existing id (border case)
        centralBank.createAccount("1@mail.com", 100);
        assertEquals(101, centralBank.getbankAccountsLength());
    }

    @Test
    public void closeAccountTest() {
        CentralBank centralBank = new CentralBank();
        // fill bank with accounts
        for (int i = 1; i < 201; i++) centralBank.createAccount(i+""+"@mail.com", 100);

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
        centralBank.createAccount("a@mail.com",500);
        centralBank.deposit("a@mail.com",500);
        assertEquals(1000,centralBank.checkBalance("a@mail.com"));
        centralBank.withdraw("a@mail.com",250);
        assertEquals(750,centralBank.checkBalance("a@mail.com"));
        centralBank.createAccount("b@mail.com",500);
        centralBank.transfer("a@mail.com","b@mail.com",250);
        assertEquals(500,centralBank.checkBalance("a@mail.com"));
        assertEquals(750,centralBank.checkBalance("b@mail.com"));
    }
}
