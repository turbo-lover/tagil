package ru.news.tagil.activity;

import android.app.Activity;
import android.os.Bundle;
import ru.news.tagil.R;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityAds extends Activity {

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

    }
}