package ru.news.tagil.activity;

import android.view.View;
import android.widget.RelativeLayout;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeSettingsContent;
import ru.news.tagil.composite.compositeSettingsFonts;
import ru.news.tagil.utility.mainFrameActivity;
import ru.news.tagil.utility.onClickInHeaderListener;
import ru.news.tagil.utility.simpleListenerInterface;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class activitySettings extends mainFrameActivity implements onClickInHeaderListener, simpleListenerInterface {

    compositeFirstButton firstButton;
    compositeHeaderSimple headerSimple;
    compositeSettingsFonts settingsFonts;
    compositeSettingsContent settingsContent;

    @Override
    protected void SetCompositeElements() {
        super.SetCompositeElements();

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

        headerSimple = new compositeHeaderSimple(this);
        settingsFonts = new compositeSettingsFonts(this);
        settingsContent= new compositeSettingsContent(this);
    }

    @Override
    public void UpdateButtonClicks() {

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
        /*if(v.equals(settingsFonts)) { // todo запилить faq
            SetSettingContentView();
        }*/
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
        container.addView(settingsContent);// todo запилить faq
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
