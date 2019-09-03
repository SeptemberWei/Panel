package com.foton.library.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.foton.library.R;


public class BallView extends View {

    private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

    public static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#802D4F95");
    public static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#804169E2");
    public static final int DEFAULT_BORDER_COLOR = Color.parseColor("#80ffffff");
    public static final ShapeType DEFAULT_WAVE_SHAPE = ShapeType.CIRCLE;

    public enum ShapeType {
        CIRCLE,
        SQUARE
    }

    private boolean mShowWave;

    private BitmapShader mWaveShader;
    private Matrix mShaderMatrix;
    private Paint mViewPaint;
    private Paint mBorderPaint;
    private Paint textPaint;

    private float mDefaultAmplitude;
    private float mDefaultWaterLevel;
    private float mDefaultWaveLength;
    private double mDefaultAngularFrequency;

    private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
    private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

    private int mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
    private int mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR;
    private ShapeType mShapeType = DEFAULT_WAVE_SHAPE;

    private int width, height;

    private String mText;

    private int TEXTCOLOR = Color.WHITE;

    private float TEXTSIZE = 10;

    public BallView(Context context) {
        super(context);
        init(context);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context) {
        mShaderMatrix = new Matrix();
        mViewPaint = new Paint();

        mViewPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);

    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.BallView, 0, 0);

        mAmplitudeRatio = typedArray.getFloat(R.styleable.BallView_amplitudeRatio, DEFAULT_AMPLITUDE_RATIO);
        mWaterLevelRatio = typedArray.getFloat(R.styleable.BallView_waveWaterLevel, DEFAULT_WATER_LEVEL_RATIO);
        mWaveLengthRatio = typedArray.getFloat(R.styleable.BallView_waveLengthRatio, DEFAULT_WAVE_LENGTH_RATIO);
        mWaveShiftRatio = typedArray.getFloat(R.styleable.BallView_waveShiftRatio, DEFAULT_WAVE_SHIFT_RATIO);
        mFrontWaveColor = typedArray.getColor(R.styleable.BallView_frontWaveColor, DEFAULT_FRONT_WAVE_COLOR);
        mBehindWaveColor = typedArray.getColor(R.styleable.BallView_behindWaveColor, DEFAULT_BEHIND_WAVE_COLOR);
        mShapeType = typedArray.getInt(R.styleable.BallView_waveShape, 0) == 0 ? ShapeType.CIRCLE : ShapeType.SQUARE;
        mShowWave = typedArray.getBoolean(R.styleable.BallView_showWave, true);

        int borderWidth = typedArray.getInt(R.styleable.BallView_border_Width, 0);
        int borderColor = typedArray.getColor(R.styleable.BallView_border_Color, DEFAULT_BORDER_COLOR);
        if (borderWidth != 0) {
            mBorderPaint = new Paint();
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStrokeWidth(borderWidth);
        }

        mText = typedArray.getString(R.styleable.BallView_text_str);
        mText = mText == null ? "" : mText;

        TEXTCOLOR = typedArray.getColor(R.styleable.BallView_text_Color, Color.WHITE);
        TEXTSIZE = typedArray.getDimension(R.styleable.BallView_text_Size, 10);

        textPaint.setTextSize(px2sp(context, TEXTSIZE));
        textPaint.setColor(TEXTCOLOR);

        typedArray.recycle();
    }

    public float getWaveShiftRatio() {
        return mWaveShiftRatio;
    }

    public void setWaveShiftRatio(float waveShiftRatio) {
        if (mWaveShiftRatio != waveShiftRatio) {
            mWaveShiftRatio = waveShiftRatio;
            invalidate();
        }
    }

    public float getWaterLevelRatio() {
        return mWaterLevelRatio;
    }

    public void setWaterLevelRatio(float waterLevelRatio) {
        if (mWaterLevelRatio != waterLevelRatio) {
            mWaterLevelRatio = waterLevelRatio;
            invalidate();
        }
    }

    public float getAmplitudeRatio() {
        return mAmplitudeRatio;
    }


    public void setAmplitudeRatio(float amplitudeRatio) {
        if (mAmplitudeRatio != amplitudeRatio) {
            mAmplitudeRatio = amplitudeRatio;
            invalidate();
        }
    }

    public float getWaveLengthRatio() {
        return mWaveLengthRatio;
    }

    public void setWaveLengthRatio(float waveLengthRatio) {
        mWaveLengthRatio = waveLengthRatio;
    }

    public boolean isShowWave() {
        return mShowWave;
    }

    public void setShowWave(boolean showWave) {
        mShowWave = showWave;
    }

    public void setBorder(int width, int color) {
        if (mBorderPaint == null) {
            mBorderPaint = new Paint();
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setStyle(Paint.Style.STROKE);
        }
        mBorderPaint.setColor(color);
        mBorderPaint.setStrokeWidth(width);

        invalidate();
    }

    public void setWaveColor(int behindWaveColor, int frontWaveColor) {
        mBehindWaveColor = behindWaveColor;
        mFrontWaveColor = frontWaveColor;

        if (getWidth() > 0 && getHeight() > 0) {
            mWaveShader = null;
            createShader();
            invalidate();
        }
    }

    public void setShapeType(ShapeType shapeType) {
        mShapeType = shapeType;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        createShader();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getViewSize(400, widthMeasureSpec);
        height = getViewSize(400, heightMeasureSpec);
    }


    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY:
                viewSize = size;
                break;
        }
        return viewSize;
    }

    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
        mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength = getWidth();

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(2);
        wavePaint.setAntiAlias(true);

        final int endX = getWidth() + 1;
        final int endY = getHeight() + 1;

        float[] waveY = new float[endX];

