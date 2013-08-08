package ru.news.tagil.composite;/**
 * Created by turbo_lover on 06.08.13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.utility.simpleListenerInterface;

public class compositeSettingsContent extends RelativeLayout implements View.OnClickListener {
    simpleListenerInterface listeners = null;
    TextView toFontsSettings,toFaq;
    public compositeSettingsContent(Context context) {
        super(context);
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {
        toFontsSettings.setOnClickListener(this);

    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_setting_content, this);

        toFontsSettings = (TextView) findViewById(R.id.setting_content_font_setting);
        toFaq = (TextView) findViewById(R.id.setting_content_FAQ);
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
}