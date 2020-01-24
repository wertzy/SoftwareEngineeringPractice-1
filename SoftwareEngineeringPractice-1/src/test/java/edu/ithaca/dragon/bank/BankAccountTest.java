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
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
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

}