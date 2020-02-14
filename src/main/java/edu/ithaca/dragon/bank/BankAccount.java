package edu.ithaca.dragon.bank;

import java.math.BigDecimal;

import java.util.ArrayList;

public class BankAccount {

    private String email;
    private String password;
    private String type;
    private double balance;
    public boolean isFrozen;
    private boolean closed = false;
    private int withdrawCount = 0;
    private ArrayList<Double> depositHistory = new ArrayList<Double>();
    private ArrayList<Double> withdrawHistory = new ArrayList<Double>();

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, String password, String type, double startingBalance) throws InsufficientFundsException {
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
        administrator tempAdmin = new administrator("abcdef1@");
        if (tempAdmin.passwordChecker(password)){
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password: " + password + " is invalid, cannot create account");
        }
        if(type=="savings"){
            this.type = type;
        }else {
            if (type=="checking"){
                this.type = type;
            }else{
                throw new IllegalArgumentException("Account must be a checking or savings account");
            }
        }
    }

    public double getBalance(){
        if(!isFrozen) {
            return balance;
        }
        else{
            return 0;
        }
    }

    public String getEmail() throws FrozenAccountException {
        if(!isFrozen) {
            return email;
        }
        else{
            throw new FrozenAccountException("account is frozen");
        }
    }

    public String getPassword() throws FrozenAccountException {
        if(!isFrozen) {
            return password;
        }
        else{
            throw new FrozenAccountException("account is frozen");
        }
    }

    public void setClosed(boolean bool) { closed = bool; }

    public boolean isClosed() { return closed; }

    public int getWithdrawCount() { return withdrawCount; }

    public ArrayList<Double> getDepositHistory() { return depositHistory; }

    public ArrayList<Double> getWithdrawHistory() { return withdrawHistory; }

    public String getType() { return type; }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws IllegalArgumentException, InsufficientFundsException, FrozenAccountException {
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

        if (isFrozen){
            throw new FrozenAccountException("account is frozen");
        }

        if (amount <= balance) {
            balance -= amount;
            withdrawHistory.add(amount);
            withdrawCount++;
        }
        else{
            throw new InsufficientFundsException("not enough money");
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
    /**
     * @post checks if amount is positive and not more than 2 decimal places
     */
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
    /**
     * @post deposits money into bank account balance
     */
    public void deposit(double amount) throws FrozenAccountException {
        if(isAmountValid(amount)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        if(isFrozen) {
            throw new FrozenAccountException("account is frozen");
        }
            balance += amount;
            depositHistory.add(amount);
    }
    /**
     * @post transfers money from one bank account to another
     */
    public void transfer(double amount,BankAccount otherBankAccount) throws FrozenAccountException {
        if(isAmountValid(amount)==false){
            throw new IllegalArgumentException("amount is invalid");
        }
        if(balance-amount<0){
            throw new IllegalArgumentException("amount will overdraft bank account balance");
        }
        if(isFrozen || otherBankAccount.isItFrozen()) {
            throw new FrozenAccountException("account is frozen");
        }
            balance -= amount;
            otherBankAccount.balance += amount;

    }

    public boolean isItFrozen(){
        return isFrozen;
    }

    public void dayPasses() {
        if (this.type=="savings"){
            this.balance = this.balance*1.01;
        }
        this.withdrawCount = 0;
    }
}