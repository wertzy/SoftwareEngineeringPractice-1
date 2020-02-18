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
                        ba.withdraw( promptAmount("withdraw") );
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

    private double promptAmount(String action) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter an amount to "+action+": ");
        return scan.nextDouble();
    }

}
