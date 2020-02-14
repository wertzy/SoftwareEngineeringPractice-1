package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BankTellerTest {
    @Test
    public void createAccountTest() {
        BankTeller bankTeller = new BankTeller();
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>(10);
        try {
            for (int i = 1; i < 11; i++)
                bankAccounts.add( new BankAccount(i+"@mail.com", "a", 1) );
            //EQ: create a bank account
            BankAccount bankAccount = bankTeller.createAccount("a@@b.com", "a",100, bankAccounts);
            assertEquals("BankAccount", bankAccount.getClass().getSimpleName());
            //EQ: create a duplicate bank account
            assertThrows(
                IllegalArgumentException.class,
                () -> bankTeller.createAccount("1@mail.com", "a", 100, bankAccounts)
            );
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void closeAccountTest() {
        BankTeller bankTeller = new BankTeller();
        try {
            // EQ: close a bank account
            BankAccount bankAccount = new BankAccount("a@b.com", "a", 100);
            bankTeller.closeAccount(bankAccount);
            assertEquals(true, bankAccount.isClosed());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void withdrawTest() {
        BankTeller bankTeller = new BankTeller();
        try {
            BankAccount bankAccount = new BankAccount("a@b.com", "a", 100);
            // EQ: bank account is withdrawn from (calls bankAccount.withdraw)
            bankTeller.withdraw(bankAccount, 1);
            assertEquals(99, bankAccount.getBalance());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void depositTest() {
        BankTeller bankTeller = new BankTeller();
        try {
            BankAccount bankAccount = new BankAccount("a@b.com", "a", 100);
            // EQ: bank account is deposited to (calls bankAccount.deposit)
            bankTeller.deposit(bankAccount, 1);
            assertEquals(101, bankAccount.getBalance());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
}
