package edu.ithaca.dragon.bank;

import java.util.Collection;

public interface AdminAPI {

    public double calcTotalAssets();

    public Collection<String> findAcctIdsWithSuspiciousActivity();

    public void freezeAccount(String acctId);

    public void unfreezeAcct(String acctId);

}
