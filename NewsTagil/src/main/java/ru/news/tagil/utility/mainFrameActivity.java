package ru.news.tagil.utility;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ru.news.tagil.R;

/**
 * Created by Alexander on 05.08.13.
 */
public class mainFrameActivity extends Activity {
    protected LinearLayout container;
    protected myScrollView scrollView;
    protected RelativeLayout header,footer,selector;
    protected myPreferencesWorker preferencesWorker;

    // This section MUST be overriden
    private void SetEventListeners() {}
    private void SetCompositeElements() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        SetCompositeElements();
        SetEventListeners();
    }

    private void InitializeComponent() {
        setContentView(R.layout.main_frame);
        preferencesWorker = new myPreferencesWorker(this);
        header = (RelativeLayout) findViewById(R.id.main_frame_header);
        scrollView = (myScrollView) findViewById(R.id.main_frame_scroll);
        footer = (RelativeLayout) findViewById(R.id.main_frame_footer);
        selector = (RelativeLayout) findViewById(R.id.main_frame_selector);
        container = (LinearLayout) findViewById(R.id.main_frame_content_holder);
    }
}
