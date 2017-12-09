package com.tencent.tmgp.jjzww.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.utils.Utils;

/**
 * Created by zhouh on 2017/11/20.
 */
public class TimeCircleProgressView extends View {

    private Paint circlePaint;
    private Paint ringPaint;
    private Paint ringPaintBg;
    private Paint ringPaintBig;

    private int mCircleColor;
    private int mProgressColor;
    private int mProgressColorBg;

    private float mRadius;
    private float mRingRadius;
    private float mStrokeWidth;

    private int mx, my;

    private int progress = Utils.CATCH_TIME_OUT;
    private int mProgress;

    public TimeCircleProgressView(Context context) {
        super(context);
    }

    public TimeCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initVariableView();
    }

    public TimeCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initVariableView();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.TimeProgressView, 0, 0);
        mRadius = typedArray.getDimension(R.styleable.TimeProgressView_radius,  56); //60
        mStrokeWidth = typedArray.getDimension(R.styleable.TimeProgressView_strokeWidth, 10);
        mRingRadius = mRadius + mStrokeWidth / 2;

        mCircleColor = typedArray.getColor(R.styleable.TimeProgressView_circleColor, 0x00000000);
        mProgressColor = typedArray.getColor(R.styleable.TimeProgressView_progressColor, 0x00000000);
        mProgressColorBg = typedArray.getColor(R.styleable.TimeProgressView_progressBgColor, 0x00000000);
    }

    private void initVariableView() {
        //内圆圈
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(mCircleColor);
        circlePaint.setStyle(Paint.Style.FILL);
        //progress的背景
        ringPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaintBg.setColor(mProgressColorBg);
        ringPaintBg.setStyle(Paint.Style.STROKE);
        ringPaintBg.setStrokeWidth(mStrokeWidth);
        //progress进度背影
        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(mProgressColor);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(mStrokeWidth);
        //最外面的红色圆环
        ringPaintBig = new Paint();
        ringPaintBig.setAntiAlias(true);
        ringPaintBig.setColor(getResources().getColor(R.color.red));
        ringPaintBig.setStyle(Paint.Style.STROKE);
        ringPaintBig.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mx = getWidth() / 2;
        my = getHeight() / 2;
        //最外面的红色圆环
        canvas.drawCircle(mx, my, mRadius + mStrokeWidth + 3, ringPaintBig);
        //内园
        canvas.drawCircle(mx, my, mRadius, circlePaint);
        //圆环的背影
        RectF rectf = new RectF();
        rectf.left = (mx - mRingRadius);
        rectf.top = (my - mRingRadius);
        rectf.right = mRingRadius * 2 + (mx - mRingRadius);
        rectf.bottom = mRingRadius * 2 + (my - mRingRadius);
        canvas.drawArc(rectf, 0, 360, false, ringPaintBg);
        //外圆弧进度
        if (mProgress > 0) {
            RectF rectf1 = new RectF();
            rectf1.left = (mx - mRingRadius);
            rectf1.top = (my - mRingRadius);
            rectf1.right = mRingRadius * 2 + (mx - mRingRadius);
            rectf1.bottom = mRingRadius * 2 + (my - mRingRadius);
            canvas.drawArc(rectf1, -90, ((float) mProgress / progress) * 360, false, ringPaint);
        }
    }

    //设置进度
    public synchronized void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

}
