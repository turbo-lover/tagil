package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.news.tagil.R;
import ru.news.tagil.activity.activityProfile;

/**
 * Created by Alexander on 08.08.13.
 */
public class compositeMyProfileSelector extends LinearLayout implements View.OnClickListener {
    private Button btn;
    public compositeMyProfileSelector(Context context) {
        super(context);
        Initialize_Component();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_my_profile_selector,this);
        btn = (Button) findViewById(R.id.composite_my_profile_selector_button);
        SetEventListeners();
    }

    private void SetEventListeners() {
        btn.setOnClickListener(this);
    }

    public compositeMyProfileSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    // для того что бы знать что профиль мой передаем в интенте поле isMy = true
    // а в другом месте ничего не передаем и он по умолчанию равен false
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.composite_my_profile_selector_button) {
            Intent i = new Intent(getContext(),activityProfile.class);
            i.putExtra("isMy",true);
            getContext().startActivity(i);
        }
    }
}
