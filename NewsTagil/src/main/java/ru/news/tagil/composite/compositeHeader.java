package ru.news.tagil.composite;
/**
 * Created by turbo_lover on 19.07.13.
 */

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.activity.activityAds;
import ru.news.tagil.activity.activityContact;
import ru.news.tagil.activity.activityNewsPreview;
import ru.news.tagil.utility.mainFrameActivity;
import ru.news.tagil.utility.onClickInHeaderListener;

public class compositeHeader extends RelativeLayout implements View.OnClickListener,EditText.OnEditorActionListener{
    private final String TAG = "compositeHeader";

    private Button firstButton, secondButton, thirdButton, updateButton, backButton;
    private TextView weather_tommorow, weather_today;
    private EditText searchTxt;
    private onClickInHeaderListener listener = null;
    public compositeHeader(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeHeader(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();

    }

    public compositeHeader(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    public void Set(String firstText,String secondText,String thirdText) {
        firstButton.setText(firstText);
        secondButton.setText(secondText);
        thirdButton.setText(thirdText);
    }

    public void SetUpdateButtonVisibility(boolean b) {
        if(b) {
            updateButton.setVisibility(View.VISIBLE);
        } else {
            updateButton.setVisibility(View.GONE);
        }
    }

    public String GetSearchString(){
        return searchTxt.getText().toString();
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header,this);
        firstButton = (Button) findViewById(R.id.composite_header_first_button);
        secondButton = (Button) findViewById(R.id.composite_header_second_button);
        thirdButton = (Button) findViewById(R.id.composite_header_third_button);
        updateButton = (Button) findViewById(R.id.composite_header_update);
        backButton = (Button) findViewById(R.id.composite_header_back_button);
        weather_tommorow = (TextView) findViewById(R.id.composite_header_weather_tommorow);
        weather_today = (TextView) findViewById(R.id.composite_header_weather_now);
        searchTxt = (EditText) findViewById(R.id.composite_header_search);
        SetEventListeners();
    }

    private void SetEventListeners() {
        firstButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        searchTxt.setOnEditorActionListener(this);
    }

    public void SetHeaderButtonsListener(onClickInHeaderListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        mainFrameActivity act = (mainFrameActivity) getContext();
        switch (view.getId())
        {
            case R.id.composite_header_first_button:
               try {
                   Intent intent = new Intent(act, activityNewsPreview.class);
                   act.startActivity(intent);
               } catch (Exception e) {
                   Log.e(TAG, "Exception");
                   e.printStackTrace();
               }
            break;
            case R.id.composite_header_second_button:
                try {
                    if(act.is_authorized) {
                        Intent intent = new Intent(getContext(), activityContact.class);
                        getContext().startActivity(intent);
                    } else {
                        act.LogIn();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception");
                    e.printStackTrace();
                }
            break;
            case R.id.composite_header_third_button:
                try {
                    if(act.is_authorized) {
                        Intent intent = new Intent(getContext(), activityAds.class);
                        getContext().startActivity(intent);
                    } else {
                        act.LogIn();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception");
                    e.printStackTrace();
                }
            break;
            case R.id.composite_header_update :
                if(listener!= null) listener.UpdateButtonClicks();
            break;
            case R.id.composite_header_back_button :
                if(listener!= null) listener.BackButtonClicks();
                break;
        }
    }

    public void UpdateWeather(String now, String tomorrow)
    {
        // alt + 0176 -> значек градуса
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_SEARCH) {
            if(listener != null ) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                listener.SearchButtonClicks(textView.getText().toString());
                return true;
            }
        }
        return false;
    }
}