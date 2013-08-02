package ru.news.tagil.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class myTextView extends TextView {


    public myTextView(Context context) {
        super(context);

        try {

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public myTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public myTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        myPreferencesWorker pw = new myPreferencesWorker(getContext());

        String typeface = pw.get_typeface();
        try {

        } catch (Exception e) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), typeface);
            this.setTypeface(tf);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }
}