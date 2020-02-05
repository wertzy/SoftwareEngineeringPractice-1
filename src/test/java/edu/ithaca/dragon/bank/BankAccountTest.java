package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void fakeWithdrawTest() throws InsufficientFundsException  {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        //Standard case withdrawal test, should withdraw succesfully
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        //withdrawing an amount that will have the bank account be a negative amount, should throw error
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(300));
        //withdrawing a negative amount, should throw error
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-200));
        //withdrawing an amount thats more than 3 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.001));


        //withdrawing with an empty bank account, this is a border case
        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(100));



    }

    @Test
    void isEmailValidTest(){
        //True Tests
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        //EC: Standard valid emails. This is not a border case.
        assertTrue(BankAccount.isEmailValid( "a-a@b.com"));
        assertTrue(BankAccount.isEmailValid( "a_a@b.com"));
        //EC: Both the above test the use of symbols in valid positions. One tests -, and the other tests _. This is not a border case.
        assertTrue(BankAccount.isEmailValid( "a@b.cc"));
        assertTrue(BankAccount.isEmailValid( "a@b.org"));

        //False Tests
        assertFalse( BankAccount.isEmailValid(""));
        //EC: Tests empty strings. This is a border case.
        assertFalse(BankAccount.isEmailValid( "a-@b.com"));
        //EC: Tests addresses that are invalid due to a - next to the @. This is not a border case.
        assertFalse( BankAccount.isEmailValid("a..a@b.com"));
    }

    @Test
    void constructorTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for negative bank account
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        //check for more than 2 decimal places
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 0.001));
    }

    @Test
    void isValidAmountTest() {
        //true tests
        //tests an integer
        assertTrue(BankAccount.isAmountValid(100));
        //tests amount in the tenth place
        assertTrue(BankAccount.isAmountValid(10.1));
        //tests amount in the hundredths place
        assertTrue(BankAccount.isAmountValid(10.01));

        //false tests
        //tests a negative amount
        assertFalse(BankAccount.isAmountValid(-100));
        //tests an amount that has too many places. amount should not be more than two decimal places
        assertFalse(BankAccount.isAmountValid(0.001));
    }

    @Test
    void depositTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());

        //depositing a negative amount, should throw error
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-200));
        //depositing an amount thats more than 3 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.001));

        //depositing an amount in the tenths place
        bankAccount.deposit(.1);
        assertEquals(300.1, bankAccount.getBalance());

        //depositing an amount in the hundredths place
        bankAccount.deposit(.01);
        assertEquals(300.11, bankAccount.getBalance());
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);
        BankAccount otherBankAccount = new BankAccount("a@b.com", 500);
        //testing transfer between two bank accounts
        bankAccount.transfer(250, otherBankAccount);
        assertEquals(250, bankAccount.getBalance());
        assertEquals(750, otherBankAccount.getBalance());

        //testing transfer between two bank accounts with an amount in two decimal places
        bankAccount.transfer(10.01, otherBankAccount);
        assertEquals(239.99, bankAccount.getBalance());
        assertEquals(760.01, otherBankAccount.getBalance());

        //transferring an amount thats more than 3 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(-100, otherBankAccount));
        //transferring an amount thats more than 3 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(0.001, otherBankAccount));
    }
}