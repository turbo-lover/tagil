package ru.news.tagil.composite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.news.tagil.R;

/**
 * Created by Alexander on 01.08.13.
 */


public class compositeMakeAds extends RelativeLayout{
    private EditText header,content;
    private ImageView img;
    private Button sentButton;
    public compositeMakeAds(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeMakeAds(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeMakeAds(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }
    public void SetImg(Bitmap bmp) {
        img.setImageBitmap(bmp);
    }
    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_make_ads_content,this);
        header = (EditText) findViewById(R.id.composite_make_ads_title);
        content = (EditText) findViewById(R.id.composite_make_ads_content);
        img = (ImageView) findViewById(R.id.composite_make_ads_img);
        sentButton = (Button) findViewById(R.id.composite_make_ads_sent);
    }

    public void SetEventListeners(OnClickListener listener) {
        img.setOnClickListener(listener);
        sentButton.setOnClickListener(listener);
    }

    public String GetHeader() {
        return content.getText().toString();
    }

    public String GetContentText() {
        return header.getText().toString();
    }

    public Bitmap GetImg() {
        return (img.getDrawable() == null)? null : ((BitmapDrawable)img.getDrawable()).getBitmap();
    }
}
