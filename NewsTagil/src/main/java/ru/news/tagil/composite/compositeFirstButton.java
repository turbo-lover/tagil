package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.news.tagil.R;
import ru.news.tagil.activity.activitySettings;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class compositeFirstButton extends LinearLayout
        implements View.OnClickListener {

    Button tape,dialog,elect,useful,setting;

    public compositeFirstButton(Context context) {
        super(context);

        InitVariable();

    }

    private void InitVariable() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.first_tape_button:
                break;
            case R.id.first_dialogs_button:
                break;
            case R.id.first_elect_button:
                break;
            case R.id.first_useful_button:
                break;
            case R.id.first_setting_button:
                Intent intent = new Intent(getContext(), activitySettings.class);

                break;
        }
    }
}
