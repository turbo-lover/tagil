package ru.news.tagil.composite;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 23.07.13.
 */
public class compositeHeaderAds extends RelativeLayout {
    private TextView tomorrow, now;
    public compositeHeaderAds(Context context,String weather_now,String weather_tomorrow) {
        super(context);
        Initialize_Component();
        SetEventListeners();
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header_ads,this);
    }
    private void SetEventListeners() {

    }
}
