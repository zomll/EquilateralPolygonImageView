package com.zomll.geometryimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by zzh on 2017/7/21.
 */

public abstract class GeometryImageView extends AppCompatImageView {
    protected Paint mPaint = new Paint();

    public GeometryImageView(Context context) {
        super(context);
    }

    public GeometryImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GeometryImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable != null) {
            //设置离屏缓冲
            int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            Bitmap bitmap = getDstBitmap(drawable);
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            Bitmap maskBitamp = getMaskBitamp();
            canvas.drawBitmap(maskBitamp, 0, 0, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveLayer);
        }
    }

    /**
     * 将设置的图片以centerCrop方式显示
     * @param drawable
     * @return
     */
    @NonNull
    private Bitmap getDstBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas _canvas = new Canvas(bitmap);
        //
        final int dwidth = drawable.getIntrinsicWidth();
        final int dheight = drawable.getIntrinsicHeight();
        final int vwidth = getWidth();
        final int vheight = getHeight();

        Matrix mDrawMatrix = new Matrix();
        float scale;
        float dx = 0, dy = 0;

        if (dwidth * vheight > vwidth * dheight) {
            scale = (float) vheight / (float) dheight;
            dx = (vwidth - dwidth * scale) * 0.5f;
        } else {
            scale = (float) vwidth / (float) dwidth;
            dy = (vheight - dheight * scale) * 0.5f;
        }

        mDrawMatrix.setScale(scale, scale);
        mDrawMatrix.postTranslate(Math.round(dx), Math.round(dy));
        _canvas.concat(mDrawMatrix);
        drawable.draw(_canvas);
        return bitmap;
    }

    /**
     * 图形
     * @return
     */
    public abstract Bitmap getMaskBitamp();
}
