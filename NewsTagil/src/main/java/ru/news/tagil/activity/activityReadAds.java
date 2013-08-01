package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeAdsContent;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by Alexander on 30.07.13.
 */
public class activityReadAds extends Activity {
    private LinearLayout headerLL,contentLL;
    private myPreferencesWorker preferencesWorker;
    private compositeHeaderSimple header;
    private compositeAdsContent content;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_ads);
        i = getIntent();
        Initialize_Component();
        SetEventListeners();
        SetCompositeElements(castText(getText(i.getStringExtra("id_advert"))));
    }

    private JSONObject getText(String id_advert) {
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("id_advert",id_advert);
            worker.execute(jo,getString(R.string.serverAddress) +getString(R.string.getAdvertText) );
            return  worker.get();
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("Exception",e.getMessage());
        }
        return null;
    }

    private String castText(JSONObject jsonObject) {
        try {
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return "";
            }
            return jsonObject.getString("result");
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.printStackTrace();
        }
        return "";
    }

    private void SetCompositeElements(String text) {
        header = new compositeHeaderSimple(this);
        header.Set(getString(R.string.advertText));
        content = new compositeAdsContent(this);
        content.Set(i.getStringExtra("title"),text,(Bitmap)i.getParcelableExtra("img"));
        contentLL.addView(content);
        headerLL.addView(header);
    }

    private void SetEventListeners() {

    }

    private void Initialize_Component() {
        headerLL = (LinearLayout) findViewById(R.id.activity_read_ads_header);
        contentLL = (LinearLayout) findViewById(R.id.activity_read_ads_content);
        preferencesWorker = new myPreferencesWorker(this);
    }
}
