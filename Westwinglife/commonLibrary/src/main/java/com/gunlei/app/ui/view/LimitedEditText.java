/**
 * LimitedEditText
 *
 * Copyright (C) 2013  Khaled Bakhit
 * 
 * 
 * LimitedEditText is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LimitedEditText code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Check <http://www.gnu.org/licenses/> for more details.
 */
package com.gunlei.app.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.gunlei.app.R;
import com.gunlei.app.ui.util.DeviceUtil;

/**
 * 限定长度的文本输入框，并带有一个清空输入的按钮，并可限定最大长度。
 * 用法：
 *                 <com.beanmonitor.app.coderside.view.LimitedEditText
 *                  app:maxLength="12"
 android:id="@+id/editPassword"
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 android:minHeight="40dp"
 android:paddingLeft="4dp"
 android:background="@drawable/text_edit_selector"
 android:textColor="@color/white"
 android:layout_marginTop="12dp"
 android:hint="密码(6到12个字)"

 android:imeOptions="actionUnspecified"
 android:inputType="textPassword"
 android:textCursorDrawable="@null"
 android:maxLines="1"
 android:textSize="18sp"
 android:singleLine="true"
 android:textColorHint="@color/font_white_disable" />

 xmlns:app="http://schemas.android.com/apk/res-auto"

 * 2016-06-19
 */
public class LimitedEditText extends EditText
{
    Drawable drawable;
    /**
     * Paint Object used to paint limit text.
     */
    private Paint limit_text_paint;
         
    /**
     * Current size of text displayed in the EditView.
     */
    private int current_text_size;
    /**
     * Maximum text size allowed.
     */
    private int maximum_text_size;
    /**
     * Default limit indicator X margin used.
     */
    private final int DEFAULT_X_MARGIN= 10;
    /**
     * Default limit indicator Y margin used.
     */
    private final int DEFAULT_Y_MARGIN= 20;
    /**
     * Flag indicating text size limit is unlimited.
     */
    public static final int UNLIMITED= -100;
     
    /**
     * LimitedEditText constructor.
     * @param context Context that will display this view.
     * @param attrs Set of attributes defined for this view.
     * @param defStyle Style defined for this view.
     */
    public LimitedEditText(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        initComponents(context, attrs);
    }
    /**
     * LimitedEditText constructor.
     * @param context Context that will display this view.
     * @param attrs Set of attributes defined for this view.
     */
    public LimitedEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initComponents(context, attrs);
    }
    /**
     * LimitedEditText constructor.
     * @param context Context that will display this view.
     */
    public LimitedEditText(Context context)
    {
        super(context);
        initComponents(context, null);
    }
     
    /**
     * Initialize the UI components.
     * @param attrs Set of attributes defined for this view.
     */
    private void initComponents(Context context, AttributeSet attrs) {
        drawable = getResources().getDrawable(R.drawable.delete_edit_login);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        limit_text_paint = new Paint();
        limit_text_paint.setColor(Color.parseColor("#7fffffff"));
        limit_text_paint.setTextSize(30);
        maximum_text_size = UNLIMITED;
        current_text_size = 0;
        if (attrs != null) //android:maxLength
         {
             TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitedEditText);
             maximum_text_size = typedArray.getInt(R.styleable.LimitedEditText_maxLength, 0);
//             maximum_text_size= attrs.getAttributeIntValue("android", "maxLength", UNLIMITED);// 无法获取到安卓内置属性的取值
    }

        if(maximum_text_size > 0) {
            setMaxTextSize(maximum_text_size);
        }

         
        super.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                updateTextState();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        updateTextState();// 初始化时刷新文本显示状态
    }

    private void updateTextState() {
        if (maximum_text_size != UNLIMITED)
            current_text_size = length();

        displayDelete(length() > 0);//getText().length()获取的值有时候为0，不准确

        invalidate();
    }
     
    /**
     * Set maximum text size for given EditText.
     * @param max Maximum number of characters allowed.
     */
    public void setMaxTextSize(int max)
    {
        maximum_text_size= max;
        if(max== UNLIMITED)
            setFilters(null);
        else
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
    }
    /**
     * Remove text size limit. 
     * This will stop drawing the amount of characters left.
     */
    public void removeTextSizeLimit()
    {
        setMaxTextSize(UNLIMITED);
    }
    /**
     * Get the X position of limit indicator.
     * @param text Text to be drawn on the limit indicator.
     * @return X position of limit indicator.
     */
    protected float getLimitIndicatorX(String text)
    {
        float widths[]= new float[text.length()];
        limit_text_paint.getTextWidths(text, widths);
         
        float sum= 0;
        for(float w: widths)
            sum+= w;
        return getWidth() + getScrollX() - sum - DEFAULT_X_MARGIN;
    }
    /**
     * Get the Y position of limit indicator.
     * @param text Text to be drawn on the limit indicator.
     * @return Y position of limit indicator.
     */
    protected float getLimitIndicatorY(String text)
    {
        return DEFAULT_Y_MARGIN + getPaddingTop() + getScrollY() + DeviceUtil.dp2px(getContext(), 4);
    }
     
     
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(!hasFocus()) {
            return;
        }
        if(maximum_text_size== UNLIMITED || maximum_text_size ==0  || current_text_size == 0 )
            return;
        int lineStartY = getScrollY() + getHeight() - getPaddingBottom();
        Paint.FontMetrics textMetrics = limit_text_paint.getFontMetrics();
        float relativeHeight = -textMetrics.ascent - textMetrics.descent;

        String text= current_text_size+"/"+maximum_text_size;
        canvas.drawText(text, getLimitIndicatorX(text), //lineStartY - 2, //+ relativeHeight - 2,
                getLimitIndicatorY(text),
                limit_text_paint);
    }

    private void displayDelete(boolean show) {
        if (show) {
            setDrawableRight(drawable);
        } else {
            setDrawableRight(null);
        }
    }

    private void setDrawableRight(Drawable drawable) {
        setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }
     
}