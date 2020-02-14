package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class BankTeller {
    public BankAccount createAccount
    (String acctId, String password, String type, double startingBalance, ArrayList<BankAccount> bankAccounts)
            throws InsufficientFundsException, IllegalArgumentException, FrozenAccountException {

        for (BankAccount ba : bankAccounts) {
            if (ba.getEmail().equals(acctId))
                throw new IllegalArgumentException("Only one checking and savings account per customer is allowed");
        }
        return new BankAccount(acctId, password, type, startingBalance);
   }

   public void closeAccount(BankAccount bankAccount) {
        bankAccount.setClosed(true);
   }

   public void withdraw(BankAccount bankAccount, double amount)
       throws IllegalArgumentException, InsufficientFundsException, FrozenAccountException {
        bankAccount.withdraw(amount);
   }

    public void deposit(BankAccount bankAccount, double amount) throws FrozenAccountException {
        bankAccount.deposit(amount);
    }
}
