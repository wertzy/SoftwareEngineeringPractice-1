package edu.ithaca.dragon.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CentralBank implements AdvancedAPI, AdminAPI, BasicAPI {

    private ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>(100);
    private ArrayList<BankAccount> closedAccounts = new ArrayList<BankAccount>(100);
    private BankTeller bankTeller = new BankTeller();
    private ATM atm=new ATM();

    public int getbankAccountsLength() { return bankAccounts.size(); }

    public int getClosedAccountsLength() { return closedAccounts.size(); }

    public ArrayList<BankAccount>  getBankAccounts() { return bankAccounts; }

    public ArrayList<BankAccount>  getClosedAccounts() { return closedAccounts; }

    public BankAccount findAccount(String acctId) {
        BankAccount bankAccount = null;
        for (BankAccount ba : bankAccounts) {
            try {
                if (ba.getEmail().equals(acctId)) {
                    bankAccount = ba;
                    break;
                }
            } catch(Exception e) {
                System.out.println(e.getLocalizedMessage());
                return null;
            }
        }
        return bankAccount;
    }

    public BankAccount findAccount(String acctId, String type) {
        return null;
    }

    //----------------- BasicAPI methods -------------------------//


    public boolean confirmCredentials(String acctId, String password, BankAccount bankAccount) throws FrozenAccountException {
        if( bankAccount.getEmail()==acctId && bankAccount.getPassword()==password){
            return true;
        } else{
            return false;
        }
    }

    public double checkBalance(String acctId) throws FrozenAccountException {
        for (BankAccount ba: bankAccounts){
            if(ba.getEmail().equals(acctId)){
                return atm.checkBalance(ba);
            }
        }
        return -1;
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

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, FrozenAccountException {
        if (!isAmountValid(amount)) {
            throw new InsufficientFundsException("amount is invalid");
        }
        BankAccount bankAccount = findAccount(acctId);
        if (bankAccount != null) atm.withdraw(bankAccount, amount);
    }

    public void deposit(String acctId, double amount) throws FrozenAccountException {
        BankAccount bankAccount = findAccount(acctId);
        if (bankAccount != null) atm.deposit(bankAccount, amount);
    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, FrozenAccountException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("amount is invalid");
        }
        BankAccount baWith = findAccount(acctIdToWithdrawFrom);
        BankAccount baDepo = findAccount(acctIdToDepositTo);

        if (baWith != null && baDepo != null) {
            atm.transfer(baWith,baDepo,amount);
        }
    }

    public String transactionHistory(String acctId) {
        BankAccount bankAccount = findAccount(acctId);
        String history = "";

        if (bankAccount != null) history = atm.transactionHistory(bankAccount);
        else history = "No such account";
        return history;
    }


    //----------------- AdvancedAPI methods -------------------------//

    public void createAccount(String acctId, String password, String type, double startingBalance) {
        try {
            BankAccount newBankAccount =  bankTeller.createAccount(acctId, password, type, startingBalance, bankAccounts);
            bankAccounts.add(newBankAccount);
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void closeAccount(String acctId) throws FrozenAccountException {
        BankAccount bankAccount = findAccount(acctId);
        if (bankAccount != null) {
            bankTeller.closeAccount(bankAccount);
            bankAccounts.remove(bankAccount);
            closedAccounts.add(bankAccount);
        }
    }


    //------------------ AdminAPI methods -------------------------//

    public double checkTotalAssets() {
        return 0.0;
    }

    @Override
    public double calcTotalAssets() {
        double returnValue = 0;
        for (int i = 0; i < getbankAccountsLength(); i++){
            returnValue = returnValue + getBankAccounts().get(i).getBalance();
        }
        return returnValue;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() throws FrozenAccountException {
        List<String> returnable = new ArrayList<String>();
        for (int i = 0; i < getbankAccountsLength(); i++){
            int isItTooHigh = getBankAccounts().get(i).getWithdrawCount();
            if (isItTooHigh > 100){
                returnable.add(getBankAccounts().get(i).getEmail());
            }
        }
        return returnable;
    }

    public void freezeAccount(String acctId) {
        administrator tempAdmin = new administrator("abcdef1@");
        tempAdmin.freezeAccount(findAccount(acctId));
    }

    public void unfreezeAcct(String acctId) {
        administrator tempAdmin = new administrator("abcdef1@");
        tempAdmin.unfreezeAcct(findAccount(acctId));
    }

}
