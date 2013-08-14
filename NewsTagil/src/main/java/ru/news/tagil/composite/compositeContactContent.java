package ru.news.tagil.composite;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import ru.news.tagil.R;

import static java.lang.Integer.*;

public class compositeContactContent extends RelativeLayout implements View.OnClickListener {

    TextView seek_content, purpose_content, marriage_content, hobby_content, music_content, about_content, name;
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
        name = (TextView) findViewById(R.id.composite_contact_content_name);
        start_dialog = (Button) findViewById(R.id.composite_contact_content_start_dialog);

        if(isMyProfile) {
            start_dialog.setVisibility(INVISIBLE);
        }

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

    public void SetName(String name) {
        this.name.setText(name);
    }

    private void AddCompositeElement() {
        user_images.addView(addImagesContact);
    }
    /* setters */
    private int myParseInt(String value) {
        try {
            return parseInt(value);
        } catch (NumberFormatException numbFormExcept) {
            return -1;
        }
    }

    public void setAvatarByTAg(String tag) {
        avatar.setImageBitmap( addImagesContact.getImageByTag(tag));
    }

    public void _setSeek(String seek_content) {
        CharSequence[] array = getResources().getTextArray(R.array.seek);
        int i = myParseInt(seek_content);
        if(i==-1) {
           this.seek_content.setText(array[array.length-1]);
           return;
       }
       this.seek_content.setText(array[i]);
    }

    public void _setPurpose(String purpose_content) {
        CharSequence[] array = getResources().getTextArray(R.array.purpose);
        int i = myParseInt(purpose_content);
        if(i==-1) {
            this.purpose_content.setText(array[array.length-1]);
            return;
        }
        this.purpose_content.setText(array[i]);

    }

    public void _setMarriage(String marriage_content) {
        CharSequence[] array = getResources().getTextArray(R.array.marriage);
        int i = myParseInt(marriage_content);
        if(i==-1) {
            this.marriage_content.setText(array[array.length-1]);
            return;
        }
        this.marriage_content.setText(array[i]);

    }

    public void _setMusic(String content) {
        if(content.equals("null")) content = getResources().getString(R.string.not_installed);
        this.music_content.setText(content);
    }

    public void _setHobby(String content) {
        if(content.equals("null")) content = getResources().getString(R.string.not_installed);
        this.hobby_content.setText(content);
    }

    public void _setAbout(String content) {
        if(content.equals("null")) content = getResources().getString(R.string.not_installed);
        this.about_content.setText(content);
    }

    public void _setAvatar(Bitmap avatar_bitmap) {
        avatar.setImageBitmap(avatar_bitmap);
    }

    public void _putImageInHorizontalTape(Bitmap image, String id) {
        addImagesContact.addImageToTape(image,id);
    }

    @Override
    public void onClick(View view) {

    }
}