package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class BankTeller {
    public BankAccount createAccount
    (String acctId, double startingBalance, ArrayList<BankAccount> bankAccounts)
    throws InsufficientFundsException, IllegalArgumentException {
        return new BankAccount(acctId, startingBalance);
   }

   public void closeAccount(BankAccount bankAccount) {
        bankAccount.setClosed(true);
   }
}
