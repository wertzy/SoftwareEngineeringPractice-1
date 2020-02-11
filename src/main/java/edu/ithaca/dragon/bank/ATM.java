package edu.ithaca.dragon.bank;

public class ATM{


    boolean confirmCredentials(String acctId, String password, BankAccount bankAccount) throws FrozenAccountException {
        if (!bankAccount.getEmail().equals(acctId)){
            return false;
        }
        if(!bankAccount.getPassword().equals(password)){
            return false;
        }
        return true;
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

    String transactionHistory(String acctId){
        return "";
    }

}
