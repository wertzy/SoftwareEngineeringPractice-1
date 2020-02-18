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
        if (action.length() > 0) {
            System.out.println(action+" again? [y/n]");
            String choice = scan.nextLine().toLowerCase();

            if (choice.equals("y")) return errorCode;
            else return 0;
        }
        return 0;
    }

    public void run(BankAccount ba,CentralBank cb) {
        int num = 0;
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
                            BankAccount inputBA = ATMUI.inputBankAccount(cb);
                            if (inputBA==null){
                                System.out.println("Error: Bank Account not found");
                                break;
                    }
                            ba.transfer(100, inputBA);
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

}
