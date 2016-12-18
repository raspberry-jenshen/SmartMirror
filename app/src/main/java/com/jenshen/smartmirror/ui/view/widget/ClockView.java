package com.jenshen.smartmirror.ui.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.jenshen.smartmirror.R;
import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData;

import java.util.Calendar;
import java.util.Locale;

public class ClockView extends View implements Widget<ClockWidgetData> {

    private static final float SEC_RAD = 2 * (float) Math.PI / 60;
    private static final float MIN_RAD = 2 * (float) Math.PI / 60;
    private static final float HOUR_RAD = 2 * (float) Math.PI / 12;
    private static final int SEC_DEG = 360 / 60;
    private static final int MIN_DEG = 360 / 60;
    private static final int HOUR_DEG = 360 / 12;
    private Paint secPaint, minPaint, hourPaint, dotPaint, circlePaint;
    private TextPaint numeralsPaint, digitalClockPaint;
    private RectF mRectF;
    private Rect mRect;

    private int second = 0, minute = 0, hour = 0;
    private float radius;
    private Path secHand, minHand, hourHand;
    private boolean mShowDigitalClock;
    private boolean mShowSecondHand;
    private boolean mShowDots;
    private boolean mShowNumerals;
    private String[] mNumerals = new String[]{"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private float numeralTextHeight = 0;

    public ClockView(Context context) {
        super(context);
        init();
        if (!isInEditMode()) {
            initAttr(null);
        }
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        if (!isInEditMode()) {
            initAttr(attrs);
        }
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (!isInEditMode()) {
            initAttr(attrs);
        }
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        if (!isInEditMode()) {
            initAttr(attrs);
        }
    }

    @Override
    public void updateWidget(ClockWidgetData clockWidgetInfo) {
        if (hour != clockWidgetInfo.getHours()) {
            setHour(clockWidgetInfo.getHours());
        }
        if (minute != clockWidgetInfo.getMinutes()) {
            setMinute(clockWidgetInfo.getMinutes());
        }
        if (second != clockWidgetInfo.getSeconds()) {
            setSecond(clockWidgetInfo.getSeconds());
        }
    }

    public boolean isShowDigitalClock() {
        return mShowDigitalClock;
    }

    public void setShowDigitalClock(boolean showDigitalClock) {
        this.mShowDigitalClock = showDigitalClock;
        invalidate();
        requestLayout();
    }

    public boolean isShowDots() {
        return mShowDots;
    }

    public void setShowDots(boolean showDots) {
        this.mShowDots = showDots;
        invalidate();
        requestLayout();
    }

    public boolean isShowNumerals() {
        return mShowNumerals;
    }

    public void setShowNumerals(boolean showNumerals) {
        this.mShowNumerals = showNumerals;
        invalidate();
        requestLayout();
    }

    public boolean isShowSecondHand() {
        return mShowSecondHand;
    }

    public void setShowSecondHand(boolean showSecondHand) {
        this.mShowSecondHand = showSecondHand;
        invalidate();
        requestLayout();
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second % 60;
        invalidate();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute % 60;
        invalidate();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour % 12;
        invalidate();
    }

    public int getSecondHandColor() {
        return secPaint.getColor();
    }

    public void setSecondHandColor(int color) {
        secPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getMinuteHandColor() {
        return minPaint.getColor();
    }

    public void setMinuteHandColor(int color) {
        minPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getHourHandColor() {
        return hourPaint.getColor();
    }

    public void setHourHandColor(int color) {
        hourPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getDotColor() {
        return dotPaint.getColor();
    }

    public void setDotColor(int color) {
        dotPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getNumeralColor() {
        return numeralsPaint.getColor();
    }

    public void setNumeralColor(int color) {
        numeralsPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getDigitalClockTextColor() {
        return digitalClockPaint.getColor();
    }

    public void setDigitalClockTextColor(int color) {
        digitalClockPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getCircleColor() {
        return circlePaint.getColor();
    }

    public void setCircleColor(int color) {
        circlePaint.setColor(color);
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        desenharPontos(canvas);
        drawTime(canvas);
        desenharPonteiros(canvas, getHour(), getMinute(), getSecond());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float realWidth = getWidth() - (paddingLeft + paddingRight);
        float realHeight = getHeight() - (paddingTop + paddingBottom);

        float width = Math.min(realWidth, realHeight);
        float height = Math.min(realWidth, realHeight);

        radius = width / 2;

        digitalClockPaint.setTextSize((float) Math.sqrt(radius) * 2.5f);
        numeralsPaint.setTextSize((float) Math.sqrt(radius) * 2);
        numeralsPaint.setFakeBoldText(true);
        numeralsPaint.setTextAlign(Paint.Align.CENTER);
        numeralsPaint.getTextBounds("0", 0, 1, mRect);
        numeralTextHeight = mRect.height() / 2f - mRect.bottom;

        float rectLeft = realWidth / 2 - radius + paddingLeft;
        float rectTop = realHeight / 2 - radius + paddingTop;
        float rectRight = realWidth / 2 - radius + paddingRight + width;
        float rectBottom = realHeight / 2 - radius + paddingBottom + height;

        mRectF.set(rectLeft, rectTop, rectRight, rectBottom);

        secHand.reset();
        secHand.moveTo(mRectF.centerX(), mRectF.centerY());
        secHand.lineTo(mRectF.centerX(), mRectF.centerY() - (radius - numeralTextHeight * 1.41f));
        secHand.close();

        minHand.reset();
        minHand.moveTo(mRectF.centerX(), mRectF.centerY());
        minHand.lineTo(mRectF.centerX() + radius * 0.025f, mRectF.centerY());
        minHand.lineTo(mRectF.centerX() + radius * 0.0015625f, mRectF.centerY() - (radius - numeralTextHeight * 1.41f));
        minHand.lineTo(mRectF.centerX() - radius * 0.0015625f, mRectF.centerY() - (radius - numeralTextHeight * 1.41f));
        minHand.lineTo(mRectF.centerX() - radius * 0.025f, mRectF.centerY());
        minHand.close();

        hourHand.reset();
        hourHand.moveTo(mRectF.centerX(), mRectF.centerY());
        hourHand.lineTo(mRectF.centerX() + radius * 0.025f, mRectF.centerY());
        hourHand.lineTo(mRectF.centerX() + radius * 0.0015625f, mRectF.centerY() - radius * 0.7f);
        hourHand.lineTo(mRectF.centerX() - radius * 0.0015625f, mRectF.centerY() - radius * 0.7f);
        hourHand.lineTo(mRectF.centerX() - radius * 0.025f, mRectF.centerY());
        hourHand.close();
    }

    /* private methods */

    private void init() {
        final Calendar c = Calendar.getInstance();
        mShowDigitalClock = true;
        mShowSecondHand = true;
        mShowNumerals = true;
        mShowDots = true;
        second = c.get(Calendar.SECOND);
        minute = c.get(Calendar.MINUTE);
        hour = c.get(Calendar.HOUR);
        mRect = new Rect();
        mRectF = new RectF();
        secHand = new Path();
        minHand = new Path();
        hourHand = new Path();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(0);

        numeralsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        numeralsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        numeralsPaint.setTextAlign(Paint.Align.CENTER);
        numeralsPaint.setStrokeCap(Paint.Cap.ROUND);
        numeralsPaint.setColor(Color.WHITE);

        digitalClockPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        digitalClockPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        digitalClockPaint.setTextAlign(Paint.Align.CENTER);
        digitalClockPaint.setStrokeCap(Paint.Cap.ROUND);

        digitalClockPaint.setColor(Color.WHITE);

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        dotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dotPaint.setStrokeWidth(3);
        dotPaint.setStrokeCap(Paint.Cap.ROUND);
        dotPaint.setColor(Color.WHITE);

        secPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        secPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        secPaint.setStrokeWidth(2);
        secPaint.setStrokeCap(Paint.Cap.ROUND);
        secPaint.setColor(Color.RED);

        minPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        minPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        minPaint.setStrokeWidth(2);
        minPaint.setColor(Color.WHITE);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        hourPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        hourPaint.setStrokeWidth(2);
        hourPaint.setColor(Color.WHITE);
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ClockView);
            try {
                mShowDigitalClock = attributes.getBoolean(R.styleable.ClockView_showDigitalClock, mShowDigitalClock);
                mShowSecondHand = attributes.getBoolean(R.styleable.ClockView_showSecondHand, mShowSecondHand);
                mShowNumerals = attributes.getBoolean(R.styleable.ClockView_showNumerals, mShowNumerals);
                mShowDots = attributes.getBoolean(R.styleable.ClockView_showDots, mShowDots);
                second = attributes.getInt(R.styleable.ClockView_second, second);
                minute = attributes.getInt(R.styleable.ClockView_minute, minute);
                hour = attributes.getInt(R.styleable.ClockView_hour, hour);

                circlePaint.setColor(attributes.getColor(R.styleable.ClockView_circleColor, circlePaint.getColor()));
                numeralsPaint.setColor(attributes.getColor(R.styleable.ClockView_numeralColor, numeralsPaint.getColor()));
                digitalClockPaint.setColor(attributes.getColor(R.styleable.ClockView_digitalClockTextColor, digitalClockPaint.getColor()));
                dotPaint.setColor(attributes.getColor(R.styleable.ClockView_dotColor, dotPaint.getColor()));
                secPaint.setColor(attributes.getColor(R.styleable.ClockView_secondHandColor, secPaint.getColor()));
                minPaint.setColor(attributes.getColor(R.styleable.ClockView_minuteHandColor, minPaint.getColor()));
                hourPaint.setColor(attributes.getColor(R.styleable.ClockView_hourHandColor, hourPaint.getColor()));
            } finally {
                attributes.recycle();
            }
        }


    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(),
                radius + 5,
                circlePaint);
        canvas.restore();
    }

    private void drawTime(Canvas canvas) {
        if (isShowDigitalClock()) {
            canvas.save();
            canvas.drawText(String.format(Locale.ENGLISH, "%02d:%02d:%02d", getHour(), getMinute(), getSecond()),
                    mRectF.centerX(), mRectF.centerY() + radius * 0.5f,
                    digitalClockPaint);
            canvas.restore();
        }
    }

    private void desenharPontos(Canvas canvas) {
        canvas.save();

        for (int i = 0; i < 12; i++) {
            final float angulo = i * HOUR_RAD - HOUR_RAD * 3;

            for (int k = 0; k <= 4; k++) {
                if (k == 0 && isShowNumerals()) {
                    canvas.drawText(mNumerals[i],
                            mRectF.centerX() + (float) Math.cos(angulo) * (radius - numeralTextHeight * 1.41f),
                            mRectF.centerY() + (float) Math.sin(angulo) * (radius - numeralTextHeight * 1.41f) + numeralTextHeight,
                            numeralsPaint);
                } else if (isShowDots()) {
                    canvas.drawCircle(mRectF.centerX() + (float) Math.cos(angulo + k * MIN_RAD) * (radius - numeralTextHeight),
                            mRectF.centerY() + (float) Math.sin(angulo + k * MIN_RAD) * (radius - numeralTextHeight),
                            k == 0 ? radius * 0.02f : radius * 0.005f,
                            dotPaint);
                }
            }
        }
        canvas.restore();
    }

    private void desenharPonteiros(Canvas canvas, int hour, int min, int sec) {
        canvas.save();
        canvas.rotate(MIN_DEG * min + 0.1f * sec, mRectF.centerX(), mRectF.centerY());
        canvas.drawPath(minHand, minPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(HOUR_DEG * hour + 0.5f * min + 0.1f * sec, mRectF.centerX(), mRectF.centerY());
        canvas.drawPath(hourHand, hourPaint);
        canvas.restore();

        if (isShowSecondHand()) {
            canvas.save();
            canvas.rotate(SEC_DEG * sec, mRectF.centerX(), mRectF.centerY());
            canvas.drawPath(secHand, secPaint);
            canvas.restore();
        }
    }
}