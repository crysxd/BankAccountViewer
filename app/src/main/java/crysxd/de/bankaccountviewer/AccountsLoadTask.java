package crysxd.de.bankaccountviewer;

import java.util.List;

import me.figo.models.Account;

/**
 * Created by cwuer on 11/26/15.
 */
public class AccountsLoadTask extends LoadTask<List<Account>> {

    public AccountsLoadTask(LoadTaskCallback<List<Account>> callback) {
        super(callback);

    }

    @Override
    protected List<Account> doLoad() throws Exception {
        return FigoSessionHolder.i().getAccounts();

    }
}
