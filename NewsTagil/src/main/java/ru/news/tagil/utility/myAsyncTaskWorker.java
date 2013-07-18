package ru.news.tagil.utility;

import android.os.AsyncTask;
import org.json.JSONObject;
/**
 * Created by Alexander on 15.07.13.
 */
public class myAsyncTaskWorker extends AsyncTask<Object, Void, JSONObject> {
    public myAsyncTaskWorker() {}
    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject response = null;
        try {
            response = jsonWorker.SendRecieveJson((JSONObject) params[0], (String) params[1]);
        } catch (Exception e) {
            e.printStackTrace(); }
        return response;
    }
}