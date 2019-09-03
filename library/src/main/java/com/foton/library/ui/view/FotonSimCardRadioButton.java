package com.foton.library.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;

import com.foton.library.R;

public class FotonSimCardRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    private String topString = "";
    private String bottomString = "";

    private int topColor = 0xFFFFFF;
    private int bottomColor = 0xFFFFFF;

    private float topSize = 10;
    private float bottomSize = 10;

    private Point centerPoint;

    private Paint topPaint;
    private Paint bottomPaint;

    public FotonSimCardRadioButton(Context context) {
        super(context);
    }

    public FotonSimCardRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FotonSimCardRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FotonSimCardRadioButton);

        topString = typedArray.getString(R.styleable.FotonSimCardRadioButton_top_text);
        bottomString = typedArray.getString(R.styleable.FotonSimCardRadioButton_bottom_text);

        topColor = typedArray.getColor(R.styleable.FotonSimCardRadioButton_top_text_color, topColor);
        bottomColor = typedArray.getColor(R.styleable.FotonSimCardRadioButton_bottom_text_color, bottomColor);

        topSize = dip2px(context, typedArray.getDimension(R.styleable.FotonSimCardRadioButton_top_text_size, topSize));
        bottomSize = dip2px(context, typedArray.getDimension(R.styleable.FotonSimCardRadioButton_bottom_text_size, bottomSize));

        topPaint = new Paint();
        topPaint.setTextSize(topSize);
        topPaint.setColor(topColor);
        topPaint.setFakeBoldText(true);
        topPaint.setStyle(Paint.Style.STROKE);
        topPaint.setAntiAlias(true);
        topPaint.setTextAlign(Paint.Align.CENTER);

        bottomPaint = new Paint();
        bottomPaint.setTextSize(bottomSize);
        bottomPaint.setColor(bottomColor);
        bottomPaint.setStyle(Paint.Style.STROKE);
        bottomPaint.setAntiAlias(true);
        bottomPaint.setTextAlign(Paint.Align.CENTER);

        centerPoint = new Point();

        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPoint.x = getMeasuredWidth() / 2;
        centerPoint.y = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(topString, centerPoint.x, centerPoint.y, topPaint);
        canvas.drawText(bottomString, centerPoint.x, centerPoint.y / 2 * 3, bottomPaint);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
