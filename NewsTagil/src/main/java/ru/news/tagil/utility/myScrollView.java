package ru.news.tagil.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
/**
 * Created by turbo_lover on 17.07.13.
 */
public class myScrollView extends ScrollView {

    onScrollViewChangedListener listener = null;
    boolean checkEvents = true;
    public myScrollView(Context context) {
        super(context);
    }

    public myScrollView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public void setListener(onScrollViewChangedListener outside_listener) {
        listener = outside_listener;
    }
    public void setEventEnable(boolean b) {
        checkEvents = b;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(!checkEvents) return;
        View view = getChildAt(getChildCount()-1);
        int diff = (view.getBottom()-(getHeight()+getScrollY())); // Calculate the scroll diff
        if( diff <= 0 ){        // if diff is zero, then the bottom has been reached
            if(listener!=null) listener.onScrollHitBottom(this, l, t, oldl, oldt);
        }
        if(getScrollY() <= 0 ) {
            if(listener != null) listener.onScrollHitTop(this,l,t,oldl,oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
