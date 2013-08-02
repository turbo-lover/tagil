package ru.news.tagil.composite;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 31.07.13.
 */
public class compositeAdsContent extends RelativeLayout {
    private TextView titleTextView,contentTextView;
    private Button firstButton,secondButton;
    private ImageView img;

    public compositeAdsContent(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeAdsContent(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();
    }

    public compositeAdsContent(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    public void Set(String header,String text,Bitmap bmp) {
        titleTextView.setText(header);
        contentTextView.setText(text);
        img.setImageBitmap(bmp);

        //SWITCH
        firstButton.setText(getResources().getString(R.string.startDialogText));
        secondButton.setText(getResources().getString(R.string.favoriteText));

    }
    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_read_ads_content,this);
        titleTextView = (TextView) findViewById(R.id.read_ads_content_title);
        contentTextView = (TextView) findViewById(R.id.read_ads_content_text);
        firstButton = (Button) findViewById(R.id.read_ads_content_first_button);
        secondButton = (Button) findViewById(R.id.read_ads_content_second_button);
        img = (ImageView) findViewById(R.id.read_ads_content_image);
    }
}
