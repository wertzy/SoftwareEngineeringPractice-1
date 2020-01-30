package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        //Standard case withdrawal test, should withdraw succesfully
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        //withdrawing an amount that will have the bank account be a negative amount, should throw error
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        //withdrawing a negative amount, should throw error
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-200));
        assertEquals(100, bankAccount.getBalance());


        //withdrawing with an empty bank account, this is a border case, should throw an error
        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertThrows(InsufficientFundsException.class, () -> bankAccount3.withdraw(100));



    }

    @Test
    void isEmailValidTest(){
        //True Tests
        //PT: Commented out tests were commented out when I got the code.
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        //EC: Standard valid emails. This is not a border case.
        assertTrue(BankAccount.isEmailValid( "a-a@b.com"));
        assertTrue(BankAccount.isEmailValid( "a_a@b.com"));
        //EC: Both the above test the use of symbols in valid positions. One tests -, and the other tests _. This is not a border case.
        //assertTrue(BankAccount.isEmailValid( "a@b.cc"));
        //assertTrue(BankAccount.isEmailValid( "a@b.org"));
        //False Tests
        assertFalse( BankAccount.isEmailValid(""));
        //EC: Tests empty strings. This is not a border case.
        assertFalse(BankAccount.isEmailValid( "a-@b.com"));
        //EC: Tests addresses that are invalid due to a - next to the @. This is not a border case.
        assertFalse( BankAccount.isEmailValid("a..a@b.com"));
        //EC: Tests addresses that are invalid due to repeated symbols. This is not a border case.
        //assertFalse(BankAccount.isEmailValid( "a@b..com"));
        //assertFalse(BankAccount.isEmailValid( "a@b.c"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
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
}