package ru.news.tagil.utility;

/**
 * Created by turbo_lover on 17.07.13.
 */
public interface onScrollViewChangedListener
{
    void onScrollHitBottom(myScrollView scrollView,int x, int y, int oldx, int oldy);
    void onScrollHitTop(myScrollView myScrollView, int l, int t, int oldl, int oldt);
}
