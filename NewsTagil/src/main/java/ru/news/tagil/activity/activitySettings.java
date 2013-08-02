package ru.news.tagil.activity;

import android.app.Activity;
import android.os.Bundle;
import ru.news.tagil.R;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class activitySettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
    }

    private void InitializeComponent() {
        setContentView(R.layout.activity_setting);
    }

}
