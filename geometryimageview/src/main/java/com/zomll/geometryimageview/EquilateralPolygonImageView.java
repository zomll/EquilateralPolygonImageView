package com.zomll.geometryimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by zzh on 2017/7/28.
 */

public class EquilateralPolygonImageView extends GeometryImageView {
    private int n;

    public void setN(int n) {
        this.n = n;
    }

    public EquilateralPolygonImageView(Context context) {
        this(context, null);
    }

    public EquilateralPolygonImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EquilateralPolygonImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.EquilateralPolygonImageView);

        int aInteger = a.getInteger(R.styleable.EquilateralPolygonImageView_N, 0);
        setN(aInteger);

    }

    @Override
    public Bitmap getMaskBitamp() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPolygon(new RectF(0, 0, getWidth(), getHeight()), canvas, paint, n);
        return bitmap;
    }

    /**
     * @param rect
     * @param canvas
     * @param paintByLevel
     * @param number
     */
    public void drawPolygon(RectF rect, Canvas canvas, Paint paintByLevel, int number) {
        if (number < 3) {
            return;
        }
        float r = (rect.right - rect.left) / 2;
        float mX = (rect.right + rect.left) / 2;
        float my = (rect.top + rect.bottom) / 2;
        Path path = new Path();
        for (int i = 0; i <= number; i++) {
            // - 0.5 : Turn 90 ° counterclockwise
            float alpha = Double.valueOf(((2f / number) * i - 0.5) * Math.PI).floatValue();
            float nextX = mX + Double.valueOf(r * Math.cos(alpha)).floatValue();
            float nextY = my + Double.valueOf(r * Math.sin(alpha)).floatValue();
            if (i == 0) {
                path.moveTo(nextX, nextY);
            } else {
                path.lineTo(nextX, nextY);
            }
        }
        canvas.drawPath(path, paintByLevel);
    }
}
