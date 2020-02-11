package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdministratorTest {
    @Test
    void freezeTest() throws InsufficientFundsException, FrozenAccountException {
        administrator newAccount = new administrator("a");
        BankAccount bankAccount = new BankAccount("a@b.com", 1.00);
        //Basic freeze test for all methods.
        newAccount.freezeAccount(bankAccount);
        assertThrows(FrozenAccountException.class, ()-> bankAccount.withdraw(1));
        assertThrows(FrozenAccountException.class, ()->bankAccount.deposit(1));
        BankAccount bankAccount2 = new BankAccount("a@b.com", 2.00);
        assertThrows(FrozenAccountException.class, ()->bankAccount.transfer(1, bankAccount2));
        assertThrows(FrozenAccountException.class, ()->bankAccount2.transfer(1, bankAccount));
        //Check that methods work after unfreezing.
        newAccount.unfreezeAcct(bankAccount);
        bankAccount.withdraw(1);
        assertEquals(0, bankAccount.getBalance());
        bankAccount.deposit(2);
        assertEquals(2, bankAccount.getBalance());
        bankAccount.transfer(2, bankAccount2);
        assertEquals(0, bankAccount.getBalance());
        bankAccount2.transfer(2, bankAccount);
        assertEquals(2, bankAccount.getBalance());
    }

    @Test
    void totalTest() {
        //Standard test
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        double checker = newAccount.calcTotalAssets(centralBank);
        assertEquals(1.00, checker);
        centralBank.createAccount("b@a.com", 2.00);
        checker = newAccount.calcTotalAssets(centralBank);
        assertEquals(3.00, checker);
        //0 Test
        CentralBank centralBank2 = new CentralBank();
        centralBank2.createAccount("a@b.com", 1.00);
        centralBank2.createAccount("b@a.com", 0.00);
        checker = newAccount.calcTotalAssets(centralBank2);
        assertEquals(1.00, checker);
    }

    @Test
    void suspActTest() throws InsufficientFundsException, FrozenAccountException {
        //Basic test
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 2.00);
        for (int i = 0; i < 150; i++){
            centralBank.getBankAccounts().get(0).withdraw(0.01);
        }
        Collection<String> testCollect = new ArrayList<String>();
        testCollect = newAccount.findAcctIdsWithSuspiciousActivity(centralBank);
        assertEquals(false, testCollect.isEmpty());
    }

    @Test
    void integrationTest() throws FrozenAccountException {
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        centralBank.getBankAccounts().get(0).deposit(1.00);
        assertEquals(2.00, newAccount.calcTotalAssets(centralBank));
        newAccount.freezeAccount(centralBank.getBankAccounts().get(0));
        assertThrows(FrozenAccountException.class, ()->centralBank.getBankAccounts().get(0).withdraw(1));
        newAccount.unfreezeAcct(centralBank.getBankAccounts().get(0));
        centralBank.getBankAccounts().get(0).deposit(1.00);
        assertEquals(3.00, centralBank.getBankAccounts().get(0).getBalance());
    }

    @Test
    void systemTest() throws FrozenAccountException {
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        for (int i = 0; i < 100; i++){
            if(!centralBank.getBankAccounts().get(0).isItFrozen()){
                newAccount.freezeAccount(centralBank.getBankAccounts().get(0));
            }else{
                newAccount.unfreezeAcct(centralBank.getBankAccounts().get(0));
            }
            if(!centralBank.getBankAccounts().get(0).isItFrozen()) {
                centralBank.getBankAccounts().get(0).deposit(1.00);
            }else{
                assertThrows(FrozenAccountException.class, ()-> centralBank.getBankAccounts().get(0).withdraw(1));
            }
        }
        assertEquals(51.00, newAccount.calcTotalAssets(centralBank));
        Collection<String> testCollect = newAccount.findAcctIdsWithSuspiciousActivity(centralBank);
        assertEquals(true, testCollect.isEmpty());
    }
}
