package ru.news.tagil.composite;/**
 * Created by turbo_lover on 06.08.13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.utility.myPreferencesWorker;
import ru.news.tagil.utility.simpleListenerInterface;

public class compositeSettingsContent extends RelativeLayout implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    simpleListenerInterface listeners = null;
    TextView toFontsSettings,toFaq;
    RadioGroup group;
    myPreferencesWorker preferencesWorker;
    RadioButton btn1,btn2;
    public compositeSettingsContent(Context context) {
        super(context);
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {
        toFontsSettings.setOnClickListener(this);
        group.setOnCheckedChangeListener(this);
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_setting_content, this);
        preferencesWorker = new myPreferencesWorker(getContext());
        btn1 = (RadioButton) findViewById(R.id.setting_content_3G);
        btn2 = (RadioButton) findViewById(R.id.setting_content_wifi);
        group = (RadioGroup) findViewById(R.id.autoupdate_mode_selector);
        toFontsSettings = (TextView) findViewById(R.id.setting_content_font_setting);
        toFaq = (TextView) findViewById(R.id.setting_content_FAQ);
        if(preferencesWorker.get_autoupdate_mode().equals(getContext().getString(R.string.autoapdateWiFi))) {
            btn2.setChecked(true);
        } else {
            btn1.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_content_font_setting:
                if(listeners!=null) listeners.toFonts();
                break;
            case R.id.setting_content_FAQ:
                if(listeners!=null) listeners.toFAQ();
                break;
        }
    }
    public void SetListener(simpleListenerInterface listener)
    {
        listeners = listener;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        String s = getContext().getString((i == R.id.setting_content_3G) ? R.string.autoapdate3G : R.string.autoapdateWiFi);
        preferencesWorker.set_autoupdate_mode(s);
    }
}