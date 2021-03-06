package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ru.news.tagil.R;
import ru.news.tagil.activity.*;
import ru.news.tagil.utility.firstClassesEnum;
import ru.news.tagil.utility.mainFrameJsonActivity;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class compositeFirstButton extends LinearLayout implements View.OnClickListener {

    private ImageView tape,dialog,elect,useful,setting;
    private Class currentClass;

    public compositeFirstButton(Context context) {
        super(context);
        currentClass = context.getClass();
        Initialize_Component();
    }

    public compositeFirstButton(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();
    }
    // THREE ARGUMENT CONSTRUCTOR FOR LINEAR LAYOUT REQUIRED MIN LVL 11 API
    private void SetListeners() {
        tape.setOnClickListener(this);
        dialog.setOnClickListener(this);
        elect.setOnClickListener(this);
        useful.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_first_button_nav, this);

        tape    =(ImageView) findViewById( R.id.first_tape_button);
        dialog  =(ImageView) findViewById( R.id.first_dialogs_button);
        elect   =(ImageView) findViewById( R.id.first_elect_button);
        useful  =(ImageView) findViewById( R.id.first_useful_button);
        setting =(ImageView) findViewById( R.id.first_setting_button);
        SetListeners();

        SetCurrentColorOnButton();
    }

    private void SetCurrentColorOnButton() {

        firstClassesEnum classes = firstClassesEnum.getClass(currentClass.toString());
        switch (classes)
        {
            case NewsPreview:
                tape.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
            case Correspondence:
                dialog.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
            case Favorite:
                elect.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
            case Useful:
                useful.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
            case Setting:
                setting.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
        }

    }
    @Override
    public void onClick(View view) {
        mainFrameJsonActivity act = (mainFrameJsonActivity) getContext();
        switch (view.getId())
        {
            case R.id.first_tape_button:
                try {
                    if(currentClass.equals(activityNewsPreview.class)) return;
                        Intent intent = new Intent(act, activityNewsPreview.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        act.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_dialogs_button:
                try {
                    if(currentClass.equals(activityCorrespondence.class)) return;
                    if(act.is_authorized) {
                        Intent intent = new Intent(act, activityCorrespondence.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        act.startActivity(intent);
                    } else {
                        act.LogIn();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_elect_button: // избранное
                try {
                    if(act.is_authorized) {
                        if(currentClass.equals(activityFavoriteNews.class)) return;
                        Intent intent = new Intent(act, activityFavoriteNews.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        act.startActivity(intent);
                    } else {
                        act.LogIn();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_useful_button:
                try {
                    if(currentClass.equals(activityUseful.class)) return;
                    Intent intent = new Intent(act, activityUseful.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_setting_button:
                try {
                    if(currentClass.equals(activitySettings.class)) return;
                    Intent intent = new Intent(act, activitySettings.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
