package ru.news.tagil;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Alexander on 15.07.13.
 */
public class My_AsyncTask_Worker extends AsyncTask<Object, Void, JSONObject> {
    public My_AsyncTask_Worker() {}
    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject response = null;
        try {
            response = JSONWorker.SendRecieveJson((JSONObject) params[0], (String) params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
