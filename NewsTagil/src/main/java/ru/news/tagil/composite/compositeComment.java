package ru.news.tagil.composite;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageButton;
import ru.news.tagil.R;

/**
 * Created by Alexander on 11.08.13.
 */
public class compositeComment extends compositeMessage {
    private ImageButton avatar;
    public compositeComment(Context context) {
        super(context);
        Initialize_Component(R.layout.composite_comment);
    }

    public compositeComment(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component(R.layout.composite_comment);
    }

    public compositeComment(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component(R.layout.composite_comment);
    }
    @Override
    public void Initialize_Component(int layoutId) {
        super.Initialize_Component(layoutId);
        avatar = (ImageButton) findViewById(R.id.composite_comment_avatar);
    }
    public void SetAvatar(Bitmap bmp) {
        if(bmp != null) {
            avatar.setImageBitmap(bmp);
        }
    }
}
