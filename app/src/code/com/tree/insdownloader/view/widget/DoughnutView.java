package com.tree.insdownloader.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.tree.insdownloader.util.DisplayUtil;

/**
 * Created by hellsam on 15/12/16.
 */
public class DoughnutView extends View {
    private static final int DEFAULT_MIN_WIDTH = 400;
    private int[] doughnutColors = new int[]{Color.parseColor("#FFAF43"), Color.parseColor("#C433AB")};
    private Paint paint = new Paint();
    private Bitmap circleBitmap;
    private int width;
    private int height;

    public DoughnutView(Context context) {
        super(context);
    }

    public DoughnutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoughnutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void resetParams() {
        width = getWidth();
        height = getHeight();
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        resetParams();
        //画背景白色圆环
        initPaint();
        float doughnutWidth = DisplayUtil.dp2px(1f);
        RectF rectF = new RectF((width > height ? Math.abs(width - height) / 2 : 0) + doughnutWidth / 2, (height > width ? Math.abs(height - width) / 2 : 0) + doughnutWidth / 2, width - (width > height ? Math.abs(width - height) / 2 : 0) - doughnutWidth / 2, height - (height > width ? Math.abs(height - width) / 2 : 0) - doughnutWidth / 2);

        //画彩色圆环
        initPaint();
        canvas.rotate(-90, width / 2, height / 2);
        paint.setStrokeWidth(doughnutWidth);
        paint.setStyle(Paint.Style.STROKE);
        if (doughnutColors.length > 1) {
            LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, doughnutColors, null, Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
        } else {
            paint.setColor(doughnutColors[0]);
        }
        canvas.drawArc(rectF, 0, 360, false, paint);

        initPaint();

        /*BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        canvas.drawCircle(getWidth()/2,getHeight()/2, getWidth()/2, paint);
*/
        super.onDraw(canvas);
    }

    /**
     * 当布局为wrap_content时设置默认长宽
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int origin) {
        int result = DEFAULT_MIN_WIDTH;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}