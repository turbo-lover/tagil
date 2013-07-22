package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeTapeContent;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityNewsContent extends Activity  {
    ScrollView main_scroller;
    myPreferencesWorker preferences_worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);
        Intent i = getIntent();
        main_scroller = (ScrollView) findViewById(R.id.read_post_scroller);
        preferences_worker = new myPreferencesWorker(this);
        JSONObject obj = getTextAndImage(i);
        try {
            JSONObject result = obj.getJSONArray("result").getJSONObject(0);
            byte[] e = result.getString("news_image").getBytes();
            byte[] imgbyte = Base64.decode(e,0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            compositeTapeContent t = new compositeTapeContent(this,i.getStringExtra("header"),i.getStringExtra("time")
                    ,i.getStringExtra("date"),result.getString("news_text"),bmp);
            main_scroller.addView(t);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ACT_NEWS_CONTENT_ONCREATE_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    private JSONObject getTextAndImage(Intent intent) {
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("id_news",intent.getStringExtra("id_news"));
            sends_data.put("login",preferences_worker.get_login());
            worker.execute(sends_data,getResources().getString(R.string.serverAddress)+getResources().getString(R.string.getNewsUrl));
            return worker.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_TEXT_AND_IMAGE_Exception", ex.getMessage() + "____________" + ex.toString());
        }
        return null;
    }
}
