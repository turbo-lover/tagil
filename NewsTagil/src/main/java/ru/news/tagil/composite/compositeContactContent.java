package ru.news.tagil.composite;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import ru.news.tagil.R;


public class compositeContactContent extends RelativeLayout {

    public compositeContactContent(Context context) {
        super(context);
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {

    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_contact_content, this);
    }
}