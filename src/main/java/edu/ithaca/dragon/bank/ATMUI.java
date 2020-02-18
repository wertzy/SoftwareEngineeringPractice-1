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



    private int displayErrorAndPrompt(String error, int errorCode) {
        System.out.println("---------------------------------------\n");
        System.out.println(error);
        System.out.println("---------------------------------------\n");

        Scanner scan = new Scanner(System.in);
        String action = "";

        if (errorCode == 1) action = "withdraw";
        else if (errorCode == 2) action = "deposit";
        else if (errorCode == 3) action = "transfer";
        if (action.length() > 0) {
            System.out.println(action+" again? [y/n]");
            String choice = scan.nextLine().toLowerCase();

            if (choice.equals("y")) return errorCode;
            else return 0;
        }
        return 0;
    }

    public void run(CentralBank centralBank) throws FrozenAccountException {
        int num = 0;
        BankAccount ba = login(centralBank);
        while (num != 4) {
            try {
                if (num == 0) {
                    displayOptions(ba);

                    Scanner scan = new Scanner(System.in);
                    System.out.println("Enter option (1-4): ");
                    num = scan.nextInt();
                }
                switch(num) {
                    case 1:
                        ba.withdraw( promptAmount("withdraw") );
                        break;
                    case 2:
                        ba.deposit( promptAmount("deposit") );
                        break;
                    case 3:
                            BankAccount inputBA = ATMUI.inputBankAccount(centralBank);
                            if (inputBA==null){
                                System.out.println("Error: Bank Account not found");
                                break;
                    }
                            ba.transfer(promptAmount("transfer") ,inputBA);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Please provide a valid option");
                        break;
                }
                if (num != 4) num = 0;
            } catch(Exception e) {
                num = displayErrorAndPrompt(e.getLocalizedMessage(), ba.getErrorCode());
                ba.resetErrorCode();
            }
        }
        run(centralBank);
    }

    private static BankAccount inputBankAccount(CentralBank cb) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter a username:");
            String input = scan.nextLine();
            BankAccount transferBA = cb.findAccount(input);
            return transferBA;
        }
        catch (Exception e){
            System.out.println("Bank Account is not found");
            return null;
        }
    }

    private double promptAmount(String action) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter an amount to "+action+": ");
        return scan.nextDouble();
    }


    public BankAccount login(CentralBank centralBank) throws FrozenAccountException {
        Scanner scan = new Scanner(System.in);
        BankAccount accountInQuestion = null;
        boolean loggedOut = true;
        while (loggedOut) {
            boolean checkingName = true;
            boolean enteringPassword = true;
            while (checkingName) {
                System.out.println("Enter a username:");
                String input = scan.nextLine();
                System.out.println(input + "... ");
                accountInQuestion = centralBank.findAccount(input);
                if (accountInQuestion == null) {
                    System.out.println("That username is not in use. ");
                } else {
                    checkingName = false;
                }



            }
            while (enteringPassword==true) {
                System.out.println("Enter a password or type 'cancel' to return to a previous state: ");
                String input2 = scan.nextLine();
                System.out.println(input2 + "... ");
                if (input2.equals("cancel")) {
                    enteringPassword = false;
                }else {

                    if (input2.equals(accountInQuestion.getPassword())) {
                        loggedOut = false;
                        enteringPassword = false;
                    } else {
                        System.out.println("Incorrect password. ");
                    }
                    if (accountInQuestion.isItFrozen()) {
                        System.out.println("It looks like that account has been frozen by an administrator. ");
                        System.out.println("If you would like to enquire further, contact customer service at 1-888-555-1212.");
                        checkingName = true;
                        enteringPassword = false;
                        loggedOut = true;
                    }
                }
            }
        }
        System.out.println("Correct! Welcome to your account. ");
        return accountInQuestion;
    }
}
