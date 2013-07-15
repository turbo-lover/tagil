package ru.news.tagil;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Alexander on 15.07.13.
 */
public class Composite_tape_activity extends RelativeLayout {
    TextView dateTextView,timeTextView,headerTextView,textTextView;
    public Composite_tape_activity(Context context,String date,String time,String header,String text) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_tape_content,this);
        dateTextView = (TextView) findViewById(R.id.date);
        timeTextView = (TextView) findViewById(R.id.time);
        headerTextView = (TextView) findViewById(R.id.name_post);
        textTextView = (TextView) findViewById(R.id.post_content);
        dateTextView.setText(date);
        timeTextView.setText(time);
        headerTextView.setText(header);
        textTextView.setText(text);
    }
}
