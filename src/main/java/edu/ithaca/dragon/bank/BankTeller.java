package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class BankTeller {
    public BankAccount createAccount
    (String acctId, String password, double startingBalance, ArrayList<BankAccount> bankAccounts)
            throws InsufficientFundsException, IllegalArgumentException, FrozenAccountException {

        for (BankAccount ba : bankAccounts) {
            if (ba.getEmail().equals(acctId)) throw new IllegalArgumentException("Duplicate ID found.");
        }
        return new BankAccount(acctId, password, startingBalance);
   }

   public void closeAccount(BankAccount bankAccount) {
        bankAccount.setClosed(true);
   }

   public void withdraw(BankAccount bankAccount, double amount)
       throws IllegalArgumentException, InsufficientFundsException, FrozenAccountException {
        bankAccount.withdraw(amount);
   }

    public void deposit(BankAccount bankAccount, double amount) throws FrozenAccountException { }
}
