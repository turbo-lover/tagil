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
    protected myPreferencesWorker preferencesWorker;
    protected RelativeLayout header,holder,footer,selector;

    // This section MUST be overriden
    protected void SetEventListeners() {}
    protected void SetCompositeElements() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
