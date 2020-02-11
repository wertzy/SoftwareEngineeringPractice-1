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
        for (int i = 0; i < centralBank.getbankAccountsLength(); i++){
            returnValue += centralBank.getBankAccounts().get(i).getBalance();
        }
        return returnValue;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity(CentralBank centralBank) {
        Collection<String> returnable = new Collection<String>;
        for (int i = 0; i < centralBank.getbankAccountsLength(); i++){
            int isItTooHigh = centralBank.getBankAccounts().get(i).getWithdrawCount();
            if (isItTooHigh > 100){
                returnable.add(centralBank.getBankAccounts().get(i).getEmail());
            }
        }
        return returnable;
    }

}
