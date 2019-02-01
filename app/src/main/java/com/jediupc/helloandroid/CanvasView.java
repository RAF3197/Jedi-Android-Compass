package com.jediupc.helloandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View implements View.OnTouchListener {

    Paint mPaint = new Paint();
    private float mx,my;
    private int factor;
    private float mFingerX,mFingerY;
    private boolean mPressed;

    public CanvasView(Context context,AttributeSet attrs) {
        super(context, attrs);

        mPaint.setColor(Color.parseColor("#f00000"));
        mPaint.setStrokeWidth(2);

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight();
        int w = getWidth();
        int smallest = Math.min(h, w);
        factor = (smallest/2) /10;

        if(mPressed) {
            canvas.drawCircle(mFingerX, mFingerY, smallest / 8, mPaint);
        }

        else canvas.drawCircle(w / 2 + mx*factor, h / 2 + my*factor, smallest / 8, mPaint);


        invalidate();
    }

    public void setXY(float x, float y){
        mx = x;
        my = y;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //dedo abajo
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mFingerX = event.getX();
            mFingerY = event.getY();
            mPressed = true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mPressed = false;
            /*
            mFingerX = event.getX();
            mFingerY  = event.getY();
            mPressed = true;
            */
        }
        return false;
    }
}