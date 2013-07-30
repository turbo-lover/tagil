package ru.news.tagil.composite;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;


public class compositeContactContent extends RelativeLayout implements View.OnClickListener {

    TextView seek, znakomstva, polojenie, hobby, music, about;
    LinearLayout user_images;
    Button start_dialog;

    public compositeContactContent(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.composite_contact_content, this);
        Initialize_Component(v);
        SetEventListeners();
    }

    private void SetEventListeners() {
        start_dialog.setOnClickListener(this);
    }

    private void Initialize_Component(View convertedView) {
        user_images = (LinearLayout) convertedView.findViewById(R.id.composite_lin_lay_user_images);
        seek = (TextView) convertedView.findViewById(R.id.composite_contact_content_seek_content);
        znakomstva = (TextView) convertedView.findViewById(R.id.composite_contact_content_for_znakomstv_content);
        polojenie = (TextView) convertedView.findViewById(R.id.composite_contact_content_polojenie_content);
        hobby = (TextView) convertedView.findViewById(R.id.composite_contact_content_hobby_content);
        music = (TextView) convertedView.findViewById(R.id.composite_contact_content_music_content);
        about = (TextView) convertedView.findViewById(R.id.composite_contact_content_about_content);
        start_dialog = (Button) convertedView.findViewById(R.id.composite_contact_content_start_dialog);
    }

    @Override
    public void onClick(View view) {

    }

}