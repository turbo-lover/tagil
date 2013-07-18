package ru.news.tagil.composite;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 18.07.13.
 */
public class compositeTapeContent extends RelativeLayout {
    TextView headerTextView,timeTextView,dateTextView,contentTextView;
    ImageView img;
    public compositeTapeContent(Context context,String header,String time,String date) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_tape_content,this);
        headerTextView = (TextView) findViewById(R.id.composite_tape_content_name_post);
        timeTextView = (TextView) findViewById(R.id.composite_tape_content_time);
        dateTextView = (TextView) findViewById(R.id.composite_tape_content_date);
        contentTextView = (TextView) findViewById(R.id.composite_tape_content_post_content);
       // img = (ImageView) findViewById();
        headerTextView.setText(header);
        timeTextView.setText(time);
        dateTextView.setText(date);
    }
}
