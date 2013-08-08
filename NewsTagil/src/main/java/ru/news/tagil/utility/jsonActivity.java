package ru.news.tagil.utility;

import org.json.JSONObject;

/**
 * Created by Alexander on 05.08.13.
 */
public interface jsonActivity {
    public abstract JSONObject Get(JSONObject jsonObject);
    public abstract void Set(JSONObject jsonObject);
}
