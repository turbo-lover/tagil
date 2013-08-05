package ru.news.tagil.activity;

import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.mainFrameActivity;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class activitySettings extends mainFrameActivity {

    compositeFirstButton firstButton;
    compositeHeaderSimple headerSimple;
    @Override
    protected void SetCompositeElements() {
        super.SetCompositeElements();
        footer.addView(firstButton);
        header.addView(headerSimple);

    }

    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        firstButton = new compositeFirstButton(this);
        headerSimple = new compositeHeaderSimple(this);
    }
}
