package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.news.tagil.R;

/**
 * Created by Alexander on 11.08.13.
 */
public class compositeSecondButton extends RelativeLayout {
    private ImageView like,comment,favorite;
    public compositeSecondButton(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeSecondButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeSecondButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_second_button_nav, this);
        like    =(ImageView) findViewById( R.id.like);
        comment  =(ImageView) findViewById( R.id.comment);
        favorite   =(ImageView) findViewById( R.id.elect);
    }

    public void SetEventListeners(OnClickListener l) {
        like.setOnClickListener(l);
        comment.setOnClickListener(l);
        favorite.setOnClickListener(l);
    }

    public void SetLikeBg(int color) {
        like.setBackgroundColor(color);
    }

    public void SetFavoriteBg(int color) {
        favorite.setBackgroundColor(color);
    }
}
