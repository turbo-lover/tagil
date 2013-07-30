package ru.news.tagil.composite;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ru.news.tagil.R;
/**
 * Created by Alexander on 15.07.13.
 */
public class compositeTapePreview extends RelativeLayout {
    private TextView dateTextView,timeTextView,headerTextView;

    public compositeTapePreview(Context context, String date, String time, String header) {
        super(context);
        Initialize_Component();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_tape_preview,this);
        dateTextView.setText(date);
        timeTextView.setText(time);
        headerTextView.setText(header);
    }

    private void Initialize_Component() {
        dateTextView = (TextView) findViewById(R.id.composite_tape_preview_date);
        timeTextView = (TextView) findViewById(R.id.composite_tape_preview_time);
        headerTextView = (TextView) findViewById(R.id.composite_tape_preview_name_post);
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
