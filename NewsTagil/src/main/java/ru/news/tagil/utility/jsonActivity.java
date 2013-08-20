package ru.news.tagil.utility;

import org.json.JSONObject;

/**
 * Created by Alexander on 05.08.13.
 */
public interface jsonActivity {
    public abstract void Set(JSONObject jsonObject);
    public abstract void FinishedRequest(JSONObject returned,jsonActivityMode mode);
}
