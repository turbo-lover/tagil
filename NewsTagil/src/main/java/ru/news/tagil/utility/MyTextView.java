package ru.news.tagil.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class myTextView extends TextView {

    private Context context;
    public myTextView(Context context) {
        super(context);

    }
    public myTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public myTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }
    private void init() {
        myPreferencesWorker pw = new myPreferencesWorker(getContext());
        try {
            String typeface = pw.get_typeface();
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), typeface);
            this.setTypeface(tf);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}