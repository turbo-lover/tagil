package ru.news.tagil.activity;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeContactPreview;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeMyProfileSelector;
import ru.news.tagil.utility.ScrollUpdateActivity;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.myAsyncTaskWorker;

public class activityContact extends ScrollUpdateActivity implements View.OnClickListener{
    private compositeHeader h;
    private compositeMyProfileSelector s;
    private compositeFirstButton cfb;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        needAutoUpdate = h.GetUpdateButtonVisibility();
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeContactPreview preview = new compositeContactPreview(this);
        try {
            byte[] e = obj.getString("img").getBytes();
            byte[] imgbyte = Base64.decode(e, 0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            preview.Set(obj.getString("login"),bmp);
            preview.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return preview;
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("users_count",GET_COUNT);
            if(!h.GetSearchString().isEmpty()) {
                jo.put("search_pattern",h.GetSearchString());
            }
            if(container.getChildCount() > 0) {
                compositeContactPreview c = (compositeContactPreview) container.getChildAt(container.getChildCount() - 1);
                jo.put("start_login",c.GetLogin());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected  void SetCompositeElements() {
        h.Set(getString(R.string.contactText),"","");
        h.SetUpdateButtonVisibility(false);
        h.UpdateWeather(weatherToday, weatherTomorow);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        header.addView(h);
        selector.addView(s,p);
        footer.addView(cfb,rlp);
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        h.SetHeaderButtonsListener(this);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        h = new compositeHeader(this);
        s = new compositeMyProfileSelector(this);
        cfb = new compositeFirstButton(this);
        scriptAddress = getString(R.string.getUsersUrl);
        tableName = "users";
        new myAsyncTaskWorker(this, jsonActivityMode.COUNT).execute(CreateJsonForGetTotalCount(null, null),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
    @Override
    public void onClick(View view) {
        try {
        compositeContactPreview preview = (compositeContactPreview) view;
        Intent i = new Intent(this,activityProfile.class);
        i.putExtra("login",preview.GetLogin());
        startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void SearchButtonClicks(String txt) {
        this.ClearContainer();
        searchStr = txt;
        new myAsyncTaskWorker(this,jsonActivityMode.COUNT).execute(CreateJsonForGetTotalCount(txt,null),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
}