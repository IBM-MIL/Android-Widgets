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
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import mil.ibm.com.reusable.utils.UIUtils;

/**
 * Widget class to render rectangles.
 */
public class MILRectangle extends View {

    private Rect rectangle;
    private Paint paint;
    private int rectangleColor;
    private int borderWidth;
    private boolean solid;

    public MILRectangle(Context context) {
        super(context);
        solid = false;
        rectangleColor = getResources().getColor(R.color.MILYellow);
        init();
    }

    public MILRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs,
                R.styleable.MILRectangle);
        int defBorderWidth = (int) UIUtils.dpiToPixels(4);
        borderWidth = arr.getDimensionPixelSize(
                R.styleable.MILRectangle_borderWidth, defBorderWidth);
        solid = arr.getBoolean(R.styleable.MILRectangle_solid, false);
        rectangleColor = arr.getColor(R.styleable.MILRectangle_rectangleColor,
                getResources().getColor(R.color.MILYellow));
        arr.recycle();
        init();
    }

    /**
     * Creating objects ahead of time is an important optimization.
     */
    private void init() {
        // Create rectangle
        rectangle = new Rect();

        // Create paint object for rectangle
        paint = new Paint();
        paint.setAntiAlias(true);
        config();
    }

    private void config() {
        paint.setColor(rectangleColor);
        if (solid) {
            paint.setStrokeWidth(0);
            paint.setStyle(Style.FILL);
        } else {
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Calculate coordinates for rectangle
        int paddingBuffer = (solid) ? 0 : (borderWidth / 2);
        int left = paddingBuffer + getPaddingLeft();
        int top = paddingBuffer + getPaddingTop();
        int right = getWidth() - paddingBuffer - getPaddingRight();
        int bottom = getHeight() - paddingBuffer - getPaddingBottom();

        // Draw rectangle
        canvas.drawColor(Color.TRANSPARENT);
        rectangle.set(left, top, right, bottom);
        canvas.drawRect(rectangle, paint);
    }

    public boolean getSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
        reconfigureView();
    }

    public int getRectangleColor() {
        return rectangleColor;
    }

    public void setRectangleColor(int rectangleColor) {
        this.rectangleColor = rectangleColor;
        reconfigureView();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        reconfigureView();
    }

    private void reconfigureView() {
        config();
        this.invalidate();
    }

}
