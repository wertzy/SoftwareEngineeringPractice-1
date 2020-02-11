package edu.ithaca.dragon.bank;

public class ATM{

    public BankAccount createAccount(String acctId, double startingBalance) throws InsufficientFundsException {
        return new BankAccount(acctId, startingBalance);
    }

    boolean confirmCredentials(String acctId, String password){
        return false;
    }

    double checkBalance(BankAccount account){
        return account.getBalance();
    }

    void withdraw(BankAccount account, double amount) throws InsufficientFundsException{
        account.withdraw(amount);
    }

    void deposit(BankAccount account, double amount){
        account.deposit(amount);
    }

    void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException{

    }

    String transactionHistory(String acctId){
        return "";
    }

}
