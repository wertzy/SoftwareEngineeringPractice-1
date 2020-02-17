package edu.ithaca.dragon.bank;

public class ATMUITest {
    public static void main(String[] args) throws FrozenAccountException, InsufficientFundsException {
        CentralBank testBank=new CentralBank();
        BasicAPI testBaseAPI=testBank;
        ATMUI testUI=new ATMUI(testBaseAPI);


        //Desmond's Test
        BankAccount tb=new BankAccount("a@mail.com","abc","checking",500);
        testUI.displayOptions(tb);
    }
}

