package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AdministratorTest {
    @Test
    void freezeTest() throws InsufficientFundsException{
        administrator newAccount = new administrator("a");
        BankAccount bankAccount = new BankAccount("a@b.com", 1.00);
        //Basic freeze test for all methods.
        newAccount.freezeAccount(bankAccount);
        bankAccount.withdraw(1);
        assertEquals(1, bankAccount.getBalance());
        bankAccount.deposit(1);
        assertEquals(1, bankAccount.getBalance());
        BankAccount bankAccount2 = new BankAccount("a@b.com", 2.00);
        bankAccount.transfer(1, bankAccount2);
        assertEquals(1, bankAccount.getBalance());
        bankAccount2.transfer(1, bankAccount);
        assertEquals(1, bankAccount.getBalance());
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
    void totalTest() throws InsufficientFundsException {
        //Standard test
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        centralBank.createAccount("a@b.com", 2.00);
        double checker = newAccount.calcTotalAssets(centralBank);
        assertEquals(3.00, checker);
        //0 Test
        CentralBank centralBank2 = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        centralBank.createAccount("a@b.com", 0.00);
        double checker2 = newAccount.calcTotalAssets(centralBank2);
        assertEquals(1.00, checker2);
    }

    @Test
    void suspActTest(){
        //Basic test
        administrator newAccount = new administrator("a");
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.01);
        for (int i = 0; i < 100; i++){
            centralBank.getBankAccounts().get(i).withdraw(0.01);
        }
        Collection<String> testCollect = new Collection<String>[5];
        testCollect = newAccount.findAcctIdsWithSuspiciousActivity(centralBank);
        assertEquals("false", testCollect.isEmpty());
    }

    @Test
    void integrationTest(){

    }

    @Test
    void systemTest(){
        administrator newAccount = new administrator;
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("a@b.com", 1.00);
        for (int i = 0; i < 100; i++){
            if(!CentralBank.getBankAccounts().get(0).isItFrozen){
                newAccount.freezeAccount(CentralBank.getBankAccounts().get(0));
            }else{
                newAccount.unfreezeAcct(CentralBank.getBankAccounts().get(0));
            }
            CentralBank.getBankAccounts().get(0).deposit(1.00);
        }
        assertEquals(51.00, getBankAccounts().get(0).getBalance);
        Collection<String> testCollect = new Collection<String>[5];
        testCollect = newAccount.findAcctIdsWithSuspiciousActivity(centralBank);
        assertEquals("true", testCollect.isEmpty());
    }
}
