package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeTapeContent;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityNewsContent extends Activity  {
    ScrollView main_scroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);
        Intent i = getIntent();
        main_scroller = (ScrollView) findViewById(R.id.read_post_scroller);
        compositeTapeContent t = new compositeTapeContent(this,i.getStringExtra("header"),i.getStringExtra("time")
        ,i.getStringExtra("date"));
        main_scroller.addView(t);
    }
}
