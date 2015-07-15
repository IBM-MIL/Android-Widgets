/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.widgets.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mil.ibm.com.reusablewidgets.R;


public class LoadActivity extends Activity {

    private DummyNetworkCall asyncTask;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        textView = (TextView) findViewById(R.id.loaded_text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        asyncTask.cancel(true);
        asyncTask = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView.setVisibility(View.INVISIBLE);
        // Create dummy task to simulate network call
        asyncTask = new DummyNetworkCall(this, new NetworkDataHandler() {
            @Override
            public void onDataLoaded() {
                textView.setVisibility(View.VISIBLE);
            }
        });
        asyncTask.execute();
    }
}
