/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mil.ibm.com.reusable.widgets.R;

/**
 * Convenience Dialog class to display a non-cancelable spinning image. Useful for implementing
 * load states in activities or fragments.
 */
public class SpinningProgressDialog extends Dialog {

    private final String TAG = getClass().getSimpleName();
    private Animation animation;
    private long startTimeMillis;
    // Default loading timeout in milliseconds
    private long loadingMinimumThreshold = 1200;

    public SpinningProgressDialog(Context ctx, int imageResourceId) {
        super(ctx);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.mil_progress_dialog);
        ImageView img = (ImageView) findViewById(R.id.loading_image);
        img.setImageResource(imageResourceId);
    }

    @Override
    public void show() {
        super.show();
        ImageView img = (ImageView) findViewById(R.id.loading_image);
        animation = AnimationUtils
                .loadAnimation(getContext(), R.anim.rotating_anim);
        img.startAnimation(animation);
        startTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void cancel() {
        super.cancel();
        loadingMinimumThreshold = 0;
    }

    @Override
    public void dismiss() {
        long timeDiff = System.currentTimeMillis() - startTimeMillis;
        long stopTimeMillis = (timeDiff > loadingMinimumThreshold) ? 0
                : (loadingMinimumThreshold - timeDiff);
        try {
            Thread.sleep(stopTimeMillis);
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            super.dismiss();
            animation.cancel();
        }
    }

    public long getLoadingMinimumThreshold() {
        return loadingMinimumThreshold;
    }

    public void setLoadingMinimumThreshold(long loadingMinimumThreshold) {
        this.loadingMinimumThreshold = loadingMinimumThreshold;
    }

}
