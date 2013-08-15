package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
import ru.news.tagil.utility.myTextView;

/**
 * Created by Alexander on 15.07.13.
 */
public class compositeTapePreview extends RelativeLayout {
    private TextView dateTextView,timeTextView,like_count;
    private myTextView headerTextView;

    public compositeTapePreview(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeTapePreview(Context context,AttributeSet attrs) {
        super(context,attrs);
        Initialize_Component();
    }

    public compositeTapePreview(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    public void Set( String date, String time, String header,String like_count) {
        dateTextView.setText(date);
        timeTextView.setText(time);
        headerTextView.setText(header);
        this.like_count.setText(like_count);
    }

    public void SetFont(){
        headerTextView.init();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_tape_preview,this);
        dateTextView = (TextView) findViewById(R.id.composite_tape_preview_date);
        timeTextView = (TextView) findViewById(R.id.composite_tape_preview_time);
        headerTextView = (myTextView) findViewById(R.id.composite_tape_preview_name_post);
        like_count = (TextView) findViewById(R.id.composite_tape_preview_like_count);
    }

    public String getDateTime() {
        return dateTextView.getText().toString()+" "+timeTextView.getText().toString();
    }

    public String getDate() {
        return dateTextView.getText().toString();
    }

    public String getTime() {
        return timeTextView.getText().toString();
    }

    public String getHeader() {
        return headerTextView.getText().toString();
    }
}
