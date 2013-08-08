package ru.news.tagil.composite;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 08.08.13.
 */
public class compositeContactPreview extends RelativeLayout {
    private ImageView img;
    private TextView txt;
    public compositeContactPreview(Context context) {
        super(context);
        Initialize_Component();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_contact_preview,this);
        img = (ImageView) findViewById(R.id.composite_contact_preview_img);
        txt = (TextView) findViewById(R.id.composite_contact_preview_textView);
    }

    public compositeContactPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeContactPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }

    public void Set(String login,Bitmap bmp) {
        img.setImageBitmap(bmp);
        txt.setText(login);
    }

    public String GetLogin() {
        return txt.getText().toString();
    }

}
