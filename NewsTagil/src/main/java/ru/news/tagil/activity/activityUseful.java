package ru.news.tagil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import ru.news.tagil.R;

/**
 * Created by turbo_lover on 19.07.13.
 */
public class activityUseful extends activityNewsPreview {
    @Override
    protected void SetCompositeElements() {
        super.SetCompositeElements();
        h.Set(getString(R.string.usefulText),"","");
    }
    @Override
    protected void InitializeComponent(){
        super.InitializeComponent();
        tableName = "useful_news";
        totalCount = GetTotalCount(null);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try{
            jo = super.CreateJsonForGet();
            jo.put("useful",true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected JSONObject CreateJsonForGetNew() {
        JSONObject jo = new JSONObject();
        try {
            jo = super.CreateJsonForGetNew();
            jo.put("useful",true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    public void SearchButtonClicks(String txt) {
        tableName = "useful_news";
        super.SearchButtonClicks(txt);
    }
}
