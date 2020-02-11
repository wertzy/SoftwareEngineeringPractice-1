package edu.ithaca.dragon.bank;

import java.math.BigDecimal;
import java.util.Collection;

public class administrator{

    public String password;

    public administrator(String password){
        this.password = password;
    }

    public void freezeAccount(BankAccount bankAccount) {
        bankAccount.isFrozen=true;
    }

    public void unfreezeAcct(BankAccount bankAccount) {
        bankAccount.isFrozen=false;
    }

    public double calcTotalAssets(CentralBank centralBank) {
        double returnValue = 0;
        for (int i = 0; i < centralBank.bankAccounts.size(); i++){
            returnValue += centralBank.bankAccounts.get(i).getBalance();
        }
        return returnValue;
    }

    public String findAcctIdsWithSuspiciousActivity() {
        return "";
    }

    public boolean passwordChecker(String password){
        return true;
    }

}
