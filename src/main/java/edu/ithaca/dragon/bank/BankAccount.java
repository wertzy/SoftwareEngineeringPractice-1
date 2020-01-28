package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
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
    public void withdraw (double amount) throws InsufficientFundsException  {
        if (amount <= 0 ){
            throw new InsufficientFundsException("negative or zero amount of money");
        }
        if (amount <= balance) {
            balance -= amount;
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
}
