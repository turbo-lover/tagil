package ru.news.tagil.utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import ru.news.tagil.R;

/**
 * Created by Alexander on 15.07.13.
 */
public class myPreferencesWorker
{
    private SharedPreferences sPref;
    private Context context;

    final private  String preference_user_login = "login";
    final private String preference_user_pass = "pass";
    final private String preference_location = "tagil_pref";
    final private String preference_current_typeface = "typeface";
    final private String preference_current_typeface_size = "typeface_size";
    final private String preference_autoupdate_mode = "autoupdate_mode";



    public myPreferencesWorker(Context in) {
        context = in;
    }
    /* функции установки значений */
    public void set_login(String login) {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(preference_user_login ,login);
        ed.commit();
    }

    public void set_pass(String pass) {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(preference_user_pass ,pass);
        ed.commit();
    }

    public void set_autoupdate_mode(String pass) {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(preference_autoupdate_mode ,pass);
        ed.commit();
    }

    public void set_typeface(String tf) {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(preference_current_typeface,tf);
        ed.commit();
    }

    public void set_typeface_size(float size) {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putFloat(preference_current_typeface_size, size);
        ed.commit();
    }
    /* функции получения */
    public String get_login() {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        return sPref.getString(preference_user_login,"");
    }

    public String get_pass() {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        return sPref.getString(preference_user_pass,"");
    }

    /**
     *
     * @return current typeface location, or by default param font7.ttf
     */
    public String get_typeface() {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        return sPref.getString(preference_current_typeface,"fonts/font7.ttf");
    }

    public float get_typeface_size() {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        return sPref.getFloat(preference_current_typeface_size, context.getResources().getDimension(R.dimen.devault_size));
    }

    public String get_autoupdate_mode() {
        ContextWrapper cw = new ContextWrapper(context);
        sPref = cw.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        return sPref.getString(preference_autoupdate_mode,"WIFI");
    }

}
