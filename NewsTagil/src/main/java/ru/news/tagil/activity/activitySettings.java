package ru.news.tagil.activity;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeSettingsContent;
import ru.news.tagil.composite.compositeSettingsFonts;
import ru.news.tagil.utility.mainFrameActivity;
import ru.news.tagil.composite.compositeSettingFaq;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.onClickInHeaderListener;
import ru.news.tagil.utility.simpleListenerInterface;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class activitySettings extends mainFrameJsonActivity implements onClickInHeaderListener, simpleListenerInterface {

    compositeFirstButton firstButton;
    compositeHeaderSimple headerSimple;
    compositeSettingsFonts settingsFonts;
    compositeSettingsContent settingsContent;

    @Override
    protected void SetCompositeElements() {
        super.SetCompositeElements();
        headerSimple.Set(getString(R.string.settingsText));
        headerSimple.UpdateWeather(weatherToday, weatherTomorow);
        headerSimple.SetUpdateButtonVisibility(false);
        footer.addView(firstButton);
        header.addView(headerSimple);
        container.addView(settingsContent);
    }

    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        headerSimple.SetHeaderButtonsListener(this);
        settingsContent.SetListener(this);
    }

    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        firstButton = new compositeFirstButton(this);
        firstButton.setLayoutParams(lp);
        scriptAddress = getString(R.string.getFaqUrl);
        headerSimple = new compositeHeaderSimple(this);
        settingsFonts = new compositeSettingsFonts(this);
        settingsContent= new compositeSettingsContent(this);
    }

    @Override
    public void BackButtonClicks() {
        View v = container.getChildAt(0);
        if (v.equals(settingsContent)) {
            finish();
            return;
        }
        if(v.equals(settingsFonts)) {
            SetSettingContentView();
        }
        if(v.getClass().equals(compositeSettingFaq.class)) {
            SetSettingContentView();
        }
    }

    private void SetSettingFontView() {
        ClearContainer();
        container.addView(settingsFonts);
    }
    private void SetSettingContentView() {
        ClearContainer();
        container.addView(settingsContent);
    }
    private void SetSettingFAQ() {
        ClearContainer();
        try{
            JSONObject jo = Get(new JSONObject());
            if(jo.getString("status").equals("ok")) {
                JSONArray arr = jo.getJSONArray("result");
                for (int i=0; i< arr.length(); i++) {
                    JSONObject j = arr.getJSONObject(i);
                    compositeSettingFaq settingsFaq = new compositeSettingFaq(this);
                    settingsFaq.Set(j.getString("q"),j.getString("a"),(i+1)+". "+getString(R.string.questionText),
                            (i+1)+". "+getString(R.string.answerText) );
                    container.addView(settingsFaq);
                }
            }
        } catch (Exception ex) {
            Log.d("SetSettingFAQ_Exception",ex.toString());
        }
    }

    @Override
    public void toFonts() {
        SetSettingFontView();
    }

    @Override
    public void toFAQ() {
        SetSettingFAQ();
    }
}
