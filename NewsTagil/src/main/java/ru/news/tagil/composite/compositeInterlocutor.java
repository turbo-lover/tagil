package ru.news.tagil.composite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 09.08.13.
 */
public class compositeInterlocutor extends RelativeLayout {
    private TextView login,lastLogin,lastMsg;
    private ImageView image;
    public compositeInterlocutor(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeInterlocutor(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeInterlocutor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }

    public void Set(String login,String lastLogin,String lastMsg,Bitmap img) {
        this.login.setText(login);
        this.lastLogin.setText(lastLogin);
        this.lastMsg.setText(lastMsg);
        if(img == null){
            image.setBackgroundColor(Color.BLACK);
        } else {
            image.setImageBitmap(img);
        }
    }

    public String GetInterlocutor() {
        return login.getText().toString();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_interlocutor,this);
        login = (TextView) findViewById(R.id.composite_interlocutor_login);
        lastLogin = (TextView) findViewById(R.id.composite_interlocutor_last_login);
        lastMsg = (TextView) findViewById(R.id.composite_interlocutor_last_msg);
        image = (ImageView) findViewById(R.id.composite_interlocutor_img);
    }
}
