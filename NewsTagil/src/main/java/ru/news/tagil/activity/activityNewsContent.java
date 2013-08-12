package ru.news.tagil.activity;
//TODO на обновлении проверять не появились ли новые новости
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeSecondButton;
import ru.news.tagil.composite.compositeTapeContent;
import ru.news.tagil.utility.jsonActivity;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityNewsContent extends mainFrameJsonActivity implements View.OnClickListener {
    private String id;
    private boolean liked,favorite;
    private compositeTapeContent t;
    private compositeHeaderSimple h_simple;
    private compositeSecondButton csb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Set(Get(CreateJsonForGet()));
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        id = getIntent().getStringExtra("id_news");
        h_simple = new compositeHeaderSimple(this);
        t = new compositeTapeContent(this);
        csb = new compositeSecondButton(this);
        liked = IsMarked(getString(R.string.isLikedUrl));
        favorite = IsMarked(getString(R.string.isFavoriteUrl));
        scriptAddress = getString(R.string.getNewsUrl);
    }

    private void SetBgColor() {
        csb.SetFavoriteBg((favorite) ? getResources().getColor(R.color.Orange) : getResources().getColor(R.color.lightGray));
        csb.SetLikeBg((liked)?getResources().getColor(R.color.Orange):getResources().getColor(R.color.lightGray));
    }
    private boolean IsMarked(String scriptAddress){
        boolean b = false;
        try{
            this.scriptAddress = scriptAddress;
            JSONObject jo = Get(CreateJsonForMarks());
            if(jo.getString("status").equals("ok")){
                if(jo.getString("result").equals("1")) {
                    b = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("IsLiked_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return b;
    }
    @Override
    protected void SetEventListeners() {
        csb.SetEventListeners(this);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.News));
        SetBgColor();
        container.addView(t);
        header.addView(h_simple);
        footer.addView(csb);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("id_news", id);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return sends_data;
    }

    private JSONObject CreateJsonForMarks() {
        JSONObject sends_data = CreateJsonForGet();
        try {
            sends_data.put("login",preferencesWorker.get_login());
            sends_data.put("pass",preferencesWorker.get_pass());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForMarks_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return sends_data;
    }

    @Override
    public void Set(JSONObject obj) {
        Intent i = getIntent();
        try {
            JSONObject result = obj.getJSONObject("result");
            byte[] e = result.getString("news_image").getBytes();
            byte[] imgbyte = Base64.decode(e,0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            t.Set(i.getStringExtra("header"),i.getStringExtra("time"),i.getStringExtra("date"),
                    result.getString("news_text"),bmp);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Set_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like:
                if(ToggleMarked(getString(R.string.toggleLikeUrl))) {
                    liked = !liked;
                    SetBgColor();
                }
                break;
            case R.id.elect:
                if(ToggleMarked(getString(R.string.toggleFavoriteUrl))) {
                    favorite = !favorite;
                    SetBgColor();
                }
                break;
            case R.id.comment: {
                Intent i = new Intent(this,activityCommentNews.class);
                i.putExtra("id_news",id);
                startActivity(i);
                }
                break;
        }
    }

    private boolean ToggleMarked(String scriptAddress) {
        try{
            this.scriptAddress = scriptAddress;
            JSONObject jo = Get(CreateJsonForMarks());
            if(jo.getString("status").equals("ok")) {
                 return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ToggleMarked_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return false;
    }

}
