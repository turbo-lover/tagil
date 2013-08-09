package ru.news.tagil.composite;/**
 * Created by turbo_lover on 08.08.13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ru.news.tagil.R;

public class compositeAddImagesContact extends RelativeLayout {
    LinearLayout header,images_layout;
    Boolean isMyProfile;

    public compositeAddImagesContact(Context context,Boolean isMyProfile) {
        super(context);
        Initialize_Component();
       // SetEventListeners(listener);

        this.isMyProfile = isMyProfile;

        if(!isMyProfile) dropAddButton();
    }

    private void dropAddButton() {
        header.removeViewAt(1); //удаляем сраную кнопку на чужой странице
    }

    public void SetEventListeners(View.OnClickListener listener) {
        if(isMyProfile) {
            View add_photo = header.getChildAt(1);
            if (add_photo != null) {
                add_photo.setOnClickListener(listener);
            }
        }
    }
    public void addImage(Bitmap bitmap) {
        ImageView image = new ImageView(getContext());
        image.setImageBitmap(bitmap);
        image.setBackgroundResource(R.drawable.ads_button_bg);
        images_layout.addView(image);
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_add_images, this);

        header = (LinearLayout) findViewById(R.id.composite_add_images_ll);
        images_layout = (LinearLayout) findViewById(R.id.user_images_horizontal_layout);
    }
}