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
        assertThrows(IllegalArgumentException.class, () -> centralBank.createAccount("1@mail.com", 100));
    }
}
