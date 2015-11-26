package crysxd.de.bankaccountviewer;

import java.util.List;

import me.figo.models.Transaction;

/**
 * Created by cwuer on 11/26/15.
 */
public class TransactionLoadTask  extends LoadTask<List<Transaction>> {

    private String mAccountId;

    public TransactionLoadTask(LoadTaskCallback<List<Transaction>> callback, String accountId) {
        super(callback);

        this.mAccountId = accountId;

    }

    @Override
    protected List<Transaction> doLoad() throws Exception {
        return FigoSessionHolder.i().getTransactions(this.mAccountId);

    }
}