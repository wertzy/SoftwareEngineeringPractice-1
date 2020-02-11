package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTellerTest {
    @Test
    public void createAccountTest() {
        BankTeller bankTeller = new BankTeller();
        try {
            //EQ: create a bank account
            BankAccount bankAccount = bankTeller.createAccount("a@@b.com", 100);
            assertEquals("BankAccount", bankAccount.getClass().getSimpleName());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void closeAccountTest() {
        BankTeller bankTeller = new BankTeller();
        try {
            // EQ: close a bank account
            BankAccount bankAccount = new BankAccount("a@b.com", 100);
            bankTeller.closeAccount(bankAccount);
            assertEquals(true, bankAccount.isClosed());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
}
