package edu.ithaca.dragon.bank;

public class ATM{


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

    void transfer(BankAccount baWith, BankAccount baDepo, double amount) throws InsufficientFundsException{
        if(!isTransferAmountValid(baWith,amount)){
            throw new InsufficientFundsException("amount value is either invalid or cause the bank withdrawing account to have a negative value ");
        }
        baWith.withdraw(amount);
        baDepo.deposit(amount);
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

    String transactionHistory(String acctId){
        return "";
    }

}
