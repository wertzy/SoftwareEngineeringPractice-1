package edu.ithaca.dragon.bank;

public class ATMUI {

    public void displayOptions(BankAccount ba){
        System.out.println("Welcome User");
        System.out.println("Your current balance is");
        System.out.println("Enter the following integer to do the corresponding action:");
        System.out.println("1:Withdraw funds");
        System.out.println("2:Deposit funds");
        System.out.println("3:Transfer funds");
    }
}
