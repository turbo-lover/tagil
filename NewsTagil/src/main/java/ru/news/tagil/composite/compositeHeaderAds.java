package ru.news.tagil.composite;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Alexander on 23.07.13.
 */
public class compositeHeaderAds extends RelativeLayout {
    private TextView tomorrow, now;
    public compositeHeaderAds(Context context,String weather_now,String weather_tomorrow) {
        super(context);
    }


}
