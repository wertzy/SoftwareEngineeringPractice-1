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
}
