package ru.news.tagil.composite;/**
 * Created by turbo_lover on 19.07.13.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.activity.activityAds;
import ru.news.tagil.activity.activityContact;
import ru.news.tagil.activity.activityNewsPreview;
import ru.news.tagil.utility.onUpdateClickListener;

public class compositeHeader extends RelativeLayout implements View.OnClickListener {
    private final String TAG = "compositeHeader";

    private Button main, contact, ads, update;
    private TextView tomorrow, now;
    private onUpdateClickListener listener = null;

    public compositeHeader(Context context,String weather_now,String weather_tomorrow) {
        super(context);
        Initialize_Component();
        SetEventListeners();
        UpdateWeather(weather_now,weather_tomorrow);
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header,this);

        main = (Button) findViewById(R.id.composite_header_main);
        contact = (Button) findViewById(R.id.composite_header_contact );
        ads = (Button) findViewById(R.id.composite_header_ads );
        update = (Button) findViewById(R.id.composite_header_update );
        tomorrow = (TextView) findViewById(R.id.composite_header_tomorrow_content);
        now = (TextView) findViewById(R.id.composite_header_now_content);
    }

    private void SetEventListeners() {
        main.setOnClickListener(this);
        contact.setOnClickListener(this);
        ads.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    public void SetUpdateListener(onUpdateClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_header_main :
               try {
                   Intent intent = new Intent(getContext(), activityNewsPreview.class);
                   getContext().startActivity(intent);
               } catch (Exception e) {
                   Log.e(TAG, "Exception");
                   e.printStackTrace();
               }
            break;
            case R.id.composite_header_contact:
                try {
                    Intent intent = new Intent(getContext(), activityContact.class);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Exception");
                    e.printStackTrace();
                }
            break;
            case R.id.composite_header_ads :
                try {
                    Intent intent = new Intent(getContext(), activityAds.class);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Exception");
                    e.printStackTrace();
                }
            break;
            case R.id.composite_header_update :
                if(listener!= null) listener.UpdateButtonClicks();
            break;

        }
    }

    public void UpdateWeather(String now, String tomorrow)
    {
        // alt + 0176 -> значек градуса
        this.now.setText(now+" C°");
        this.tomorrow.setText(tomorrow + " C°");
    }
}