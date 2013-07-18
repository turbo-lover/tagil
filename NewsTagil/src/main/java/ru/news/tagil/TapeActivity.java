package ru.news.tagil;
//TODO ДОПИСАТЬ ДЕЙСТВИЕ НА РЕФРЕШ
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class TapeActivity extends Activity implements onScrollViewChangedListener,View.OnClickListener {
    My_Preferences_Worker preferences_worker;
    MyScrollView scroller;
    LinearLayout ll;
    int total_news_count;
    final int GET_NEWS_COUNT = 10; // Количество новостей подгружаемых за раз.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
        scroller = (MyScrollView) findViewById(R.id.news_headers_scroller);
        ll = (LinearLayout) findViewById(R.id.news_headers_content_holder);
        preferences_worker = new My_Preferences_Worker(this);
        total_news_count = 0;
        scroller.setListener(this);
        getNewsHeaders();
    }

    private void getNewsHeaders() {
        if(ll.getChildCount() == total_news_count && total_news_count != 0) {
            return; }
        My_AsyncTask_Worker worker = new My_AsyncTask_Worker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("news_count",GET_NEWS_COUNT);
            String send_time = null;
            if(ll.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                sends_data.put("total_news_count",true);
            } else {
                Composite_tape_activity c = (Composite_tape_activity) ll.getChildAt(ll.getChildCount() - 1);
                send_time = c.getTime();
                sends_data.put("total_news_count",false);
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
            if(jo.has("total_news_count")) {
                total_news_count = Integer.parseInt(jo.getString("total_news_count")); }
            JSONArray arr = jo.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                String[] s = obj.getString("pub_time").split(" ");
                Composite_tape_activity composite_tape_activity = new Composite_tape_activity(this,s[0],
                        s[1],obj.getString("header"),"SOME RANDOM TEXT");
                composite_tape_activity.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,ReadPostActivity.class);
        Composite_tape_activity c = (Composite_tape_activity) view;
        i.putExtra("publication_time",c.getTime());
        i.putExtra("header",c.getHeader());
        startActivity(i);
    }
}
