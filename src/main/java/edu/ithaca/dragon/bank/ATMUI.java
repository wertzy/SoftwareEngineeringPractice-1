package edu.ithaca.dragon.bank;
import java.util.Scanner;

public class ATMUI {

    public void displayOptions(BankAccount ba) throws FrozenAccountException, InsufficientFundsException {
        System.out.println("Welcome User");
        System.out.println("Your current balance is");
        System.out.print(ba.getBalance());
        System.out.println("Enter the following integer to do the corresponding action:");
        System.out.println("1:Withdraw funds");
        System.out.println("2:Deposit funds");
        System.out.println("3:Transfer funds");
        System.out.println("4:Log out");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Integer");
        int num = scan.nextInt();
        if (num==1){
            ba.withdraw(100);
        }
        else if (num==2){
            ba.deposit(100);
        }
        else if (num==3){
            BankAccount inputBA=ATMUI.inputBankAccount();
            ba.transfer(100,inputBA);
        }
        else if (num==4){

        }
        else {
            displayOptions(ba);
        }
    }

    private static BankAccount inputBankAccount() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a username:");
        String input = scan.nextLine();
        return null;
    }
}
