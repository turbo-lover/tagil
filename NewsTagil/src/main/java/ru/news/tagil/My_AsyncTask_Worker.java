package ru.news.tagil;

import org.json.JSONObject;

/**
 * Created by Alexander on 15.07.13.
 */
public class My_AsyncTask_Worker {
    public My_AsyncTask_Worker()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected JSONObject doInBackground(Object... params)
    {
        JSONObject response = null;
        try {
            response = JSONWorker.SendRecieveJson((JSONObject) params[0], (String) params[1]);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }
}
