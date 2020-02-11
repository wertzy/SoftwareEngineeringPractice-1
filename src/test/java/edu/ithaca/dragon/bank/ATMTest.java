package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    @Test
    public void withdrawTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba=new BankAccount("a@mail.com",1000);
        atm.withdraw(ba,500);
        assertEquals(500,atm.checkBalance(ba));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,1000));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,-500));

    }

    @Test
    public void depositTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba=new BankAccount("a@mail.com",1000);
        atm.deposit(ba,500);
        assertEquals(1500,atm.checkBalance(ba));
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(ba,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(ba,-500));

    }

    @Test
    public void transferTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm=new ATM();
        BankAccount ba1=new BankAccount("a@mail.com",1000);
        BankAccount ba2=new BankAccount("a@mail.com",1000);
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
            assertEquals(0, Integer.parseInt(
                    atm.transactionHistory(bankAccount).substring(headerLength, headerLength+1)
            ));
            // EQ: with transaction history
            for (int i = 1; i < 101; i++) bankAccount.deposit(i);
            assertEquals(100, Integer.parseInt(
                    atm.transactionHistory(bankAccount).substring(headerLength, headerLength+1)
            ));
        } catch(Exception e) {
            fail(e.getLocalizedMessage());
        }
    }
}
