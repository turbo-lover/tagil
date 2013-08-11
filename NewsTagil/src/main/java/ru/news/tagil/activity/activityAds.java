package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeAdsPreview;
import ru.news.tagil.composite.compositeAdsSelector;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.ScrollUpdateActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityAds extends ScrollUpdateActivity implements View.OnClickListener {
    private compositeHeaderSimple ads_header;
    private compositeAdsSelector ads_selector;
    @Override
    protected void SetCompositeElements() {
        ads_header.Set(getString(R.string.advertText));
       LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
               LinearLayout.LayoutParams.WRAP_CONTENT);
        selector.addView(ads_selector,p);
        header.addView(ads_header);

    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        ads_header.SetHeaderButtonsListener(this);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        ads_header = new compositeHeaderSimple(this);
        ads_selector = new compositeAdsSelector(this);
        tableName = "adverts";
        scriptAddress = getString(R.string.getAdvertsHeadersUrl);
        totalCount = GetTotalCount(null,null);
     }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityReadAds.class);
        compositeAdsPreview c = (compositeAdsPreview) view;
        i.putExtra("title",c.getTitle());
        i.putExtra("img",c.getImg());
        i.putExtra("id_advert",(String) c.getTag());
        startActivity(i);
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj){
        compositeAdsPreview adsPreview = new compositeAdsPreview(this);
        try {
            byte[] e = obj.getString("advert_image").getBytes();
            byte[] imgbyte = Base64.decode(e, 0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            adsPreview.Set(obj.getString("header"),obj.getString("login"),obj.getString("pub_date"),bmp);
            adsPreview.setOnClickListener(this);
            adsPreview.setTag(obj.getString("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return adsPreview;
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("adverts_count",GET_COUNT);
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeAdsPreview c = (compositeAdsPreview) container.getChildAt(container.getChildCount() - 1);
                send_time = c.getDate();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected JSONObject CreateJsonForGetNew() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeAdsPreview c = (compositeAdsPreview) container.getChildAt(0);
                send_time = c.getDate();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
}