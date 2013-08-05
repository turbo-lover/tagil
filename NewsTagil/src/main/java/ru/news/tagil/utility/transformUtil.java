package ru.news.tagil.utility;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by turbo_lover on 05.08.13.
 */
public class transformUtil {

    static public int PxtDIP(float dp, Context cnt)
    {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, cnt.getResources().getDisplayMetrics());
        return value;
    }

    static public int PxtSIP(float sp, Context cnt)
    {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, cnt.getResources().getDisplayMetrics());
        return value;
    }
}
