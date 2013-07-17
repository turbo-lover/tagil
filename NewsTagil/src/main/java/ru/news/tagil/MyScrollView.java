package ru.news.tagil;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by turbo_lover on 17.07.13.
 */
public class MyScrollView extends ScrollView {

    onScrollViewChangedListener listener = null;
    public MyScrollView(Context context) {
        super(context);
    }

    public void setListener(onScrollViewChangedListener outside_listener)
    {
        listener = outside_listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = getChildAt(getChildCount()-1);
        int diff = (view.getBottom()-(getHeight()+getScrollY())); // Calculate the scroll diff
        if( diff == 0 ){  // if diff is zero, then the bottom has been reached
          if(listener!=null) listener.onScrollChanged(this,l,t,oldl,oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
