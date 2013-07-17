package ru.news.tagil;

/**
 * Created by turbo_lover on 17.07.13.
 */
public interface onScrollViewChangedListener
{
    void onScrollHitBottom(MyScrollView scrollView,
                         int x, int y, int oldx, int oldy);
}
