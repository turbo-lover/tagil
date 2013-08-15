package ru.news.tagil.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ru.news.tagil.R;
import ru.news.tagil.activity.activityLogin;

/**
 * Created by Alexander on 05.08.13.
 */
public class mainFrameActivity extends Activity implements onClickInHeaderListener {
    protected static final int NEED_AUTHORIZATION = 0;
    protected LinearLayout container;
    protected myScrollView scrollView;
    protected myPreferencesWorker preferencesWorker;
    protected RelativeLayout header,holder,footer,selector;
    public static boolean is_authorized = false;
    protected static String weatherToday = "";
    protected static String weatherTomorow = "";

    // This section MUST be overriden
    protected void SetEventListeners() {}
    protected void SetCompositeElements() {}
    @Override
    public void UpdateButtonClicks() {}
    @Override
    public void BackButtonClicks() {
        this.finish();
    }
    @Override
    public void SearchButtonClicks(String txt) {}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NEED_AUTHORIZATION) {
                is_authorized = true;
            }
        }
    }

    public void LogIn() {
        Intent i  = new Intent(this,activityLogin.class);
        startActivityForResult(i,NEED_AUTHORIZATION);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if(i.getExtras() != null && !i.getExtras().isEmpty()) {
            if(i.hasExtra("weather_today")){
                weatherToday = i.getStringExtra("weather_today");
                weatherTomorow = i.getStringExtra("weather_tomorrow");
            }
        }
        InitializeComponent();
        SetCompositeElements();
        SetEventListeners();
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
