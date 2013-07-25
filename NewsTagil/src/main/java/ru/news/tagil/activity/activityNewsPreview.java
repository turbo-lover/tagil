package ru.news.tagil.activity;
//TODO add refresh button action
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;
import ru.news.tagil.utility.myScrollView;
import ru.news.tagil.utility.onScrollViewChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexander on 15.07.13.
 */
public class activityNewsPreview extends Activity implements onScrollViewChangedListener,View.OnClickListener {
    private myPreferencesWorker preferences_worker;
    private myScrollView scroller;
    private compositeFirstButton cfb;
    private LinearLayout ll, navigation_footter;
    private int total_news_count;
    private final int GET_NEWS_COUNT = 10; // Количество новостей подгружаемых за раз.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
        Initialize_Variable();
        SetCompositeElements();
        getNewsHeaders();
    }

    private void SetCompositeElements() {
        try {
            cfb = new compositeFirstButton(this);
            navigation_footter.addView(cfb);
        } catch (Exception e) {
            e.printStackTrace(); }
    }

    private void Initialize_Variable() {
        scroller = (myScrollView) findViewById(R.id.news_headers_scroller);
        ll = (LinearLayout) findViewById(R.id.news_headers_content_holder);
        navigation_footter = (LinearLayout) findViewById(R.id.tape_first_nav);
        preferences_worker = new myPreferencesWorker(this);
        total_news_count = 0;
        scroller.setListener(this);
    }

    private void getNewsHeaders() {
        if(ll.getChildCount() == total_news_count && total_news_count != 0) {
            return; }
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("news_count",GET_NEWS_COUNT);
            String send_time = null;
            if(ll.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                sends_data.put("total_news_count",true);
            } else {
                compositeTapePreview c = (compositeTapePreview) ll.getChildAt(ll.getChildCount() - 1);
                send_time = c.getDateTime();
                sends_data.put("total_news_count",false);
            }
            sends_data.put("time",send_time);
            worker.execute(sends_data,getResources().getString(R.string.serverAddress)+getResources().getString(R.string.getNewsHeadersUrl));
            JSONObject resivedJObj = worker.get();
            addHeaders(resivedJObj);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_NEWS_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    private void addHeaders(JSONObject jo) {
        try{
            if(jo.getString("status").equals("error")){
                Toast.makeText(this,jo.getString("errormsg"),Toast.LENGTH_SHORT).show();
                return;
            }
            if(jo.has("total_news_count")) {
                total_news_count = Integer.parseInt(jo.getString("total_news_count")); }
            JSONArray arr = jo.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                String[] s = obj.getString("pub_time").split(" ");
                compositeTapePreview compositeTapePreview = new compositeTapePreview(this,s[0],
                        s[1],obj.getString("header"));
                compositeTapePreview.setOnClickListener(this);
                compositeTapePreview.setTag(obj.getString("id"));
                ll.addView(compositeTapePreview);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ADD_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {
        getNewsHeaders();
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
}
