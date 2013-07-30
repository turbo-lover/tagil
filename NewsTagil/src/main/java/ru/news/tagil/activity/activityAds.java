package ru.news.tagil.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeAdsContent;
import ru.news.tagil.composite.compositeAdsSelector;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeHeaderAds;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;
import ru.news.tagil.utility.myScrollView;
import ru.news.tagil.utility.onScrollViewChangedListener;
import ru.news.tagil.utility.onUpdateClickListener;
import ru.news.tagil.utility.updateListActivity;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityAds extends Activity implements onScrollViewChangedListener, View.OnClickListener,updateListActivity,onUpdateClickListener {
    private LinearLayout ads_content,ads_header_ll,ads_selector_ll;
    private myScrollView ads_scroller;
    private compositeHeaderAds ads_header;
    private compositeAdsSelector ads_selector;
    private myPreferencesWorker preferencesWorker;
    private int totalCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        Initialize_Component();
        SetEventListeners();
        SetCompositeElements();
        Get();
    }

    private void SetCompositeElements() {
        try {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ads_header_ll.addView(ads_header);
            ads_selector_ll.addView(ads_selector,p);
        } catch (Exception e) {
            e.printStackTrace(); }
    }

    private void SetEventListeners() {
        ads_scroller.setListener(this);
    }

    private void Initialize_Component() {
        preferencesWorker = new myPreferencesWorker(this);
        ads_header = new compositeHeaderAds(this,"0","2");
        ads_selector = new compositeAdsSelector(this);
        ads_content = (LinearLayout) findViewById(R.id.ads_content_holder);
        ads_scroller = (myScrollView) findViewById(R.id.ads_scroller);
        ads_header_ll = (LinearLayout) findViewById(R.id.activity_ads_include);
        ads_selector_ll = (LinearLayout) findViewById(R.id.ads_selector);
        totalCount = GetTotalCount();
     }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {
        Get();
    }

    @Override
    public void onClick(View view) {
        /*
        Intent i = new Intent(this,activityNewsContent.class);
        compositeTapePreview c = (compositeTapePreview) view;
        i.putExtra("time",c.getTime());
        i.putExtra("date",c.getDate());
        i.putExtra("header",c.getHeader());
        i.putExtra("id_news",(String) c.getTag());
        startActivity(i);
        */
    }

    @Override
    public void Set(JSONObject jsonObject, boolean insertAtStart) {
        try{
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray arr = jsonObject.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                byte[] e = obj.getString("advert_image").getBytes();
                byte[] imgbyte = Base64.decode(e, 0);
                Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
                compositeAdsContent compositeAdsPreview = new compositeAdsContent(this,obj.getString("header"),
                        obj.getString("login"),obj.getString("pub_date"),bmp);
                compositeAdsPreview.setOnClickListener(this);
                compositeAdsPreview.setTag(obj.getString("id"));
                if(insertAtStart){
                    ads_content.addView(compositeAdsPreview,0+i);
                } else {
                    ads_content.addView(compositeAdsPreview); }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ADD_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public void Get() {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        if(ads_content.getChildCount() == totalCount) {
            return; }
        try{
            jo = new JSONObject();
            jo.put("login",preferencesWorker.get_login());
            jo.put("adverts_count",COUNT);
            String send_time = null;
            if(ads_content.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeAdsContent c = (compositeAdsContent) ads_content.getChildAt(ads_content.getChildCount() - 1);
                send_time = c.getDate();
            }
            jo.put("time",send_time);
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getAdvertsHeadersUrl));
            Set(asyncTaskWorker.get(), false);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_ADS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public void GetNew() {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try {
            jo = new JSONObject();
            jo.put("login",preferencesWorker.get_login());
            String send_time = null;
            if(ads_content.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeAdsContent c = (compositeAdsContent) ads_content.getChildAt(0);
                send_time = c.getDate();
            }
            jo.put("time",send_time);
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getAdvertsHeadersUrl));
            Set(asyncTaskWorker.get(),true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_ADS_NEW_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public int GetTotalCount() {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try{
            jo = new JSONObject();
            jo.put("table_name","adverts");
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
            jo = asyncTaskWorker.get();
            return Integer.parseInt(jo.getString("result"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_TOTAL_ADVERTS_COUNT_Exception", ex.getMessage() + "____________" + ex.toString());
        }
        return 0;
    }

    @Override
    public void UpdateButtonClicks() {
        int new_count = GetTotalCount();
        if(new_count > totalCount) {
            totalCount = new_count;
            GetNew();
        }
    }
}