//        wavePaint.setColor(mBehindWaveColor);
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{mBehindWaveColor, mBehindWaveColor, mBehindWaveColor}, new float[]{0.0f, 0.5f, 1f}, Shader.TileMode.REPEAT);
        wavePaint.setShader(mLinearGradient);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

//        wavePaint.setColor(mFrontWaveColor);
        LinearGradient mLinearGradient2 = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{mFrontWaveColor, mBehindWaveColor,  Color.parseColor("#663C93F7")}, new float[]{0.0f, 0.5f, 1f}, Shader.TileMode.REPEAT);
        wavePaint.setShader(mLinearGradient2);
        final int wave2Shift = (int) (mDefaultWaveLength / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(mWaveShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShowWave && mWaveShader != null) {
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }

            mShaderMatrix.setScale(
                    mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                    mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                    0,
                    mDefaultWaterLevel);
            mShaderMatrix.postTranslate(
                    mWaveShiftRatio * getWidth(),
                    (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight());

            mWaveShader.setLocalMatrix(mShaderMatrix);

            float borderWidth = mBorderPaint == null ? 0f : mBorderPaint.getStrokeWidth();
            switch (mShapeType) {
                case CIRCLE:
                    if (borderWidth > 0) {
                        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f,
                                (getWidth() - borderWidth) / 2f - 1f, mBorderPaint);
                    }
                    float radius = getWidth() / 2f - borderWidth;
                    canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mViewPaint);
                    break;
                case SQUARE:
                    if (borderWidth > 0) {
                        canvas.drawRect(
                                borderWidth / 2f,
                                borderWidth / 2f,
                                getWidth() - borderWidth / 2f - 0.5f,
                                getHeight() - borderWidth / 2f - 0.5f,
                                mBorderPaint);
                    }
                    canvas.drawRect(borderWidth, borderWidth, getWidth() - borderWidth,
                            getHeight() - borderWidth, mViewPaint);
                    break;
            }
        } else {
            mViewPaint.setShader(null);
        }
        drawText(canvas, textPaint, mText);
    }

    private void drawText(Canvas canvas, Paint paint, String text) {
        Rect targetRect = new Rect(0, 0, width, height);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, targetRect.centerX(), baseline, paint);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private void setText(String text) {
        this.mText = text;
    }
}
