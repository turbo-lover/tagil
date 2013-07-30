package ru.news.tagil.utility;

import org.json.JSONObject;

/**
 * Created by Alexander on 29.07.13.
 */
public interface updateListActivity {
    public final int COUNT = 10;
    public void Set(JSONObject jsonObject,boolean insertAtStart);
    public void Get();
    public void GetNew();
    public int GetTotalCount();
}
