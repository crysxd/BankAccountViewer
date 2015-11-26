package crysxd.de.bankaccountviewer;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cwuer on 11/26/15.
 */
public abstract class LoadTaskActivity<T> extends AppCompatActivity implements LoadTaskCallback<T> {

    private ProgressDialog mProgressDialog;

    @Override
    public void showIndeterminateProgressDialog(int messageResource) {
        if(null == this.mProgressDialog) {
            this.mProgressDialog = new ProgressDialog(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ui_error_dialog_title);
        builder.setMessage(messageResource);
        builder.setPositiveButton(R.string.ui_ok, null);
        builder.create().show();

    }
}
