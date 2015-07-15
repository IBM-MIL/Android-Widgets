/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.widgets.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mil.ibm.com.reusable.utils.UIUtils;
import mil.ibm.com.reusable.widgets.MILRectangle;
import mil.ibm.com.reusable.widgets.MILRing;
import mil.ibm.com.reusablewidgets.R;

public class MainActivity extends Activity {

    private boolean toggled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * OnClick listener for the toggle button on the activity.
     *
     * @param v
     */
    public void handleToggle(View v) {
        // Get references to widgets
        MILRing ringLeft = (MILRing) findViewById(R.id.ring_left);
        MILRing ringRight = (MILRing) findViewById(R.id.ring_right);
        MILRing ringCenter = (MILRing) findViewById(R.id.ring_center);
        MILRectangle leftRectangle = (MILRectangle) findViewById(R.id.leftRectangle);
        MILRectangle rightRectangle = (MILRectangle) findViewById(R.id.rightRectangle);

        if (toggled) {
            ringLeft.setMentions(899);
            ringLeft.setPercentage(90);
            ringRight.setMentions(200000);
            ringRight.setPercentage(35);
            leftRectangle.setSolid(true);
            leftRectangle.setRectangleColor(getResources().getColor(R.color.MILYellow));
            rightRectangle.setSolid(false);
            rightRectangle.setBorderWidth((int) UIUtils.dpiToPixels(5));
            rightRectangle.setRectangleColor(getResources().getColor(R.color.MILGreen));
            ringCenter.setPercentage(75);
        } else {
            ringLeft.setMentions(200000);
            ringLeft.setPercentage(35);
            ringRight.setMentions(899);
            ringRight.setPercentage(90);
            leftRectangle.setSolid(false);
            leftRectangle.setBorderWidth((int) UIUtils.dpiToPixels(5));
            leftRectangle.setRectangleColor(getResources().getColor(R.color.MILGreen));
            rightRectangle.setSolid(true);
            rightRectangle.setRectangleColor(getResources().getColor(R.color.MILYellow));
            ringCenter.setPercentage(35);
        }
        toggled = !toggled;
    }

    /**
     * OnClick listener for the load button on the activity.
     *
     * @param v
     */
    public void startLoadActivity(View v) {
        Intent intent = new Intent(this, LoadActivity.class);
        startActivity(intent);
    }
}
