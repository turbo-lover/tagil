package ru.news.tagil.composite;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import ru.news.tagil.R;


public class compositeContactContent extends RelativeLayout implements View.OnClickListener {

    TextView seek_content, purpose_content, marriage_content, hobby_content, music_content, about_content;
    ImageView avatar;
    View seek,purpose,marriage,hobby,music,about;
    LinearLayout user_images;
    Button start_dialog;
    compositeAddImagesContact addImagesContact;
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
        addImagesContact.SetEventListeners(listener);
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

        addImagesContact = new compositeAddImagesContact(getContext(),isMyProfile);
        user_images = (LinearLayout) findViewById(R.id.contact_photo_panel);//addImagesContent;

        seek_content = (TextView) findViewById(R.id.composite_contact_content_seek_content);
        purpose_content = (TextView) findViewById(R.id.composite_contact_content_for_znakomstv_content);
        marriage_content = (TextView) findViewById(R.id.composite_contact_content_polojenie_content);
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

        avatar = (ImageView) findViewById(R.id.contact_avatar);

        AddCompositeElement();
        SetEventListeners();
    }

    private void AddCompositeElement() {
        user_images.addView(addImagesContact);
    }
    /* setters */

    public void _setSeek(String seek_content) {
        this.seek_content.setText(seek_content);
    }

    public void _setPurpose(String purpose_content) {
        this.purpose_content.setText(purpose_content);
    }

    public void _setMarriage(String marriage_content) {
        this.marriage_content.setText(marriage_content);
    }

    public void _setMusic(String music_content) {
        this.music_content.setText(music_content);
    }

    public void _setHobby(String hobby_content) {
        this.hobby_content.setText(hobby_content);
    }

    public void _setAbout(String about_content) {
        this.about_content.setText(about_content);
    }

    public void _setAvatar(Bitmap avatar_bitmap) {
        avatar.setImageBitmap(avatar_bitmap);
    }
    public void _putImageInHorizontalTape(Bitmap image) {
        addImagesContact.addImage(image);
    }

    @Override
    public void onClick(View view) {

    }

}