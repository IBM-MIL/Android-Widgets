/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.widgets.demo;

import android.content.Context;

import mil.ibm.com.reusable.tasks.SpinningAbstractAsyncTask;
import mil.ibm.com.reusablewidgets.R;

/**
 * Asynchronous task for simulating a network call. To simulate a network call,
 * this task simply sleeps for 5 seconds.
 */
public class DummyNetworkCall extends SpinningAbstractAsyncTask<Void, Void, Void> {

    private long sleepTime = 5000;
    private NetworkDataHandler networkDataHandler;

    public DummyNetworkCall(Context context, NetworkDataHandler networkDataHandler) {
        super(context, R.drawable.loading_soccer_ball);
        this.networkDataHandler = networkDataHandler;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Simulate network call for N seconds
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // Ignore
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        networkDataHandler = null;
    }

    @Override
    protected void onPostExecute(Void param) {
        super.onPostExecute(param);
        networkDataHandler.onDataLoaded();
    }
    
}
