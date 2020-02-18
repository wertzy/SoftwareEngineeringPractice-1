package edu.ithaca.dragon.bank;
import java.util.Scanner;

public class ATMUI {

    public ATMUI(BasicAPI testBaseAPI) {

    }

    private void displayOptions(BankAccount ba) throws FrozenAccountException, InsufficientFundsException {
        System.out.println("---------------------------------------");
        System.out.println("Welcome User");
        System.out.println("Your current balance is");
        System.out.print(ba.getBalance());
        System.out.println("\n\nEnter the following integer to do the corresponding action:");
        System.out.println("1:Withdraw funds");
        System.out.println("2:Deposit funds");
        System.out.println("3:Transfer funds");
        System.out.println("4:Log out");
        System.out.println("---------------------------------------\n");
    }

    public void run(BankAccount ba) {
        int num = 0;
        while (num != 4) {
            try {
                displayOptions(ba);

                Scanner scan = new Scanner(System.in);
                System.out.println("Enter option (1-4): ");
                num = scan.nextInt();
                switch(num) {
                    case 1:
                        ba.withdraw(100);
                        break;
                    case 2:
                        ba.deposit(100);
                        break;
                    case 3:
                        BankAccount inputBA=ATMUI.inputBankAccount();
                        ba.transfer(100,inputBA);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Please provide a valid option");
                        break;
                }
            } catch(Exception e) {

            }
        }
    }

    private static BankAccount inputBankAccount() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a username:");
        String input = scan.nextLine();
        return null;
    }

    public void login(CentralBank centralBank) throws FrozenAccountException {
        Scanner scan = new Scanner(System.in);
        BankAccount accountInQuestion = null;
        boolean loggedOut = true;
        while (loggedOut) {
            boolean checkingName = true;
            boolean enteringPassword = true;
            while (checkingName) {
                System.out.println("Enter a username:");
                String input = scan.nextLine();
                System.out.println(input+"... ");
                accountInQuestion = centralBank.findAccount(input);
                if (accountInQuestion == null) {
                    System.out.println("That username is not in use. ");
                } else {
                    checkingName = false;
                }

            }
            if(accountInQuestion.isItFrozen()){
                System.out.println("It looks like that account has been frozen by an administrator. ");
                System.out.println("If you would like to enquire further, contact customer service at 1-888-555-1212.");
                checkingName = true;
            }
            while (enteringPassword) {
                System.out.println("Enter a password or type 'cancel' to return to a previous state: ");
                String input2 = scan.nextLine();
                if(input2 == "cancel"){
                    enteringPassword = false;
                    checkingName = true;
                }
                if (input2 == accountInQuestion.getPassword()){
                    loggedOut = false;
                    run(accountInQuestion);
                }
            }
        }
    }
}
