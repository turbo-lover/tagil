package ru.news.tagil.utility;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;

/**
 * Created by Alexander on 01.08.13.
 */
public class ScrollUpdateActivity extends mainFrameJsonActivity implements updateListActivity,onScrollViewChangedListener {
    protected int totalCount;
    protected String tableName; //Must be set in Initialize method
    protected String searchStr;

    // This method MUST be overriden
    protected View CreateViewToAdd(JSONObject obj) { return null; }
    protected JSONObject CreateJsonForGetNew() { return null; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Set(Get(CreateJsonForGet()), false);
    }

    protected void SetEventListeners() {
        scrollView.setListener(this);
    }

    @Override
    public void Set(JSONObject jsonObject, boolean insertAtStart) {
        if(jsonObject == null) {
            return;
        }
        try{
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray arr = jsonObject.getJSONArray("result");
            for (int i = 0; i < arr.length();i++) {
                JSONObject obj =  arr.getJSONObject(i);
                View v = CreateViewToAdd(obj);
                if(insertAtStart){
                    container.addView(v,0+i);
                } else {
                    container.addView(v); }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("SET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public int GetTotalCount(String extra1,String extra2) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try{
            jo = new JSONObject();
            jo.put("table_name",tableName);
            if(extra1 != null) {
                jo.put("extra",extra1);
                if(tableName == "messages") {
                    jo.put("extra2",extra2);
                }
            }
            asyncTaskWorker.execute(jo,getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
            jo = asyncTaskWorker.get();
            return Integer.parseInt(jo.getString("result"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_TOTAL_COUNT_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return 0;
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(container.getChildCount() == totalCount) {
            return; }
        Set(Get(CreateJsonForGet()),false);
    }

    @Override
    public void UpdateButtonClicks() {
        String extra1 = (tableName == "news"|| tableName == "adverts")?null:preferencesWorker.get_login();
        if(tableName == "comments") {
            extra1 = searchStr; }
        String extra2 =  (tableName == "messages")?searchStr:null;
        int new_count = GetTotalCount(extra1,extra2);
        if(new_count > totalCount) {
            totalCount = new_count;
            Set(Get(CreateJsonForGetNew()),true);
        }
    }
}
