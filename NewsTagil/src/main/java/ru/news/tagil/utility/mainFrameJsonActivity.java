package ru.news.tagil.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.activity.activityLogin;

/**
 * Created by Alexander on 05.08.13.
 */
public class mainFrameJsonActivity extends Activity implements onClickInHeaderListener,jsonActivity {
    protected static final int NEED_AUTHORIZATION = 0;
    protected LinearLayout container;
    protected myScrollView scrollView;
    protected myPreferencesWorker preferencesWorker;
    protected RelativeLayout header,holder,footer,selector;
    protected String scriptAddress;
    public static boolean is_authorized = false;
    protected static String weatherToday = "";
    protected static String weatherTomorow = "";


    // This section MUST be overriden
    protected JSONObject CreateJsonForGet() { return null; }
    @Override
    public void Set(JSONObject jsonObject) { }
    protected void SetEventListeners() {}
    protected void SetCompositeElements() {}
    @Override
    public void UpdateButtonClicks() {}
    @Override
    public void SearchButtonClicks(String txt) {}

    @Override
    public void UpdateWeather() {}

    @Override
    public void BackButtonClicks() {
        this.finish();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NEED_AUTHORIZATION) {
                is_authorized = true;
            }
        }
    }
    @Override
    public void FinishedRequest(JSONObject returned, jsonActivityMode mode) {
        Set(returned);
    }

    public void LogIn() {
        Intent i  = new Intent(this,activityLogin.class);
        startActivityForResult(i,NEED_AUTHORIZATION);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if(weatherToday.isEmpty() && weatherTomorow.isEmpty()) {
            new myAsyncTaskWorker(this,jsonActivityMode.WEATHER).execute(new JSONObject(),
                    getString(R.string.serverAddress)+getString(R.string.getWeatherUrl)); }
        InitializeComponent();
        SetCompositeElements();
        SetEventListeners();
    }

    public void SetWeather(JSONObject jsonObject) {
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
                JSONObject obj =  arr.getJSONObject(i);
                JSONArray temp_from = obj.getJSONArray("temp_from");
                JSONArray temp_to = obj.getJSONArray("temp_to");
                if(i == 0) {
                    weatherToday = temp_from.getString(1)+"-"+temp_to.getString(1);
                } else {
                    weatherTomorow = temp_from.getString(1)+"-"+temp_to.getString(1);
                }
            }
            this.UpdateWeather();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("SetWeather_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }
    protected void InitializeComponent() {
        setContentView(R.layout.main_frame);
        preferencesWorker = new myPreferencesWorker(this);
        header = (RelativeLayout) findViewById(R.id.main_frame_header);
        holder = (RelativeLayout) findViewById(R.id.main_frame_holder);
        scrollView = (myScrollView) findViewById(R.id.main_frame_scroll);
        footer = (RelativeLayout) findViewById(R.id.main_frame_footer);
        selector = (RelativeLayout) findViewById(R.id.main_frame_selector);
        container = (LinearLayout) findViewById(R.id.main_frame_content_holder);
    }

    protected void ClearContainer() {
        container.removeAllViews();
    }
}
