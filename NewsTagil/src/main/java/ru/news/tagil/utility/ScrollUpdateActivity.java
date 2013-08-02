package ru.news.tagil.utility;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;

/**
 * Created by Alexander on 01.08.13.
 */
public class ScrollUpdateActivity extends Activity implements updateListActivity,onScrollViewChangedListener,onUpdateClickListener {
    protected int totalCount;
    protected String scriptAddress,tableName;
    protected LinearLayout container;

    // This section MUST be ovveriden
    protected View CreateViewToAdd(JSONObject obj) { return null; }
    protected JSONObject CreateJsonForGet() { return null; }
    protected JSONObject CreateJsonForGetNew() { return null; }
    protected void InitializeComponent() {}
    protected void SetEventListeners() {}
    protected void SetCompositeElements() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        SetCompositeElements();
        SetEventListeners();
        Get(CreateJsonForGet(),scriptAddress);
    }

    @Override
    public void Set(JSONObject jsonObject, boolean insertAtStart) {
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
    public void Get(JSONObject jsonObject,String scriptAddress) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        if(container.getChildCount() == totalCount) {
            return; }
        try{
            asyncTaskWorker.execute(jsonObject,getString(R.string.serverAddress)+scriptAddress);
            Set(asyncTaskWorker.get(), false);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public void GetNew(JSONObject jsonObject, String scriptAddress) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        try {
            asyncTaskWorker.execute(jsonObject,getString(R.string.serverAddress)+scriptAddress);
            Set(asyncTaskWorker.get(),true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_NEW_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public int GetTotalCount(String tableName,String login) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        JSONObject jo;
        try{
            jo = new JSONObject();
            jo.put("table_name",tableName);
            if(login != null) {
                jo.put("login",login); }
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
        Get(CreateJsonForGet(),scriptAddress);
    }

    @Override
    public void UpdateButtonClicks() {
        int new_count = GetTotalCount(tableName,null);
        if(new_count > totalCount) {
            totalCount = new_count;
            GetNew(CreateJsonForGetNew(),scriptAddress);
        }
    }
}
