package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;
import ru.news.tagil.utility.onClickInHeaderListener;

/**
 * Created by Alexander on 23.07.13.
 */
public class compositeHeaderSimple extends RelativeLayout implements View.OnClickListener {
    private Button button,backButton,updateButton;
    private TextView tomorrow, now;
    private onClickInHeaderListener listener = null;
    
    public compositeHeaderSimple(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeHeaderSimple(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();
    }

    public compositeHeaderSimple(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    public void Set(String buttonText){
        button.setText(buttonText);
    }

    public void UpdateWeather(String now, String tomorrow)
    {
        this.now.setText(now+getContext().getString(R.string.degree));
        this.tomorrow.setText(tomorrow+getContext().getString(R.string.degree));
    }

    public void SetUpdateButtonVisibility(boolean b) {
        if(b) {
            updateButton.setVisibility(View.VISIBLE);
        } else {
            updateButton.setVisibility(View.GONE);
        }
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header_simple,this);
        button = (Button) findViewById(R.id.composite_header_simple_button);
        backButton = (Button) findViewById(R.id.composite_header_simple_back_button);
        updateButton = (Button) findViewById(R.id.composite_header_simple_update);
        now = (TextView) findViewById(R.id.composite_header_simple_weather_now);
        tomorrow = (TextView) findViewById(R.id.composite_header_simple_weather_tomorrow);
        SetEventListeners();
    }

    private void SetEventListeners() {
        backButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    public void SetHeaderButtonsListener(onClickInHeaderListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_header_simple_update :
                if(listener!= null) listener.UpdateButtonClicks();
                break;
            case R.id.composite_header_simple_back_button :
                if(listener!= null) listener.BackButtonClicks();
                break;
        }
    }
}
