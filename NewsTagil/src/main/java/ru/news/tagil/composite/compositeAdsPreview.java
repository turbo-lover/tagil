package ru.news.tagil.composite;

import ru.news.tagil.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Created by Alexander on 30.07.13.
 */
public class compositeAdsPreview extends RelativeLayout {
    private TextView titleTextView,publisherTextView,dateTextView;
    private ImageView img;
    public compositeAdsPreview(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeAdsPreview(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();
    }

    public compositeAdsPreview(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    public void Set(String title, String publisher, String date, Bitmap bmp) {
        titleTextView.setText(title);
        publisherTextView.setText(publisher);
        dateTextView.setText(date);
        if(bmp != null) {
            img.setImageBitmap(bmp);
        } else {
            img.setMaxHeight(0); } //TODO либо сделать картинку - заглушку, либо уменьшать размер ImageView до 0.
    }
    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_ads_content, this);
        titleTextView = (TextView) findViewById(R.id.composite_ads_title);
        publisherTextView = (TextView) findViewById(R.id.composite_ads_name);
        dateTextView = (TextView) findViewById(R.id.composite_ads_date);
        img = (ImageView) findViewById(R.id.composite_ads_imageView);
    }

    public String getDate() {
        return dateTextView.getText().toString();
    }

    public String getTitle() {
        return titleTextView.getText().toString();
    }

    public String getPublisher() {
        return publisherTextView.getText().toString();
    }

    public Bitmap getImg() {
        return (img.getDrawable() == null)? null : ((BitmapDrawable)img.getDrawable()).getBitmap();
    }
}
