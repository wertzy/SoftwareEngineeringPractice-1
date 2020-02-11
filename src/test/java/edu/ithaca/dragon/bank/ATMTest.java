package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    @Test
    public void credentialsTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm = new ATM();
        BankAccount bankAccount = new BankAccount("a@b.com", "a", 1.00);
        //Passing
        assertEquals(true, atm.confirmCredentials("a@b.com", "a", bankAccount));
        //Failure for email
        assertFalse(atm.confirmCredentials("a@.com", "a", bankAccount));
        //Failure for password
        assertFalse(atm.confirmCredentials("a@b.com", "b", bankAccount));
        //Failure for both
        assertFalse(atm.confirmCredentials("a@.com", "v", bankAccount));
    }

    @Test
    public void withdrawTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba=new BankAccount("a@mail.com","a",1000);
        atm.withdraw(ba,500);
        assertEquals(500,atm.checkBalance(ba));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,1000));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,-500));

    }

    @Test
    public void depositTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba=new BankAccount("a@mail.com","a", 1000);
        atm.deposit(ba,500);
        assertEquals(1500,atm.checkBalance(ba));
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(ba,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(ba,-500));

    }

    @Test
    public void transferTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba1=new BankAccount("a@mail.com", "a", 1000);
        BankAccount ba2=new BankAccount("a@mail.com","a", 1000);
        atm.transfer(ba1,ba2,500);
        assertEquals(500,atm.checkBalance(ba1));
        assertEquals(1500,atm.checkBalance(ba2));
        assertThrows(IllegalArgumentException.class, () -> atm.transfer(ba1,ba2,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.transfer(ba1,ba2,-500));
        assertThrows(IllegalArgumentException.class, () -> atm.transfer(ba1,ba2,1000));
    }

    @Test
    public void transactionHistoryTest() {
        try {
            BankAccount bankAccount = new BankAccount("a@mail.com", 1);
            ATM atm = new ATM();
            int headerLength = "total transactions:".length();

            // EQ: without transaction history
            String transactionCountStart = atm.transactionHistory(bankAccount).substring(headerLength);
            String strCount = "";
            int i = 0, transactionCount = 0;
            while (transactionCountStart.charAt(i) != '\n') strCount += transactionCountStart.charAt(i++);
            transactionCount = Integer.parseInt(strCount);
            assertEquals(0, transactionCount);

            // EQ: with transaction history
            for (i = 1; i < 51; i++) bankAccount.deposit(i);
            for (i = 1; i < 51; i++) bankAccount.withdraw(i);
            transactionCountStart = atm.transactionHistory(bankAccount).substring(headerLength);
            i = 0;
            strCount = "";
            while (transactionCountStart.charAt(i) != '\n') strCount += transactionCountStart.charAt(i++);
            transactionCount = Integer.parseInt(strCount);
            assertEquals(100, transactionCount);
            System.out.println(atm.transactionHistory(bankAccount));
        } catch(Exception e) {
            fail(e.getLocalizedMessage());
        }
    }
}
