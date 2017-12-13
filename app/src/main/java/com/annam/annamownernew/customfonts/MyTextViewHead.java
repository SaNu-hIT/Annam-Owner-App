package com.annam.annamownernew.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class MyTextViewHead extends TextView {

    public MyTextViewHead(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewHead(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pacifico-Regular.ttf");
            setTypeface(tf);
        }
    }

}