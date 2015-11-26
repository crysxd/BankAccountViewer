package crysxd.de.bankaccountviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import crysxd.de.bankaccountviewer.dummy.DummyContent;
import me.figo.models.Transaction;

/**
 * A fragment representing a single Transaction detail screen.
 * This fragment is either contained in a {@link TransactionListActivity}
 * in two-pane mode (on tablets) or a {@link TransactionDetailActivity}
 * on handsets.
 */
public class TransactionDetailFragment extends Fragment implements LoadTaskCallback<List<Transaction>> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "account_id";
    public static final String ARG_ITEM_NAME = "account_name";

    private String mAccountId;
    private String mAccountName;
    private ProgressDialog mProgressDialog;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mAccountId = mAccountName = getArguments().getString(ARG_ITEM_ID);

        }

        if(getArguments().containsKey(ARG_ITEM_ID)) {
            mAccountName = getArguments().getString(ARG_ITEM_NAME);
        }

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mAccountName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_detail, container, false);
    }

    @Override
    public void showIndeterminateProgressDialog(int messageResource) {
        if(null == this.mProgressDialog) {
            this.mProgressDialog = new ProgressDialog(this.getActivity());
            this.mProgressDialog.setIndeterminate(true);
            this.mProgressDialog.setCancelable(false);
        }

        this.mProgressDialog.setMessage(this.getString(messageResource));
        this.mProgressDialog.show();

    }

    @Override
    public void hideIndeterminateProgressDialog() {
        if(null != this.mProgressDialog) {
            this.mProgressDialog.hide();

        }
    }

    @Override
    public void showErrorDialog(int messageResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.ui_error_dialog_title);
        builder.setMessage(messageResource);
        builder.setPositiveButton(R.string.ui_ok, null);
        builder.create().show();

    }

    @Override
    public void onResult(List<Transaction> result) {

    }
}
