package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityAds extends Activity {
    TextView headerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {

    }

    private void Initialize_Component() {
        headerText = (TextView) findViewById(R.id.header_ads_link);
        Intent i = getIntent();
        headerText.setText(i.getStringExtra("HeaderText"));
    }
}