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
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;
import ru.news.tagil.utility.myScrollView;
import ru.news.tagil.utility.onScrollViewChangedListener;
import ru.news.tagil.utility.onUpdateClickListener;
import ru.news.tagil.utility.updateListActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexander on 15.07.13.
 */
public class activityNewsPreview extends Activity implements onScrollViewChangedListener,View.OnClickListener,onUpdateClickListener,updateListActivity {
    private myScrollView scroller;
    private compositeFirstButton cfb;
    private compositeHeader compositeHeader;
    private LinearLayout ll, navigation_footter,tape_header;
    private int total_news_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
        Initialize_Variable();
        SetCompositeElements();
        SetEventListeners();
        Get();
    }

    private void SetEventListeners() {
        compositeHeader.SetUpdateListener(this);
        scroller.setListener(this);
    }

    private void SetCompositeElements() {
        navigation_footter.addView(cfb);
        tape_header.addView(compositeHeader);
    }

    private void Initialize_Variable() {
        scroller = (myScrollView) findViewById(R.id.news_headers_scroller);
        ll = (LinearLayout) findViewById(R.id.news_headers_content_holder);
        tape_header = (LinearLayout) findViewById(R.id.tape_header);
        navigation_footter = (LinearLayout) findViewById(R.id.tape_first_nav);
        cfb = new compositeFirstButton(this);
        compositeHeader = new compositeHeader(this,"0","2",getString(R.string.mainText),getString(R.string.contactText)
                ,getString(R.string.advertText));  //TODO допилить получение погоды
        total_news_count = GetTotalCount();
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {
        Get();
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
    public void UpdateButtonClicks() {
        int new_count = GetTotalCount();
        if(new_count > total_news_count) {
            total_news_count = new_count;
            GetNew();
        }
    }

    @Override
    public void Set(JSONObject jsonObject, boolean insertAtStart) {
        try{
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this,jsonObject.getString("errormsg"),Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray arr = jsonObject.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                String[] s = obj.getString("pub_time").split(" ");
                compositeTapePreview compositeTapePreview = new compositeTapePreview(this,s[0],
                        s[1],obj.getString("header"));
                compositeTapePreview.setOnClickListener(this);
                compositeTapePreview.setTag(obj.getString("id"));
                if(insertAtStart){
                    ll.addView(compositeTapePreview,0+i);
                } else {
                    ll.addView(compositeTapePreview); }
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
        if(ll.getChildCount() == total_news_count) {
            return; }
        try {
            jo = new JSONObject();
            jo.put("news_count",COUNT);
            String send_time = null;
            if(ll.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeTapePreview c = (compositeTapePreview) ll.getChildAt(ll.getChildCount() - 1);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getNewsHeadersUrl));
            Set(asyncTaskWorker.get(), false);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_NEWS_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public void GetNew() {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try {
            jo = new JSONObject();
            String send_time = null;
            if(ll.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeTapePreview c = (compositeTapePreview) ll.getChildAt(0);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getNewsHeadersUrl));
            Set(asyncTaskWorker.get(),true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_NEWS_HEADERS_Exception", ex.getMessage() + "____________" + ex.toString());
        }
    }

    @Override
    public int GetTotalCount() {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try{
            jo = new JSONObject();
            jo.put("table_name","news");
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
            jo = asyncTaskWorker.get();
            return Integer.parseInt(jo.getString("result"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_TOTAL_NEWS_COUNT_Exception", ex.getMessage() + "____________" + ex.toString());
        }
        return 0;
    }
}
