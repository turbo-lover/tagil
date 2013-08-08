package ru.news.tagil.composite;/**
 * Created by turbo_lover on 06.08.13.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ru.news.tagil.R;
import ru.news.tagil.utility.myPreferencesWorker;

public class compositeSettingsFonts extends RelativeLayout {

    final Context cnt;
    myPreferencesWorker pw;
    LinearLayout typefaceList,fontSizeList;
    public compositeSettingsFonts(Context context) {
        super(context);
        cnt = context;
        Initialize_Component();
        SetEventListeners();
    }

    public compositeSettingsFonts(Context context, AttributeSet attrs) {
        super(context, attrs);
        cnt = context;
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {

    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_settings_fonts, this);
        typefaceList = (LinearLayout) findViewById(R.id.font_type_linear);
        fontSizeList = (LinearLayout) findViewById(R.id.font_size_linear);
        pw = new myPreferencesWorker(getContext());
        SetFonts();
        SetFontSize();
    }

    private void SetFontSize() {
        for(int i = 0; i < fontSizeList.getChildCount(); i++ ) {
            final TextView child = (TextView) fontSizeList.getChildAt(i);
            if (child != null) {
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pw.set_typeface_size(child.getTextSize());
                        Toast.makeText(cnt,cnt.getString(R.string.message_font_size_changed),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void SetFonts() {
        for(int i = 0; i < typefaceList.getChildCount(); i++ ) {
            TextView child = (TextView) typefaceList.getChildAt(i);
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/font"+(i+1)+".ttf");
            final int number = i+1;

           if(child != null) child.setTypeface(tf);

            if (child != null) {
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pw.set_typeface("fonts/font" + number + ".ttf");
                        Toast.makeText(cnt,cnt.getString(R.string.message_font_type_changed),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}