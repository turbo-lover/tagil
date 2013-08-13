package ru.news.tagil.utility;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONObject;
import ru.news.tagil.R;

/**
 * Created by Alexander on 05.08.13.
 */
public class mainFrameJsonActivity extends mainFrameActivity implements jsonActivity {

    /**
     * Must be set in Initialize method
     */
    protected String scriptAddress;

    /**
     *  This section MUST be overriden
     * @return
     */

    protected JSONObject CreateJsonForGet() { return null; }

    @Override
    public void Set(JSONObject jsonObject) { }

    @Override
    public JSONObject Get(JSONObject jsonObject) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        try{
            asyncTaskWorker.execute(jsonObject,getString(R.string.serverAddress)+scriptAddress);
            return asyncTaskWorker.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return null;
    }


}
