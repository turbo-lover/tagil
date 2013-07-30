package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.news.tagil.R;
import ru.news.tagil.activity.activityMakeAds;
import ru.news.tagil.activity.activityMyAds;

/**
 * Created by Alexander on 23.07.13.
 */
public class compositeAdsSelector extends LinearLayout implements View.OnClickListener {
    Button createNewAds,personalAds;
    public compositeAdsSelector(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.composite_ads_selector,this);
        Initialize_Component(v);
        SetEventListeners();
    }

    private void SetEventListeners() {
        createNewAds.setOnClickListener(this);
        personalAds.setOnClickListener(this);
    }

    private void Initialize_Component(View convertedView) {
        createNewAds = (Button) convertedView.findViewById(R.id.composite_ads_first_tab);
        personalAds = (Button) convertedView.findViewById(R.id.composite_ads_second_tab);
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId())
        {
            case R.id.composite_ads_first_tab:
                i = new Intent(getContext(),activityMakeAds.class);
                getContext().startActivity(i);
                break;
            case R.id.composite_ads_second_tab:
                i = new Intent(getContext(),activityMyAds.class);
                getContext().startActivity(i);
                break;
        }
    }
}
