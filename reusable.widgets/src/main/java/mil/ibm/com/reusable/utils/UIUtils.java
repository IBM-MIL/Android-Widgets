/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Collections of utility functions for UI operations.
 */
public class UIUtils {
    private Activity activity;

    /**
     * Constructor for this class.
     *
     * @param activity
     */
    public UIUtils(Activity activity) {
        super();
        this.activity = activity;
    }

    /**
     * Shows a Toast with the specified string.
     *
     * @param msg
     */
    public void showToastOnUiThread(final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method must be invoked from the main UI thread. It shows a pop-up
     * dialog with the specified message.
     *
     * @param title
     * @param message
     */
    public void showPopUpMessage(String title, String message) {
        showPopUpMessage(title, message, null);
    }

    /**
     * This method must be invoked from the main UI thread. It shows a pop-up
     * dialog with the specified message and executes the onNeutralClick()
     * method of the callback instance.
     *
     * @param title
     * @param message
     * @param callback
     */
    public void showPopUpMessage(String title, String message,
                                 final DialogCallback callback) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setMessage(message).setTitle(title).setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (callback != null) {
                            callback.onNeutralClick();
                        }
                    }
                });
        if (!activity.isFinishing()) {
            alertBuilder.show();
        }
    }

    /**
     * This method must be invoked from the main UI thread. It shows a pop-up
     * dialog with the specified message and executes the onPositiveClick() &
     * onNegativeClick() methods of the callback instance.
     *
     * @param title
     * @param message
     * @param callback
     */
    public void showPopUpOption(String title, String message,
                                final DialogCallback callback) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (callback != null) {
                                    callback.onPositiveClick();
                                }
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (callback != null) {
                            callback.onNegativeClick();
                        }
                    }
                });
        if (!activity.isFinishing()) {
            alertBuilder.show();
        }
    }

    /**
     * Hides the soft keyboard.
     */
    public void hideSoftKeyboard() {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void setGrayScaleOnImageView(ImageView v) {
        toggleGrayScaleOnImageView(v, true);
    }

    public static void unsetGrayScaleOnImageView(ImageView v) {
        toggleGrayScaleOnImageView(v, false);
    }

    private static void toggleGrayScaleOnImageView(ImageView v, boolean grayScaleToggleFlag) {
        int saturation = (grayScaleToggleFlag) ? 0 : 1;
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(saturation); // 0 means gray scale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);

    }

    /**
     * Determines if the network is available.
     *
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Point getDisplaySize() {
        // Get width of the screen
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public void vibratePhone(long vibrateLength) {
        Vibrator v = (Vibrator) activity
                .getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(vibrateLength);
    }

    public static float pixelsToDPI(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        // float density = ctx.getResources().getDisplayMetrics().density;
        float density = metrics.density;
        return (px / density);
    }

    public static float dpiToPixels(float dpi) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float density = metrics.density;
        // float density = ctx.getResources().getDisplayMetrics().density;
        return (dpi * density);
    }

    /**
     * Convenience method for replacing a fragment.
     *
     * @param containerId
     * @param fragment
     */
    public void replaceFragment(int containerId, Fragment fragment) {
        activity.getFragmentManager().beginTransaction()
                .replace(containerId, fragment).addToBackStack(null).commit();
    }

    /**
     * Provides similar behavior to replaceFragment() except the fragment being replaced
     * is not added to the back stack. This prevents the user from having to back through
     * each fragment that was visited within a single activity.
     *
     * @param containerId
     * @param fragment
     */
    public void replaceFragmentWithoutBackStack(int containerId, Fragment fragment) {
        activity.getFragmentManager().beginTransaction()
                .replace(containerId, fragment).commit();
    }
}

