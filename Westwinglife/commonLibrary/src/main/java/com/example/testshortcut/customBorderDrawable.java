package com.example.testshortcut;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/***
 * 有边框的ShapeDrawable
 */
public class customBorderDrawable extends ShapeDrawable {

    private static final int DEFAULT_STROKE_WIDTH = 2;
    private Paint fillpaint, strokepaint;

    public customBorderDrawable(Context context,Shape s) {
        super(s);
        fillpaint = this.getPaint();

        strokepaint = new Paint(fillpaint);
        strokepaint.setStyle(Paint.Style.STROKE);
        strokepaint.setStrokeWidth(AppShortCutUtil.dipToPixels(context, DEFAULT_STROKE_WIDTH));
        strokepaint.setARGB(255, 0, 0, 0);
    }

    @Override
    protected void onDraw(Shape shape, Canvas canvas, Paint fillpaint) {
        shape.draw(canvas, fillpaint);
        shape.draw(canvas, strokepaint);
    }

    public void setFillColour(int c) {
        fillpaint.setColor(c);
    }

    public Paint getFillpaint() {
        return fillpaint;
    }

    public void setFillpaint(Paint fillpaint) {
        this.fillpaint = fillpaint;
    }

    public Paint getStrokepaint() {
        return strokepaint;
    }

    public void setStrokepaint(Paint strokepaint) {
        this.strokepaint = strokepaint;
    }

}
