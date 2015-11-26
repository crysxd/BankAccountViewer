package crysxd.de.bankaccountviewer;

/**
 * Created by cwuer on 11/26/15.
 */
public interface LoadTaskCallback<T> {

    void showIndeterminateProgressDialog(int messageResource);
    void hideIndeterminateProgressDialog();
    void showErrorDialog(int messageResource);
    void onResult(T result);

}
