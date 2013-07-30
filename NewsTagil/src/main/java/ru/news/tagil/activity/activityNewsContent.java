package ru.news.tagil.activity;
//TODO на обновлении проверять не появились ли новые новости
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeTapeContent;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityNewsContent extends Activity  {
    private ScrollView main_scroller;
    private myPreferencesWorker preferences_worker;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);
        i = getIntent();
        main_scroller = (ScrollView) findViewById(R.id.read_post_scroller);
        preferences_worker = new myPreferencesWorker(this);
        SetContent(getTextAndImage());
    }

    private JSONObject getTextAndImage() {
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("id_news",i.getStringExtra("id_news"));
            sends_data.put("login",preferences_worker.get_login());
            sends_data.put("pass",preferences_worker.get_pass());
            worker.execute(sends_data,getString(R.string.serverAddress)+getString(R.string.getNewsUrl));
            return worker.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_TEXT_AND_IMAGE_Exception", ex.getMessage() + "____________" + ex.toString());
        }
        return null;
    }

    private void SetContent(JSONObject obj) {
        try {
            JSONObject result = obj.getJSONObject("result");
            byte[] e = result.getString("news_image").getBytes();
            byte[] imgbyte = Base64.decode(e,0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            compositeTapeContent t = new compositeTapeContent(this,i.getStringExtra("header"),i.getStringExtra("time")
                    ,i.getStringExtra("date"),result.getString("news_text"),bmp);
            main_scroller.addView(t);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("SET_CONTENT_NEWS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }
}
