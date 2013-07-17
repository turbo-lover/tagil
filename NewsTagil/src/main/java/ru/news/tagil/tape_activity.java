package ru.news.tagil;

import android.app.Activity;
import android.content.Context;
import android.location.GpsStatus;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexander on 15.07.13.
 */
public class tape_activity extends Activity implements onScrollViewChangedListener {
    My_Preferences_Worker preferences_worker;
    MyScrollView scroller;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
        scroller = (MyScrollView) findViewById(R.id.news_headers_scroller);
        ll = (LinearLayout) findViewById(R.id.content_holder);
        scroller.setListener(this);
        preferences_worker = new My_Preferences_Worker(this);
        getNewsHeaders();
    }

    private void getNewsHeaders() {
        My_AsyncTask_Worker worker = new My_AsyncTask_Worker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("news_count",10);
            String send_time = null;
            if(ll.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                Composite_tape_activity c = (Composite_tape_activity) ll.getChildAt(ll.getChildCount() - 1);
                send_time = c.getTime();
            }
            sends_data.put("time",send_time);
            worker.execute(sends_data,getResources().getString(R.string.serverAddress)+getResources().getString(R.string.getNewsHeadersUrl));
            addHeaders(worker.get());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_NEWS_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    private void addHeaders(JSONObject jo) {
        try{
            if(jo.getString("status") == "error"){
                Toast.makeText(this,jo.getString("errormsg"),Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray arr = jo.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                String[] s = obj.getString("pub_time").split(" ");
                Composite_tape_activity composite_tape_activity = new Composite_tape_activity(this,s[0],
                        s[1],obj.getString("header"),"SOME RANDOM TEXT");
                ll.addView(composite_tape_activity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ADD_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public void onScrollHitBottom(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        getNewsHeaders();
    }
}
