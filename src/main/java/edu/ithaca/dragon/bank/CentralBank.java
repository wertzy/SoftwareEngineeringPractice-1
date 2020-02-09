package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.Collection;

public class CentralBank implements AdvancedAPI, AdminAPI {

    private ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>(100);
    private ArrayList<BankAccount> closedAccounts = new ArrayList<BankAccount>(100);
    private BankTeller bankTeller = new BankTeller();

    public int getbankAccountsLength() { return bankAccounts.size(); }

    public int getClosedAccountsLength() { return closedAccounts.size(); }

    //----------------- BasicAPI methods -------------------------//

    public boolean confirmCredentials(String acctId, String password) {
        return false;
    }

    public double checkBalance(String acctId) {
        return 0;
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException {

    }

    public void deposit(String acctId, double amount) {

    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException {

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
