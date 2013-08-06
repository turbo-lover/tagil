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
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by Alexander on 30.07.13.
 */
public class activityReadAds extends mainFrameJsonActivity {
    private compositeHeaderSimple h_simple;
    private compositeAdsContent content;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Set(Get(CreateJsonForGet()));
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        scriptAddress = getString(R.string.getAdvertTextUrl);
        i = getIntent();
        h_simple = new compositeHeaderSimple(this);
        content = new compositeAdsContent(this);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.advertText));
        header.addView(h_simple);
        container.addView(content);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("id_advert",i.getStringExtra("id_advert"));
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    public void Set(JSONObject jsonObject) {
        try {
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return ;
            }
            content.Set(i.getStringExtra("title"),jsonObject.getString("result"),(Bitmap)i.getParcelableExtra("img"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
