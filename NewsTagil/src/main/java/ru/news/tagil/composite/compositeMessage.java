package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;
import ru.news.tagil.utility.myTextView;

/**
 * Created by Alexander on 09.08.13.
 */
public class compositeMessage extends RelativeLayout {
    private TextView login,date,time;
    private myTextView msg;
    public compositeMessage(Context context) {
        super(context);
    }

    public compositeMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public compositeMessage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void Initialize_Component(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId,this);
        login = (TextView) findViewById(R.id.composite_message_login);
        date = (TextView) findViewById(R.id.composite_message_date);
        time = (TextView) findViewById(R.id.composite_message_time);
        msg = (myTextView) findViewById(R.id.composite_message_text);
    }

    public void Set(String login,String msg,String date,String time) {
        this.login.setText(login);
        this.date.setText(date);
        this.time.setText(time);
        this.msg.setText(msg);
    }

    public String getDateTime() {
        return date.getText().toString()+" "+time.getText().toString();
    }
}
