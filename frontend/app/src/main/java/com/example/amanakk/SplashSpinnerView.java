package com.example.amanakk;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class SplashSpinnerView extends View {
    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator rotationAnimator;

    public SplashSpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        float stroke = 4f * getResources().getDisplayMetrics().density;
        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setStrokeWidth(stroke);
        trackPaint.setColor(Color.rgb(229, 232, 235));
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(stroke);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setColor(Color.rgb(56, 93, 148));
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float inset = trackPaint.getStrokeWidth() / 2f + 1f;
        RectF bounds = new RectF(inset, inset, getWidth() - inset, getHeight() - inset);
        canvas.drawOval(bounds, trackPaint);
        canvas.drawArc(bounds, -90f, 105f, false, arcPaint);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        rotationAnimator = ObjectAnimator.ofFloat(this, View.ROTATION, 0f, 360f);
        rotationAnimator.setDuration(900L);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.start();
    }

    @Override protected void onDetachedFromWindow() {
        if (rotationAnimator != null) rotationAnimator.cancel();
        super.onDetachedFromWindow();
    }
}
