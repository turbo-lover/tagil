package ru.news.tagil.activity;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeContactPreview;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeMyProfileSelector;
import ru.news.tagil.utility.ScrollUpdateActivity;

public class activityContact extends ScrollUpdateActivity implements View.OnClickListener{
    private compositeHeader h;
    private compositeMyProfileSelector s;
    private compositeFirstButton cfb;

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
        h.UpdateWeather(weatherToday, weatherTomorow);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        header.addView(h);
        selector.addView(s,p);
        footer.addView(cfb);
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
        totalCount = GetTotalCount(null,null);
    }
    @Override
    public void onClick(View view) {
        compositeContactPreview preview = (compositeContactPreview) view;
        Intent i = new Intent(this,activityProfile.class);
        i.putExtra("login",preview.GetLogin());
        startActivity(i);
    }
    @Override
    public void SearchButtonClicks(String txt) {
        this.ClearContainer();
        tableName = "users_search";
        searchStr = txt;
        totalCount = GetTotalCount(txt,null);
        Set(Get(CreateJsonForGet()),false);
    }
}