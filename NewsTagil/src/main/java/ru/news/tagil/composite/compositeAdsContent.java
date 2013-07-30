package ru.news.tagil.composite;

import ru.news.tagil.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Created by Alexander on 30.07.13.
 */
public class compositeAdsContent extends RelativeLayout {
    private TextView titleTextView,publisherTextView,dateTextView;
    private ImageView img;
    public compositeAdsContent(Context context,String title,String publisher,String date,Bitmap bmp) {
        super(context);
        Initialize_Component();
        titleTextView.setText(title);
        publisherTextView.setText(publisher);
        dateTextView.setText(date);
        if(bmp != null) {
            img.setImageBitmap(bmp);
        } else {
            img.setMaxHeight(0); } //TODO либо сделать картинку - заглушку, либо уменьшать размер ImageView до 0.
    }

    private void  Initialize_Component() {
        titleTextView = (TextView) findViewById(R.id.composite_ads_title);
        publisherTextView = (TextView) findViewById(R.id.composite_ads_name);
        dateTextView = (TextView) findViewById(R.id.composite_ads_date);
        img = (ImageView) findViewById(R.id.composite_ads_imageView);
    }

    public String getDate() {
        return dateTextView.getText().toString();
    }
}
