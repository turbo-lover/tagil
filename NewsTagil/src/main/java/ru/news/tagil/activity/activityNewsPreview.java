package ru.news.tagil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.ScrollUpdateActivity;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.myAsyncTaskWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexander on 15.07.13.
 */
public class activityNewsPreview extends ScrollUpdateActivity implements View.OnClickListener{
    private compositeFirstButton cfb;
    protected compositeHeader h;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        needAutoUpdate = h.GetUpdateButtonVisibility();
    }

    @Override
    protected void onResume(){
        super.onResume();
        for (int i=0;i< container.getChildCount(); i++) {
            compositeTapePreview cp = (compositeTapePreview) container.getChildAt(i);
            cp.SetFont();
        }
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("news_count",GET_COUNT);
            if(!h.GetSearchString().isEmpty()) {
                jo.put("search_pattern",h.GetSearchString());
            }
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
            compositeTapePreview c = (compositeTapePreview) container.getChildAt(container.getChildCount() - 1);
            send_time = c.getDateTime();
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
            String send_time = null;
            if(!h.GetSearchString().isEmpty()) {
                jo.put("search_pattern",h.GetSearchString());
            }
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeTapePreview c = (compositeTapePreview) container.getChildAt(0);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeTapePreview tapePreview = new compositeTapePreview(this);
        try {
            String[] s = obj.getString("pub_time").split(" ");
            tapePreview.Set(s[0],s[1],obj.getString("header"),obj.getString("like_count"),obj.getString("text")+"...");
            tapePreview.setOnClickListener(this);
            tapePreview.setTag(obj.getString("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return tapePreview;
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        h.SetHeaderButtonsListener(this);
    }
    @Override
    protected void SetCompositeElements() {
        if(getClass().equals(activityUseful.class)) {
            h.Set(getString(R.string.usefulText),"","");
        } else {
            h.Set(getString(R.string.mainText),getString(R.string.contactText),getString(R.string.advertText));
        }
        h.UpdateWeather(weatherToday, weatherTomorow);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        footer.addView(cfb,p);
        header.addView(h);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        cfb = new compositeFirstButton(this);
        h = new compositeHeader(this);
        scriptAddress = getString(R.string.getNewsHeadersUrl);
        tableName =(getClass().equals(activityUseful.class))?"useful_news": "news";
        new myAsyncTaskWorker(this,jsonActivityMode.COUNT).execute(CreateJsonForGetTotalCount(null, null),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityNewsContent.class);
        compositeTapePreview c = (compositeTapePreview) view;
        i.putExtra("time",c.getTime());
        i.putExtra("date",c.getDate());
        i.putExtra("header",c.getHeader());
        i.putExtra("id_news",(String) c.getTag());
        startActivity(i);
    }
    @Override
    public void BackButtonClicks() {
        moveTaskToBack(true);
    }
    @Override
    public void UpdateWeather(){
        h.UpdateWeather(weatherToday, weatherTomorow);
    }

    @Override
    public void SearchButtonClicks(String txt) {
        this.ClearContainer();
        searchStr = txt;
        new myAsyncTaskWorker(this,jsonActivityMode.COUNT).execute(CreateJsonForGetTotalCount(txt,null),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
}
