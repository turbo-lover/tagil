package ru.news.tagil.utility;

import android.util.Log;
import android.view.View;
import org.json.JSONObject;

import ru.news.tagil.R;

/**
 * Created by Alexander on 05.08.13.
 */
public class mainFrameJsonActivity extends mainFrameActivity implements jsonActivity {
    protected String scriptAddress; //Must be set in Initialize method

    // This section MUST be overriden
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
