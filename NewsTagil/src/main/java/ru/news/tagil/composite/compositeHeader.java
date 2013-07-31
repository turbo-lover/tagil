package ru.news.tagil.composite;
/**
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

    private Button firstButton, secondButton, thirdButton, updateButton, backButton;
    private TextView weather_tommorow, weather_today;
    private onUpdateClickListener listener = null;

    public compositeHeader(Context context,String weather_now,String weather_tomorrow,String firstText,String secondText,String thirdText) {
        super(context);
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.composite_header,this);
        Initialize_Component(v);
        firstButton.setText(firstText);
        secondButton.setText(secondText);
        thirdButton.setText(thirdText);
        SetEventListeners();
        UpdateWeather(weather_now,weather_tomorrow);
    }

    private void Initialize_Component(View convertedView) {
        firstButton = (Button) convertedView.findViewById(R.id.composite_header_first_button);
        secondButton = (Button) convertedView.findViewById(R.id.composite_header_second_button);
        thirdButton = (Button) convertedView.findViewById(R.id.composite_header_third_button);
        updateButton = (Button) convertedView.findViewById(R.id.composite_header_update);
        backButton = (Button) convertedView.findViewById(R.id.composite_header_back_button);
        weather_tommorow = (TextView) convertedView.findViewById(R.id.composite_header_weather_tommorow);
        weather_today = (TextView) convertedView.findViewById(R.id.composite_header_weather_now);
    }

    private void SetEventListeners() {
        firstButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    public void SetUpdateListener(onUpdateClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_header_first_button:
               try {
                   Intent intent = new Intent(getContext(), activityNewsPreview.class);
                   getContext().startActivity(intent);
               } catch (Exception e) {
                   Log.e(TAG, "Exception");
                   e.printStackTrace();
               }
            break;
            case R.id.composite_header_second_button:
                try {
                    Intent intent = new Intent(getContext(), activityContact.class);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Exception");
                    e.printStackTrace();
                }
            break;
            case R.id.composite_header_third_button:
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
            case R.id.composite_header_back_button :
                // create on back action
                break;
        }
    }

    public void UpdateWeather(String now, String tomorrow)
    {
        // alt + 0176 -> значек градуса
    }
}