package edu.ithaca.dragon.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class administrator{

    public String password;

    public administrator(String password){
        if (passwordChecker(password)){
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password: " + password + " is invalid, cannot create account");
        }
    }

    public String getPassword(){ return password; }

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

    public Collection<String> findAcctIdsWithSuspiciousActivity(CentralBank centralBank) throws FrozenAccountException {
        List<String> returnable = new ArrayList<String>();
        for (int i = 0; i < centralBank.getbankAccountsLength(); i++){
            int isItTooHigh = centralBank.getBankAccounts().get(i).getWithdrawCount();
            if (isItTooHigh > 100){
                returnable.add(centralBank.getBankAccounts().get(i).getEmail());
            }
        }
        return returnable;
    }

    static boolean passwordChecker(String password){
        if (password.length() < 6){
            return false;
        }
        if (password.indexOf('@') == -1 && password.indexOf('#') == -1 && password.indexOf('$') == -1 && password.indexOf('&') == -1 && password.indexOf('!') == -1 && password.indexOf('+') == -1 && password.indexOf('-') == -1 && password.indexOf('~') == -1){
            return false;
        }
        if (password.indexOf('0') == -1 && password.indexOf('1') == -1 && password.indexOf('2') == -1 && password.indexOf('3') == -1 && password.indexOf('4') == -1 && password.indexOf('5') == -1 && password.indexOf('6') == -1 && password.indexOf('7') == -1 && password.indexOf('8') == -1 && password.indexOf('9') == -1){
            return false;
        }
        return true;
    }

}
