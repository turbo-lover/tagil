package ru.news.tagil;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * Created by Alexander on 15.07.13.
 */
public class My_Preferences_Worker
{
    final private  String preference_user_login = "login";
    final private String preference_user_pass = "pass";
    final private String preference_location = "tagil_pref";
    private SharedPreferences sPref;
    private Context context;

    public My_Preferences_Worker(Context in) {
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
}
