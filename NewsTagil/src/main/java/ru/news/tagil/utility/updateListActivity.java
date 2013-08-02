package ru.news.tagil.utility;

import org.json.JSONObject;

/**
 * Created by Alexander on 29.07.13.
 */
public interface updateListActivity {
    public final int GET_COUNT = 10;
    public abstract void Set(JSONObject jsonObject,boolean insertAtStart);
    public abstract void Get(JSONObject jsonObject,String scriptAddress);
    public abstract void GetNew(JSONObject jsonObject,String scriptAddress);
    public abstract int GetTotalCount(String tableName,String login);

}
