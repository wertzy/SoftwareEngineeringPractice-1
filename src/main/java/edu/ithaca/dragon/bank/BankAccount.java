package edu.ithaca.dragon.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) throws InsufficientFundsException {
        if(isAmountValid(startingBalance)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }


    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws IllegalArgumentException, InsufficientFundsException {
        if(isAmountValid(amount)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        if(balance ==0 ){
            throw new IllegalArgumentException("no money in bank account");
        }
        if (amount <= 0 ){
            throw new InsufficientFundsException("negative or zero amount of money");
        }
        if(balance-amount<0){
            throw new IllegalArgumentException("amount will overdraw the account");
        }

        if (amount <= balance) {
            balance -= amount;
        }
        else{
            throw new IllegalArgumentException("not enough money");
        }

    }


    public static boolean isEmailValid(String email) {
        if (email.indexOf('@') == -1) {
            return false;
        } else {
            if (email.charAt(email.indexOf('.') + 1) == '.') {
                return false;
            } else {
                if (email.indexOf('@') - 1 == email.indexOf('-')) {
                    return false;
                } else {
                    if (email.indexOf('@') + 1 == email.indexOf('-')) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
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

    public static double deposit(double amount){
        if(isAmountValid(amount)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        return amount;
    }
    public static double transfer(double amount){
        if(isAmountValid(amount)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        return amount;
    }
}
