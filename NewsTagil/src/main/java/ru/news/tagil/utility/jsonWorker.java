package ru.news.tagil.utility;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
/**
 * Created by Alexander on 15.07.13.
 */
public class jsonWorker {
    //TODO add timeout for connection
    //private static final int TIMEOUT_MILLISEC = 5000;
    public static JSONObject SendRecieveJson(JSONObject jsonToSend,String uri) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(uri);
        httppost.setEntity(new ByteArrayEntity(jsonToSend.toString().getBytes("UTF8")));
        httppost.setHeader("Content-Type", "application/json");
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        String responseBody;
        try {
            responseBody = httpclient.execute(httppost,responseHandler);
            return new JSONObject(responseBody);
        } catch (Exception ex) {
            ex.printStackTrace(); }
        return null;
    }
}
