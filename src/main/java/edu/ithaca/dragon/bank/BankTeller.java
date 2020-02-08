package edu.ithaca.dragon.bank;

public class BankTeller {
    public BankAccount createAccount(String acctId, double startingBalance) throws InsufficientFundsException {
        return new BankAccount(acctId, startingBalance);
   }
}
