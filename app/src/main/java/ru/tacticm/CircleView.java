package ru.tacticm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * The type Circle view.
 */
public class CircleView extends androidx.appcompat.widget.AppCompatImageView {
    private int x, y, radius;

    private Paint paint;

    /**
     * Instantiates a new Circle view.
     *
     * @param context the context
     */
    public CircleView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * Instantiates a new Circle view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * Set color.
     *
     * @param color the color
     */
    public void setColor(int color){
        paint.setColor(0x80000000 | color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        //
        x = w>>1;
        y = h>>1;
        radius = w < h ? x : y;
        radius -= 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        paint.setColor(0x60ffffff);
        canvas.drawCircle(x, y, radius, paint);
        super.onDraw(canvas);
    }
}

