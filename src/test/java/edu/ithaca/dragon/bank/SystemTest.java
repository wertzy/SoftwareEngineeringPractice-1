package edu.ithaca.dragon.bank;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SystemTest {
    @Test
    public void systemTest() throws FrozenAccountException, InsufficientFundsException {
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@mail.com", "abc123!", "savings", 50000);
        centralBank.createAccount("b@mail.com", "abc123!",  "savings", 50000);
        //check bankaccount length and there is no history in the bank accounts
        assertEquals(2, centralBank.getbankAccountsLength());
        assertEquals(true, centralBank.transactionHistory("c@mail.com").equals("No such account"));

        //history test
        String history = centralBank.transactionHistory("a@mail.com");
        assertEquals(true, !history.equals("No such account") && history.length() > 0);

        //deposit, withdraw, transfer
        centralBank.deposit("a@mail.com",1000);
        assertEquals(51000,centralBank.checkBalance("a@mail.com"));
        centralBank.withdraw("b@mail.com",30000);
        assertEquals(20000,centralBank.checkBalance("b@mail.com"));
        centralBank.transfer("a@mail.com","b@mail.com",6000);
        assertEquals(45000,centralBank.checkBalance("a@mail.com"));
        assertEquals(26000,centralBank.checkBalance("b@mail.com"));
        centralBank.transfer("b@mail.com","a@mail.com",500);
        assertEquals(45500,centralBank.checkBalance("a@mail.com"));
        assertEquals(25500,centralBank.checkBalance("b@mail.com"));

        //throwing errors from deposit transfer and withdraw
        assertThrows(IllegalArgumentException.class, () -> centralBank.deposit("a@mail.com",500.001));
        assertThrows(IllegalArgumentException.class, () -> centralBank.deposit("b@mail.com",-500));

        assertThrows(IllegalArgumentException.class, () -> centralBank.transfer("a@mail.com","b@mail.com",500.001));
        assertThrows(IllegalArgumentException.class, () -> centralBank.transfer("a@mail.com","b@mail.com",-500));
        assertThrows(IllegalArgumentException.class, () -> centralBank.transfer("a@mail.com","b@mail.com",10000000));


        //closing accounts
        centralBank.closeAccount("a@mail.com");
        centralBank.closeAccount("b@mail.com");
        assertEquals(0, centralBank.getbankAccountsLength());

    }
}
