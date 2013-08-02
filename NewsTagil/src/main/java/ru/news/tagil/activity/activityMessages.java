package ru.news.tagil.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityMessages extends Activity {
    private LinearLayout navigation, message, header;
    private compositeFirstButton cfb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage_list);
        Initialize_Component();
        SetEventListeners();
        SetCompositeElement();
    }

    private void Initialize_Component() {
        navigation = (LinearLayout) findViewById(R.id.message_first_nav);
        header = (LinearLayout) findViewById(R.id.message_header);
        message = (LinearLayout) findViewById(R.id.message_list);

        navigation.removeAllViews();
        header.removeAllViews();
        message.removeAllViews();
    }

    private void SetEventListeners() {

    }

    private void SetCompositeElement() {
        cfb = new compositeFirstButton(this);
        navigation.addView(cfb);
    }
}
