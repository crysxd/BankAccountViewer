package crysxd.de.bankaccountviewer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

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
    private RecyclerView mRecyclerView;
    private View mRoot;

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
            TransactionLoadTask task = new TransactionLoadTask(this, this.mAccountId);
            task.execute();

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
        mRoot =  inflater.inflate(R.layout.transaction_detail, container, false);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.recyclerView);
        return mRoot;
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

        assert mRecyclerView != null;
        mRecyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(result));
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(false);

    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Transaction> mValues;

        public SimpleItemRecyclerViewAdapter(List<Transaction> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).getName());
            holder.mDetailView.setText(mValues.get(position).getAmount() + mValues.get(position).getCurrency());

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public final TextView mDetailView;
            public Transaction mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(android.R.id.text1);
                mDetailView = (TextView) view.findViewById(android.R.id.text2);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mDetailView.getText() + "'";
            }
        }
    }
}
