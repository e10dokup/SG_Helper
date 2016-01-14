package xyz.dokup.slidecontroller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import xyz.dokup.slidecontroller.R;

public class TriangleView extends View {
    private static final String TAG = TriangleView.class.getSimpleName();
    private final TriangleView self = this;

    private int mDefaultStrokeWidth = 2;

    private int mGraphHeight = 100;
    private int mGraphWidth = 100;
    private int mStrokeWidth = 1;
    private int mFillColor = Color.WHITE;
    private int mStrokeColor = Color.BLACK;
    private boolean mStrokeFlg = true;

    /**
     * 中心座標
     */
    private int mCenterX;
    private int mCenterY;

    private float mDensity;
    private Paint mFillPaint;
    private Paint mStrokePaint;
    private Path mPath = new Path();

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setmStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(int fillColor) {
        this.mFillColor = fillColor;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
    }

    public boolean isStrokeFlg() {
        return mStrokeFlg;
    }

    public void setStrokeFlg(boolean strokeFlg) {
        this.mStrokeFlg = strokeFlg;
    }

    public int getCenterX() {
        return mCenterX;
    }

    public void setCenterX(int centerX) {
        this.mCenterX = centerX;
    }

    public int getCenterY() {
        return mCenterY;
    }

    public void setCenterY(int centerY) {
        this.mCenterY = centerY;
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        mDensity = getContext().getResources().getDisplayMetrics().density;
        mStrokeWidth = (int) (mDefaultStrokeWidth * mDensity);

        if (attrs == null) {
            setInitPaint();
            return;
        }

        TypedArray tArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.TriangleView
                );

        mStrokeWidth = tArray.getDimensionPixelSize(R.styleable.TriangleView_stroke_width, mStrokeWidth);
        mFillColor = tArray.getColor(R.styleable.TriangleView_fill_color, mFillColor);
        mStrokeColor = tArray.getColor(R.styleable.TriangleView_stroke_color, mStrokeColor);
        mStrokeFlg = tArray.getBoolean(R.styleable.TriangleView_stroke_flg, true);

        setInitPaint();
    }

    private void setInitPaint(){
        mFillPaint = new Paint();
        mFillPaint.setAntiAlias(true);
        mFillPaint.setColor(mFillColor);

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStrokeWidth(mStrokeWidth);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mStrokePaint.setColor(mStrokeColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mGraphWidth = w;
        mGraphHeight = h;

        int drawLinePadding = 0;
        if(mStrokeFlg) {
            drawLinePadding = mStrokeWidth;
        }
        mPath.reset();
        mPath.moveTo(drawLinePadding,drawLinePadding);
        mPath.lineTo(mGraphWidth - drawLinePadding,drawLinePadding);
        mPath.lineTo(mCenterX,mGraphHeight - drawLinePadding);
        mPath.close();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle(canvas);
    }

    private void drawTriangle(Canvas canvas){
        canvas.drawPath(mPath,mFillPaint);
        if(mStrokeFlg){
            canvas.drawPath(mPath,mStrokePaint);
        }
    }
}