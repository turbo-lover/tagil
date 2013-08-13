package ru.news.tagil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.utility.jsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;

public class activityMain extends Activity implements View.OnClickListener,jsonActivity {
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_holder);
        rl.setOnClickListener(this);
        i  = new Intent(this,activityNewsPreview.class);
        Set(Get(new JSONObject()));
    }
    @Override
    public void onClick(View view) {
        startActivity(i);
    }

    @Override
    public JSONObject Get(JSONObject jsonObject) {
        myAsyncTaskWorker asyncTaskWorker = new myAsyncTaskWorker();
        try{
            asyncTaskWorker.execute(jsonObject,getString(R.string.serverAddress)+ getString(R.string.getWeatherUrl));
            return asyncTaskWorker.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("GET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return null;
    }

    @Override
    public void Set(JSONObject jsonObject) {
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
                JSONArray temp_from = obj.getJSONArray("temp_from");
                JSONArray temp_to = obj.getJSONArray("temp_to");
                if(i == 0) {
                    this.i.putExtra("weather_today",temp_from.getString(1)+"-"+temp_to.getString(1));
                } else {
                    this.i.putExtra("weather_tomorrow",temp_from.getString(1)+"-"+temp_to.getString(1));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("SET_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }
}
