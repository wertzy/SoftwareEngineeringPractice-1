package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ATMTest {

    @Test
    public void withdrawTest() throws InsufficientFundsException {
        ATM atm=new ATM();
        BankAccount ba=new BankAccount("a@mail.com",1000);
        atm.withdraw(ba,500);
        assertEquals(500,atm.checkBalance(ba));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,1000));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,500.001));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(ba,-500));

    }

    @Test
    public void depositTest(){
        CentralBank centralBank = new CentralBank();

    }
}
