package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.news.tagil.R;
import ru.news.tagil.activity.*;

/**
 * Created by turbo_lover on 18.07.13.
 */
public class compositeFirstButton extends LinearLayout
        implements View.OnClickListener {

    Button tape,dialog,elect,useful,setting;

    public compositeFirstButton(Context context) {
        super(context);

        InitVariable();
        SetListeners();
    }

    private void SetListeners() {
        tape.setOnClickListener(this);
        dialog.setOnClickListener(this);
        elect.setOnClickListener(this);
        useful.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void InitVariable() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_first_button_nav,this);

        tape    =(Button) findViewById( R.id.first_tape_button);
        dialog  =(Button) findViewById( R.id.first_dialogs_button);
        elect   =(Button) findViewById( R.id.first_elect_button);
        useful  =(Button) findViewById( R.id.first_useful_button);
        setting =(Button) findViewById( R.id.first_setting_button);
    }

    @Override
    public void onClick(View view) {
        Context context = getContext();
        switch (view.getId())
        {
            case R.id.first_tape_button:
                try {
                    Intent intent = new Intent(context, activityNewsPreview.class);
                    context.startActivity(intent);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_dialogs_button:
                try {
                    Intent intent = new Intent(context, activityMessages.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_elect_button: // избранное
                try {
                    Intent intent = new Intent(context, activityFavoriteNews.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_useful_button:
                try {
                    Intent intent = new Intent(context, activityUseful.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.first_setting_button:
                try {
                    Intent intent = new Intent(context, activitySettings.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
