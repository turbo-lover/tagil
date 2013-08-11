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

    TextView seek_content, znakomstva_content, polojenie_content, hobby_content, music_content, about_content;
    View seek,purpose,marriage,hobby,music,about;
    LinearLayout user_images;
    Button start_dialog;
    compositeAddImagesContact imagesContact;
    Boolean isMyProfile;

    public compositeContactContent(Context context,Boolean isMy) {
        super(context);
        isMyProfile = isMy;
        Initialize_Component();
    }

    private void SetEventListeners() {
        start_dialog.setOnClickListener(this);

    }
    public void SetEventListeners(OnClickListener listener) {
        imagesContact.SetEventListeners(listener);
        seek.setOnClickListener(listener);
        purpose.setOnClickListener(listener);
        marriage.setOnClickListener(listener);
        hobby.setOnClickListener(listener);
        music.setOnClickListener(listener);
        about.setOnClickListener(listener);
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_contact_content, this);

        imagesContact = new compositeAddImagesContact(getContext(),isMyProfile);
        user_images = (LinearLayout) findViewById(R.id.contact_photo_panel);//addImagesContent;

        seek_content = (TextView) findViewById(R.id.composite_contact_content_seek_content);
        znakomstva_content = (TextView) findViewById(R.id.composite_contact_content_for_znakomstv_content);
        polojenie_content = (TextView) findViewById(R.id.composite_contact_content_polojenie_content);
        hobby_content = (TextView) findViewById(R.id.composite_contact_content_hobby_content);
        music_content = (TextView) findViewById(R.id.composite_contact_content_music_content);
        about_content = (TextView) findViewById(R.id.composite_contact_content_about_content);
        start_dialog = (Button) findViewById(R.id.composite_contact_content_start_dialog);

        seek = findViewById(R.id.contact_seek);
        purpose= findViewById(R.id.contact_purpose_for_seeking);
        marriage= findViewById(R.id.contact_marriage);
        hobby= findViewById(R.id.contact_hobby);
        music= findViewById(R.id.contact_music);
        about= findViewById(R.id.contact_about);

        AddCompositeElement();
        SetEventListeners();
    }

    private void AddCompositeElement() {
        user_images.addView(imagesContact);
    }


    @Override
    public void onClick(View view) {

    }

}