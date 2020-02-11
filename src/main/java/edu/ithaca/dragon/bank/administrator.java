package edu.ithaca.dragon.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        for (int i = 0; i < centralBank.getbankAccountsLength(); i++){
            returnValue = returnValue + centralBank.getBankAccounts().get(i).getBalance();
        }
        return returnValue;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity(CentralBank centralBank) {
        List<String> returnable = new ArrayList<String>();
        for (int i = 0; i < centralBank.getbankAccountsLength(); i++){
            int isItTooHigh = centralBank.getBankAccounts().get(i).getWithdrawCount();
            if (isItTooHigh > 100){
                returnable.add(centralBank.getBankAccounts().get(i).getEmail());
            }
        }
        return returnable;
    }

}
