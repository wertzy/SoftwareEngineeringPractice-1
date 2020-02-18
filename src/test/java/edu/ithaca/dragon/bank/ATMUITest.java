package edu.ithaca.dragon.bank;

public class ATMUITest {
    public static void main(String[] args) throws FrozenAccountException, InsufficientFundsException {
        CentralBank testBank=CentralBankTest.getTestObject();
        BasicAPI testBaseAPI=testBank;
        ATMUI testUI=new ATMUI(testBaseAPI);

        //Peter's test
        BankAccount tb2=new BankAccount("a@mail.com","abcdef@1","checking",500);
        testUI.login(testBank);

        //Desmond's Test
        BankAccount tb=new BankAccount("a@mail.com","abcdef@1","checking",500);
        testUI.run(tb);

    }
}

