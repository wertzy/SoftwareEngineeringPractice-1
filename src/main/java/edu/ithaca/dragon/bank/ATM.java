package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class ATM{


    boolean confirmCredentials(String acctId, String password){
        return false;
    }

    double checkBalance(BankAccount account){
        return account.getBalance();
    }

    void withdraw(BankAccount account, double amount) throws InsufficientFundsException, FrozenAccountException {
        account.withdraw(amount);
    }

    void deposit(BankAccount account, double amount) throws FrozenAccountException {
        account.deposit(amount);
    }

    void transfer(BankAccount baWith, BankAccount baDepo, double amount) throws InsufficientFundsException, FrozenAccountException {
        try {
            baWith.withdraw(amount);
            baDepo.deposit(amount);
        }
        finally{
        }
    }
    boolean isTransferAmountValid(BankAccount baWith, double amount){
        if(!baWith.isAmountValid(amount)){
            return false;
        }
        if(baWith.getBalance()-amount<0){
            return false;
        }
        return true;
    }

    String transactionHistory(BankAccount bankAccount) {
        ArrayList<Double> depositHistory = bankAccount.getDepositHistory();
        ArrayList<Double> withdrawHistory = bankAccount.getWithdrawHistory();
        int totalTransactions = depositHistory.size()+withdrawHistory.size();
        String history = "total transactions:"+totalTransactions+"\n";

        history += "--Deposit History--\n";
        for (Double n : depositHistory) history += n.toString()+"\n";

        history += "--Withdraw History--\n";
        for (Double n : withdrawHistory) history += n.toString()+"\n";

        return history;
    }

}
