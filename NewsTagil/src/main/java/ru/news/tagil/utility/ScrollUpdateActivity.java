package ru.news.tagil.utility;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.activity.activityMessages;

/**
 * Created by Alexander on 01.08.13.
 */
public class ScrollUpdateActivity extends mainFrameJsonActivity implements updateListActivity,onScrollViewChangedListener {
    protected int totalCount = -1;
    protected String tableName; //Must be set in Initialize method
    protected String searchStr;
    protected boolean needAutoUpdate = false;  //Must be set in onCreate method
    protected boolean isMessages;

    // This method MUST be overriden
    protected View CreateViewToAdd(JSONObject obj) { return null; }
    protected JSONObject CreateJsonForGetNew() { return null; }

    protected boolean IsConnectedToWiFI() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isMessages) scrollView.setEventEnable(false);
        new myAsyncTaskWorker(this,jsonActivityMode.GET_NEW).execute(CreateJsonForGetNew(),
                getString(R.string.serverAddress)+scriptAddress);
        new CountDownTimer(1000*60*10, 1000*10*2) {
            @Override
            public void onTick(long l) {
                UpdateButtonClicks();
            }
            @Override
            public void onFinish() {
                start();
            }
        }.start();
    }
    @Override
    public void FinishedRequest(JSONObject returned,jsonActivityMode mode) {
        try{
            switch (mode) {
                case GET:
                    Set(returned,isMessages);
                    if(isMessages) {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                if(container.getChildCount() > GET_COUNT) {
                                    View v = container.getChildAt(GET_COUNT);
                                    int t = v.getTop();
                                    scrollView.scrollTo(0,t);
                                } else {
                                    scrollView.fullScroll(scrollView.FOCUS_DOWN);
                                    scrollView.setEventEnable(true);
                                }
                            }
                        });
                    }
                    break;
                case GET_NEW:
                    Set(returned,!isMessages);
                    if(isMessages) {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
                            }
                        });
                    }
                    break;
                case COUNT:
                    totalCount = Integer.parseInt(returned.getString("result"));
                    new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJsonForGet(),
                            getString(R.string.serverAddress)+scriptAddress);
                    break;
                case COUNT_NEW:
                    if(Integer.parseInt(returned.getString("result")) > totalCount && totalCount != -1) {
                        totalCount = Integer.parseInt(returned.getString("result"));
                        new myAsyncTaskWorker(this,jsonActivityMode.GET_NEW).execute(CreateJsonForGetNew(),
                                getString(R.string.serverAddress)+scriptAddress);
                    }
                    break;
                case WEATHER:
                    SetWeather(returned);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("FinishedRequest_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        isMessages = this.getClass().equals(activityMessages.class);
    }
    @Override
    protected void SetEventListeners() {
        scrollView.setListener(this);
    }

    @Override
    public void Set(JSONObject jsonObject, boolean insertAtStart) {
        if(jsonObject == null) {
            return;
        }
        try{
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray arr = jsonObject.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject((isMessages)?arr.length() -i  -1 :i);
                View v = CreateViewToAdd(obj);
                if(insertAtStart){
                    container.addView(v,0+i);
                } else {
                    container.addView(v);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("SET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public JSONObject CreateJsonForGetTotalCount(String extra1,String extra2) {
        JSONObject jo;
        try{
            jo = new JSONObject();
            jo.put("table_name",tableName);
            if(extra1 != null) {
                jo.put("extra",extra1);
                if(tableName == "messages") {
                    jo.put("extra2",extra2);
                }
            }
            return jo;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGetTotalCount_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return null;
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {
        if( container.getChildCount() == totalCount ) {
            return; }
        new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJsonForGet(),
                getString(R.string.serverAddress)+scriptAddress);
    }

    @Override
    public void onScrollHitTop(myScrollView myScrollView, int l, int t, int oldl, int oldt) { }

    @Override
    public void UpdateButtonClicks() {
        if(totalCount == -1) return;
        if(!needAutoUpdate) return;
        if(!(preferencesWorker.get_autoupdate_mode().equals(getString(R.string.autoapdateWiFi)) && IsConnectedToWiFI())) return;
        String extra1 = (tableName == "news"|| tableName == "adverts"|| tableName == "gavorite_news"|| tableName == "users" || tableName == "comments")?
                searchStr:preferencesWorker.get_login();
        String extra2 =  (tableName == "messages")?searchStr:null;
        new myAsyncTaskWorker(this,jsonActivityMode.COUNT_NEW).execute(CreateJsonForGetTotalCount(extra1,extra2),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
}
