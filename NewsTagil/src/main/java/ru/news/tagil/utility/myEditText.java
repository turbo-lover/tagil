package ru.news.tagil.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

/**
 * Created by Alexander on 15.08.13.
 */
public class myEditText extends EditText {
    public myEditText(Context context) {
        super(context);
    }

    public myEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }
    public void init() {
        myPreferencesWorker pw = new myPreferencesWorker(getContext());
        try {
            String typeface = pw.get_typeface();
            float typeSize = pw.get_typeface_size();
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), typeface);
            this.setTypeface(tf);
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX,typeSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
