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
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeTapeContent;
import ru.news.tagil.utility.jsonActivity;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityNewsContent extends mainFrameJsonActivity {
    private Intent i;
    private compositeTapeContent t;
    private compositeHeaderSimple h_simple;
    //private compositeSecondButton csb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Set(Get(CreateJsonForGet()));
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        i = getIntent();
        h_simple = new compositeHeaderSimple(this);
        t = new compositeTapeContent(this);
        scriptAddress = getString(R.string.getNewsUrl);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.News));
        container.addView(t);
        header.addView(h_simple);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("id_news",i.getStringExtra("id_news"));
            sends_data.put("login",preferencesWorker.get_login());
            sends_data.put("pass",preferencesWorker.get_pass());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return sends_data;
    }
    @Override
    public void Set(JSONObject obj) {
        try {
            JSONObject result = obj.getJSONObject("result");
            byte[] e = result.getString("news_image").getBytes();
            byte[] imgbyte = Base64.decode(e,0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            t.Set(i.getStringExtra("header"),i.getStringExtra("time"),i.getStringExtra("date"),
                    result.getString("news_text"),bmp);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Set_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }
}
