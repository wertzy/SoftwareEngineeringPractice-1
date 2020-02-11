package edu.ithaca.dragon.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CentralBank implements AdvancedAPI, AdminAPI, BasicAPI {

    private ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>(100);
    private ArrayList<BankAccount> closedAccounts = new ArrayList<BankAccount>(100);
    private BankTeller bankTeller = new BankTeller();
    private ATM atm=new ATM();

    public int getbankAccountsLength() { return bankAccounts.size(); }

    public int getClosedAccountsLength() { return closedAccounts.size(); }

    public ArrayList<BankAccount>  getBankAccounts() { return bankAccounts; }

    public ArrayList<BankAccount>  getClosedAccounts() { return closedAccounts; }

    //----------------- BasicAPI methods -------------------------//


    public boolean confirmCredentials(String acctId, String password) {
        return false;
    }

    public double checkBalance(String acctId) { /*
        for (BankAccount ba: bankAccounts){
            if(ba.getEmail().equals(acctId)){
                return ba.getBalance();
            }
        }
        return -1;
        */
        return 0;
    }

    public static boolean isAmountValid(double amount) {
        if(amount<=0) {
            return false;
        }
        if(BigDecimal.valueOf(amount).scale()>2){
            return false;
        }
        else {
            return true;
        }
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException {
        if (!bankAccounts.contains(acctId)) {
            throw new InsufficientFundsException("account ID not found");
        }
        if (!isAmountValid(amount)) {
            throw new InsufficientFundsException("amount is invalid");
        }
            for (BankAccount ba : bankAccounts) {
                if (ba.getEmail().equals(acctId)){
                    atm.withdraw(ba,amount);
                }
            }
    }

    public void deposit(String acctId, double amount)  {
        if (!bankAccounts.contains(acctId)) {
            System.out.println("The account you entered does not exist");
            return;
        }
        for (BankAccount ba : bankAccounts) {
            if (ba.getEmail().equals(acctId)){
                atm.deposit(ba,amount);
            }
        }

    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException {

        if(!isAmountValid(amount)){
            throw new InsufficientFundsException("amount is invalid");
        }
        if (!bankAccounts.contains(acctIdToWithdrawFrom)) {
            System.out.println("The account you entered does not exist");
            return;
        }
        if (!bankAccounts.contains(acctIdToDepositTo)) {
            System.out.println("The account you entered does not exist");
            return;
        }
        for (BankAccount baWith : bankAccounts) {
            if (baWith.getEmail().equals(acctIdToWithdrawFrom)){
                atm.deposit(baWith,amount);
            }
        for (BankAccount baDepo : bankAccounts) {
            if (baDepo.getEmail().equals(acctIdToDepositTo)){
                atm.deposit(baDepo,amount);
            }
        }



    }

    public String transactionHistory(String acctId) {
        return null;
    }


    //----------------- AdvancedAPI methods -------------------------//

    public void createAccount(String acctId, double startingBalance) {
        try {
            for (BankAccount ba : bankAccounts) {
                if (ba.getEmail().equals(acctId)) return;
            }
            BankAccount newBankAccount =  bankTeller.createAccount(acctId, startingBalance);
            bankAccounts.add(newBankAccount);
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void closeAccount(String acctId) {
        BankAccount bankAccount = null;
        for (BankAccount ba : bankAccounts) {
            if (ba.getEmail().equals(acctId)) {
                bankAccount = ba;
                break;
            }
        }
        if (bankAccount != null) {
            bankTeller.closeAccount(bankAccount);
            bankAccounts.remove(bankAccount);
            closedAccounts.add(bankAccount);
        }
    }


    //------------------ AdminAPI methods -------------------------//

    public double checkTotalAssets() {
        return 0;
    }

    @Override
    public double calcTotalAssets() {
        return 0;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        return null;
    }

    public void freezeAccount(String acctId) {

    }

    public void unfreezeAcct(String acctId) {

    }

}
