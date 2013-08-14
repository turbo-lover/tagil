package ru.news.tagil.composite;/**
 * Created by turbo_lover on 08.08.13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ru.news.tagil.R;
import ru.news.tagil.utility.transformUtil;

public class compositeAddImagesContact extends RelativeLayout {
    LinearLayout header,images_layout;
    Boolean isMyProfile;
    OnClickListener listener;

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
                this.listener = listener;
            }
        }
    }
    public void addImageToTape(Bitmap bitmap, String id) {
        ImageView image = new ImageView(getContext());
        image.setImageBitmap(bitmap);
        image.setBackgroundResource(R.drawable.ads_button_bg);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(transformUtil.PxtDIP(80,getContext()), transformUtil.PxtDIP(80, getContext()));
        int margin = transformUtil.PxtDIP(2,getContext());
        lp.setMargins(margin,margin,margin,margin);
        lp.weight =1;
        image.setLayoutParams(lp);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setTag(id);
        if(isMyProfile) image.setOnClickListener(listener);//переменная в классе, инициализируется в SetEventListener и только если
        images_layout.addView(image);

    }

    public Bitmap getImageByTag(String tag) {
        for( int i =0; i<images_layout.getChildCount();i++) {
            ImageView child = (ImageView) images_layout.getChildAt(i);
            if(child.getTag().equals(tag))
                return ((BitmapDrawable) child.getDrawable()).getBitmap();
        }
        return null;
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_add_images, this);

        header = (LinearLayout) findViewById(R.id.composite_add_images_ll);
        images_layout = (LinearLayout) findViewById(R.id.user_images_horizontal_layout);
    }
}