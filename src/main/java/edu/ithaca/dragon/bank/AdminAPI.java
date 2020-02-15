package edu.ithaca.dragon.bank;

import java.util.Collection;

public interface AdminAPI {

    public double calcTotalAssets();

    public Collection<String> findAcctIdsWithSuspiciousActivity() throws FrozenAccountException;

    public void freezeAccount(String acctId);

    public void unfreezeAcct(String acctId);

}
