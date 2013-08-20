package ru.news.tagil.utility;

import android.os.AsyncTask;
import org.json.JSONObject;
/**
 * Created by Alexander on 15.07.13.
 */
public class myAsyncTaskWorker extends AsyncTask<Object, Void, JSONObject> {
    private jsonActivity listenerActivity;
    private jsonActivityMode mode;
    public myAsyncTaskWorker(jsonActivity listenerActivity,jsonActivityMode mode) {
        this.listenerActivity = listenerActivity;
        this.mode = mode;
    }
    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject response = null;
        try {
            response = jsonWorker.SendRecieveJson((JSONObject) params[0], (String) params[1]);
        } catch (Exception e) {
            e.printStackTrace(); }
        return response;
    }
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        listenerActivity.FinishedRequest(jsonObject,mode);
    }
}