package ru.news.tagil.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class myTextView extends TextView {

    public myTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public myTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public myTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font.ttf");
            setTypeface(tf);
        }
    }
}