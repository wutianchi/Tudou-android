package com.gunlei.app.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 右下三角形组件.
 */
public class BottomRightTriangleShapeView extends View {

    public BottomRightTriangleShapeView(Context context) {
        super(context);
    }

    public BottomRightTriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BottomRightTriangleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth() / 2;
        int h = getHeight();

        Path path = new Path();
        path.moveTo( w, h);
        path.lineTo( 2 * w , h);
        path.lineTo( 2 * w , w);
        path.lineTo( w , h);
        path.close();

        Paint p = new Paint();
        p.setColor( Color.parseColor("#FF9900") );

        canvas.drawPath(path, p);
    }
}