package com.rc.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class FontIconView extends AppCompatTextView {

   
    public FontIconView(Context context) {
        super(context);
        init(context);
    }
    
    public FontIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     *
     * @param context
     */
    private void init(Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        this.setTypeface(font);
    }
}