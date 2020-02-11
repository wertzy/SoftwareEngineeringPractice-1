package edu.ithaca.dragon.bank;

//API to be used by ATMs
public interface BasicAPI {

    boolean confirmCredentials(String acctId, String password, BankAccount bankAccount) throws FrozenAccountException;

    double checkBalance(String acctId) throws FrozenAccountException;

    void withdraw(String acctId, double amount) throws InsufficientFundsException, FrozenAccountException;

    void deposit(String acctId, double amount) throws FrozenAccountException;

    void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, FrozenAccountException;

    String transactionHistory(String acctId);

}
