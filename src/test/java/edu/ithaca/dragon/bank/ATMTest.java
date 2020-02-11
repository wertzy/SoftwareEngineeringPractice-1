package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    @Test
    public void credentialsTest() throws InsufficientFundsException, FrozenAccountException {
        ATM atm = new ATM();
        BankAccount bankAccount = new BankAccount("a@b.com", "a", 1.00);
        //Passing
        assertTrue(atm.confirmCredentials("a@b.com", "a", bankAccount));
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
}
