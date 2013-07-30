package ru.news.tagil.composite;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 18.07.13.
 */
public class compositeTapeContent extends RelativeLayout {
    private TextView headerTextView,timeTextView,dateTextView,contentTextView;
    private ImageView img;
    public compositeTapeContent(Context context,String header,String time,String date,String text,Bitmap bmp) {
        super(context);
        Initialize_Component();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_tape_content,this);
        headerTextView.setText(header);
        timeTextView.setText(time);
        dateTextView.setText(date);
        contentTextView.setText(text);
        if(bmp != null) {
            img.setImageBitmap(bmp);
        } else {
            img.setMaxHeight(0); } //TODO либо сделать картинку - заглушку, либо уменьшать размер ImageView до 0.
    }

    private void Initialize_Component() {
        headerTextView = (TextView) findViewById(R.id.composite_tape_content_name_post);
        timeTextView = (TextView) findViewById(R.id.composite_tape_content_time);
        dateTextView = (TextView) findViewById(R.id.composite_tape_content_date);
        contentTextView = (TextView) findViewById(R.id.composite_tape_content_post_content);
        img = (ImageView) findViewById(R.id.composite_tape_content_img);
    }
}
