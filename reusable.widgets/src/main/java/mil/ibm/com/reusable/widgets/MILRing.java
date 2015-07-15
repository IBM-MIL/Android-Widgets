/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import mil.ibm.com.reusable.utils.NumberUtils;
import mil.ibm.com.reusable.utils.UIUtils;

/**
 * MILRing class definition.
 */
public class MILRing extends View {

    private int arcWidth;
    private int negativeColor;
    private int aboveThresholdColor;
    private int belowThresholdColor;
    private int threshold = 50;
    private int percentage = 100;
    private int percentageTextSize;
    private int mentions;
    private int mentionsTextSize;
    private int arc1Color;
    private float startAngle1 = 270f;
    private float sweepAngle1;
    private float startAngle2;
    private float sweepAngle2;
    private RectF circle;
    private Rect percentageTextRectangle;
    private Rect mentionsTextRectangle;
    private Paint circlePaint;
    private Paint arcPaint;
    private Paint linePaint;
    private Paint percentageTextPaint;
    private Paint mentionsTextPaint;
    private String percentageText;
    private String mentionsText;

    public MILRing(Context context) {
        super(context);
        init();
    }

    public MILRing(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs,
                R.styleable.MILRing);
        // Process values specified in the XML file for the ring
        percentage = arr.getInt(R.styleable.MILRing_percentage, -1);
        mentions = arr.getInt(R.styleable.MILRing_mentions, -1);
        threshold = arr.getInt(R.styleable.MILRing_threshold, 50);
        int defArcWidth = (int) UIUtils.dpiToPixels(5);
        arcWidth = arr.getDimensionPixelSize(R.styleable.MILRing_arcWidth,
                defArcWidth);
        int defPercentageTextSize = (int) UIUtils.dpiToPixels(24);
        percentageTextSize = arr.getDimensionPixelSize(
                R.styleable.MILRing_percentageTextSize, defPercentageTextSize);
        int defMentionsTextSize = (int) UIUtils.dpiToPixels(14);
        mentionsTextSize = arr.getDimensionPixelSize(
                R.styleable.MILRing_mentionsTextSize, defMentionsTextSize);
        // Set ring colors
        aboveThresholdColor = arr.getColor(R.styleable.MILRing_aboveThresholdColor, getResources().getColor(R.color.MILGreen));
        belowThresholdColor = arr.getColor(R.styleable.MILRing_belowThresholdColor, getResources().getColor(R.color.MILYellow));
        negativeColor = arr.getColor(R.styleable.MILRing_negativeColor, getResources().getColor(R.color.MILBlue));
        // Recycle the type array
        arr.recycle();
        init();
    }

    /**
     * Creating objects ahead of time is an important optimization.
     * We should do this outside the onDraw() method.
     */
    private void init() {
        // Create circle (rectangle for circle)
        circle = new RectF();

        // Create paint object for circle
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.TRANSPARENT);
        circlePaint.setStyle(Paint.Style.FILL);

        // Create paint object for arc
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);

        // Create paint object for line
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.WHITE);
        int lineThickness = (int) UIUtils.dpiToPixels(1);
        linePaint.setStrokeWidth(lineThickness);

        // Create rectangle for texts
        percentageTextRectangle = new Rect();
        mentionsTextRectangle = new Rect();

        // Create paint for percentage and mentions texts
        percentageTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG
                | Paint.ANTI_ALIAS_FLAG);
        // percentageTextPaint.setTypeface();
        percentageTextPaint.setColor(Color.WHITE);
        percentageTextPaint.setAntiAlias(true);

        mentionsTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG
                | Paint.ANTI_ALIAS_FLAG);
        // mentionsTextPaint.setTypeface();
        mentionsTextPaint.setColor(Color.WHITE);
        mentionsTextPaint.setAntiAlias(true);

        computeValues();
    }

    /**
     * Compute angle values for ring.
     */
    private void computeValues() {
        if (percentage >= 0) {
            // Sanity check
            percentage = percentage > 100 ? 100 : percentage;

            // Compute angles for arcs
            sweepAngle1 = (percentage * 360.0f) / 100.0f;
            float tmp = startAngle1 + sweepAngle1;
            startAngle2 = (tmp >= 360) ? (tmp - 360.0f) : tmp;
            sweepAngle2 = (((100 - percentage) * 360.0f) / 100.0f);

            // Choose color for arc1
            arc1Color = (percentage >= threshold) ? aboveThresholdColor : belowThresholdColor;

            // Text for percentage
            percentageText = String.valueOf(percentage);
            percentageTextPaint.setTextSize(percentageTextSize);

            // Text for mentions
            if (mentions > -1) {
                mentionsText = NumberUtils.transformNumber(mentions);
                mentionsTextPaint.setTextSize(mentionsTextSize);
            }
        }
    }

    /**
     * Draw ring!
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (percentage >= 0) {
            // Calculate coordinates for circle/rectangle
            int left = arcWidth + getPaddingLeft();
            int top = arcWidth + getPaddingTop();
            int right = getWidth() - arcWidth - getPaddingRight();
            int bottom = getHeight() - arcWidth - getPaddingBottom();

            // Draw circle
            canvas.drawColor(Color.TRANSPARENT);
            circle.set(left, top, right, bottom);
            canvas.drawOval(circle, circlePaint);

            // Draw arcs
            arcPaint.setColor(arc1Color);
            canvas.drawArc(circle, startAngle1, sweepAngle1, false, arcPaint);

            arcPaint.setColor(negativeColor);
            canvas.drawArc(circle, startAngle2, sweepAngle2, false, arcPaint);

            percentageTextPaint.getTextBounds(percentageText, 0,
                    percentageText.length(), percentageTextRectangle);
            if (mentions == -1) {
                // Draw percentage text on the center of the circle
                float xPos = (((right - left) / 2) + left)
                        - Math.abs(percentageTextRectangle.exactCenterX());
                float yPos = (((bottom - top) / 2) + top)
                        + Math.abs(percentageTextRectangle.exactCenterY());
                canvas.drawText(percentageText, xPos, yPos, percentageTextPaint);
            } else {
                // Draw line in the center of circle
                float centerOfCircle = ((bottom - top) / 2) + top;
                canvas.drawLine(left + arcWidth, centerOfCircle, right
                        - arcWidth, centerOfCircle, linePaint);

                // Draw percentage text above the center of the circle
                float xPos1 = (((right - left) / 2) + left)
                        - Math.abs(percentageTextRectangle.exactCenterX());
                float yPos1 = (((centerOfCircle - top - arcWidth) / 2) + top + arcWidth)
                        + Math.abs(percentageTextRectangle.exactCenterY());
                canvas.drawText(percentageText, xPos1, yPos1,
                        percentageTextPaint);

                mentionsTextPaint.getTextBounds(mentionsText, 0,
                        mentionsText.length(), mentionsTextRectangle);
                // Draw mentions text under the center of the circle
                float xPos2 = (((right - left) / 2) + left)
                        - Math.abs(mentionsTextRectangle.exactCenterX());
                float yPos2 = (((bottom - arcWidth - centerOfCircle) / 2) + centerOfCircle)
                        + Math.abs(mentionsTextRectangle.exactCenterY());
                canvas.drawText(mentionsText, xPos2, yPos2, mentionsTextPaint);
            }
        }
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        reconfigureView();
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
        reconfigureView();
    }

    public int getAboveThresholdColor() {
        return aboveThresholdColor;
    }

    public void setAboveThresholdColor(int aboveThresholdColor) {
        this.aboveThresholdColor = aboveThresholdColor;
        reconfigureView();
    }

    public int getBelowThresholdColor() {
        return belowThresholdColor;
    }

    public void setBelowThresholdColor(int belowThresholdColor) {
        this.belowThresholdColor = belowThresholdColor;
        reconfigureView();
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
        reconfigureView();
    }

    private void reconfigureView() {
        computeValues();
        this.invalidate();
    }
}
