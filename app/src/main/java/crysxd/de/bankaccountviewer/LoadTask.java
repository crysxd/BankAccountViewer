package crysxd.de.bankaccountviewer;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by cwuer on 11/26/15.
 */
public abstract class LoadTask<T> extends AsyncTask<Void, Void , T> {

    private LoadTaskCallback<T> mCallback;

    public LoadTask(LoadTaskCallback<T> callback) {
        this.mCallback = callback;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.mCallback.showIndeterminateProgressDialog(R.string.ui_loading_data);
    }

    @Override
    protected T doInBackground(Void... params) {
        try {
            return this.doLoad();

        } catch(Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error while loading FigoSession", e);
            e.printStackTrace();
            return null;

        }
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);

        mCallback.hideIndeterminateProgressDialog();

        if (null != result) {
            this.mCallback.onResult(result);

        } else {
            this.mCallback.showErrorDialog(R.string.ui_error_loading_data);

        }
    }

    protected abstract T doLoad() throws Exception;

}
