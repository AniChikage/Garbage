package com.netease.nrtc.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


public class ProgressView extends View {

    private static final int[] SECTION_COLORS = {Color.RED, Color.YELLOW, Color.GREEN};
    private float max;
    private float current;
    private Paint paint;
    private int width;
    private int height;

    public ProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ProgressView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setAntiAlias(true);

        float section = current/max;
        RectF rectProgressBg = new RectF(0, 0, width * section, height);
        if(section <= 1.0f/3.0f){
            if(section != 0.0f){
                paint.setColor(SECTION_COLORS[0]);
            }else{
                paint.setColor(Color.TRANSPARENT);
            }
        }else{
            int count = (section <= 1.0f/3.0f*2 ) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if(count == 2){
                positions[0] = 0.0f;
                positions[1] = 1.0f-positions[0];
            }else{
                positions[0] = 0.0f;
                positions[1] = (max/3)/current;
                positions[2] = 1.0f-positions[0]*2;
            }
            positions[positions.length-1] = 1.0f;
            LinearGradient shader = new LinearGradient(0, 0, (width)*section, height, colors,null, Shader.TileMode.MIRROR);
            paint.setShader(shader);
        }

        canvas.drawRect(rectProgressBg, paint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }


    public void setMax(float max) {
        this.max = max;
    }


    public void setProgress(float value) {
        this.current = value > max ? max : value;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            width = widthSpecSize;
        } else {
            width = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            height = dipToPx(15);
        } else {
            height = heightSpecSize;
        }
        setMeasuredDimension(width, height);
    }


}
