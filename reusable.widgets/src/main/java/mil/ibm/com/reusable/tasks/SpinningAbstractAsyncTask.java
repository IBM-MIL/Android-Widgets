/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.tasks;

import android.content.Context;
import android.os.AsyncTask;

import mil.ibm.com.reusable.dialogs.SpinningProgressDialog;

/**
 * An abstract asynchronous task that creates an instance of the SpinningProgressDialog class. Child
 * classes can override the methods implemented in this class but must call the super implementation
 * for each one.
 */
public abstract class SpinningAbstractAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private SpinningProgressDialog dialog;

    public SpinningAbstractAsyncTask(Context context, int imageResourceId) {
        dialog = new SpinningProgressDialog(context, imageResourceId);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        dialog.cancel();
        dialog = null;
    }

    @Override
    protected void onPostExecute(Result param) {
        super.onPostExecute(param);
        dialog.dismiss();
    }

    public long getLoadingMinimumThreshold() {
        return (isCancelled()) ? 0 : dialog.getLoadingMinimumThreshold();
    }

    public void setLoadingMinimumThreshold(long loadingMinimumThreshold) {
        if (!isCancelled()) {
            dialog.setLoadingMinimumThreshold(loadingMinimumThreshold);
        }
    }

}
