package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class compositeMessageItem extends RelativeLayout {
    public compositeMessageItem(Context context) {
        super(context);
        Initialize_Component();
    }

    public compositeMessageItem(Context context,AttributeSet attrs)
    {
        super(context,attrs);
        Initialize_Component();
    }

    public compositeMessageItem(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Initialize_Component();
    }

    private void Initialize_Component() {

    }
}
