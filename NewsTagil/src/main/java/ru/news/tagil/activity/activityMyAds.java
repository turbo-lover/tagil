package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeAdsPreview;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.ScrollUpdateActivity;


/**
 * Created by Alexander on 23.07.13.
 */
public class activityMyAds extends ScrollUpdateActivity implements View.OnClickListener
{
    private compositeHeaderSimple ads_header;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Set(Get(CreateJsonForGetNew()),true);
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityReadAds.class);
        compositeAdsPreview c = (compositeAdsPreview) view;
        i.putExtra("title",c.getTitle());
        i.putExtra("img",c.getImg());
        i.putExtra("id_advert",(String) c.getTag());
        i.putExtra("login",preferencesWorker.get_login());
        startActivity(i);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("adverts_count",GET_COUNT);
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
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        ads_header = new compositeHeaderSimple(this);
        tableName = "my_adverts";
        scriptAddress = getString(R.string.getMyAdvertsHeadersUrl);
        totalCount = GetTotalCount(preferencesWorker.get_login(),null);
    }
    @Override
    protected void SetEventListeners() {
        ads_header.SetHeaderButtonsListener(this);
    }
    @Override
    protected void SetCompositeElements() {
        ads_header.Set(getString(R.string.myAdvertText));
        ads_header.UpdateWeather(weatherToday, weatherTomorow);
        header.addView(ads_header);
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj){
        compositeAdsPreview adsPreview = new compositeAdsPreview(this);
        try {
            byte[] e = obj.getString("advert_image").getBytes();
            byte[] imgbyte = Base64.decode(e, 0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            adsPreview.Set(obj.getString("header"),"",obj.getString("pub_date"),bmp,null);
            adsPreview.setOnClickListener(this);
            adsPreview.setTag(obj.getString("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return adsPreview;
    }

}
