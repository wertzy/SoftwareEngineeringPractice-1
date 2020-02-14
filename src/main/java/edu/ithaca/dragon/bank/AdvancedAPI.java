package edu.ithaca.dragon.bank;

//API to be used by Teller systems
public interface AdvancedAPI extends BasicAPI {

    public void createAccount(String acctId, String password, String type, double startingBalance);

    public void closeAccount(String acctId) throws FrozenAccountException;
}
