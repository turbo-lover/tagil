package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeHeaderAds;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myScrollView;
import ru.news.tagil.utility.onScrollViewChangedListener;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityAds extends Activity implements onScrollViewChangedListener, View.OnClickListener {
    LinearLayout ads_content;
    myScrollView ads_scroller;
    compositeHeaderAds ads_header;
    LinearLayout ads_header_ll;
    final int GET_ADS_COUNT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        Initialize_Component();
        SetEventListeners();
        SetCompositeElements();
    }

    private void getAds() {
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject send_data = new JSONObject();
        try{

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_ADS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    private void SetCompositeElements() {
        try {
            ads_header = new compositeHeaderAds(this,"0","2");
            ads_header_ll.addView(ads_header);
        } catch (Exception e) {
            e.printStackTrace(); }
    }

    private void SetEventListeners() {
        ads_scroller.setListener(this);
    }

    private void Initialize_Component() {
        ads_content = (LinearLayout) findViewById(R.id.ads_content_holder);
        ads_scroller = (myScrollView) findViewById(R.id.ads_scroller);
        ads_header_ll = (LinearLayout) findViewById(R.id.include);
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void onClick(View view) {

    }
}