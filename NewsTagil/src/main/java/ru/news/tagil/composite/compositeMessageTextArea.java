package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import ru.news.tagil.R;

/**
 * Created by Alexander on 10.08.13.
 */
public class compositeMessageTextArea extends RelativeLayout {
    private Button send;
    private EditText text;
    public compositeMessageTextArea(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeMessageTextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeMessageTextArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_message_textarea,this);
        send = (Button) findViewById(R.id.composite_message_textarea_send);
        text = (EditText) findViewById(R.id.composite_message_textarea_txt);
    }

    public String GetText() {
        return text.getText().toString();
    }

    public void SetEventListeners(OnClickListener listener) {
        send.setOnClickListener(listener);
    }

    public void eraseInput() {
        text.setText("");
    }
    public void BlockInput(boolean b) {
        text.setEnabled(b);
        send.setEnabled(b);
    }
}
