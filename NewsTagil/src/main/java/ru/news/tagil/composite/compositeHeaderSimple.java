package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;
import ru.news.tagil.utility.onUpdateClickListener;

/**
 * Created by Alexander on 23.07.13.
 */
public class compositeHeaderSimple extends RelativeLayout implements View.OnClickListener {
    private Button button,backButton,updateButton;
    private TextView tomorrow, now;
    private onUpdateClickListener listener = null;
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

    public void UpdateWeather(String weather_tomorrow,String buttonText) {
        //TODO
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header_simple,this);
        button = (Button) findViewById(R.id.composite_header_simple_button);
        backButton = (Button) findViewById(R.id.composite_header_simple_back_button);
        updateButton = (Button) findViewById(R.id.composite_header_simple_update);
        SetEventListeners();
    }

    private void SetEventListeners() {
        backButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    public void SetUpdateListener(onUpdateClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_header_update :
                if(listener!= null) listener.UpdateButtonClicks();
                break;
            case R.id.composite_header_back_button :
                // create on back action
                break;
        }
    }
}