package ru.news.tagil.utility;

import org.json.JSONObject;

/**
 * Created by Alexander on 29.07.13.
 */
public interface updateListActivity  {
    public final int GET_COUNT = 20;    //Number of elements that will be downloaded per instance (messages,news,adverts,etc.)
    public abstract void Set(JSONObject jsonObject,boolean insertAtStart);
    public abstract int GetTotalCount(String extra1,String extra2);
}
