package ru.news.tagil.activity;
//TODO на обновлении проверять не появились ли новые новости
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
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeSecondButton;
import ru.news.tagil.composite.compositeTapeContent;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;

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
        new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJsonForGet(),
                getString(R.string.serverAddress) + getString(R.string.getNewsUrl));
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        id = getIntent().getStringExtra("id_news");
        h_simple = new compositeHeaderSimple(this);
        t = new compositeTapeContent(this);
        csb = new compositeSecondButton(this);
        CheckMarks();
        scriptAddress = getString(R.string.getNewsUrl);
    }

    private void CheckMarks() {
        if(is_authorized) {
            new myAsyncTaskWorker(this,jsonActivityMode.IS_LIKED).execute(CreateJsonForMarks(),
                    getString(R.string.serverAddress) + getString(R.string.isLikedUrl));
            new myAsyncTaskWorker(this,jsonActivityMode.IS_FAVORITE).execute(CreateJsonForMarks(),
                    getString(R.string.serverAddress) + getString(R.string.isFavoriteUrl));
        }
    }
    private void SetBgColor() {
        csb.SetFavoriteBg((favorite) ? getResources().getColor(R.color.Orange) : getResources().getColor(R.color.lightGray));
        csb.SetLikeBg((liked)?getResources().getColor(R.color.Orange):getResources().getColor(R.color.lightGray));
    }
    private boolean IsMarked(JSONObject jsonObject){
        boolean b = false;
        try{
            if(jsonObject.getString("status").equals("ok")){
                if(jsonObject.getString("result").equals("1")) {
                    b = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("IsMarked_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return b;
    }
    @Override
    protected void SetEventListeners() {
        csb.SetEventListeners(this);
        h_simple.SetHeaderButtonsListener(this);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.News));
        h_simple.UpdateWeather(weatherToday, weatherTomorow);
        h_simple.SetUpdateButtonVisibility(false);
        container.addView(t);
        header.addView(h_simple);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        footer.addView(csb,p);
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
    @Override
    protected void onResume() {
        super.onResume();
        CheckMarks();
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
    public void FinishedRequest(JSONObject returned,jsonActivityMode mode) {
        try{
            switch (mode) {
                case GET:
                    Set(returned);
                    break;
                case IS_LIKED:
                    liked = IsMarked(returned);
                    SetBgColor();
                    break;
                case IS_FAVORITE:
                    favorite = IsMarked(returned);
                    SetBgColor();
                    break;
                case TOGGLE_LIKE:
                    if(OkResponse(returned)) {
                        liked = !liked;
                        SetBgColor();
                    }
                    break;
                case TOGGLE_FAVORITE:
                    if(OkResponse(returned)) {
                        favorite = !favorite;
                        SetBgColor();
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("FinishedRequest_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like:
                if(is_authorized) {
                    new myAsyncTaskWorker(this,jsonActivityMode.TOGGLE_LIKE).execute(CreateJsonForMarks(),
                            getString(R.string.serverAddress) + getString(R.string.toggleLikeUrl));
                } else {
                   LogIn();
                }
                break;
            case R.id.elect:
                if(is_authorized) {
                    new myAsyncTaskWorker(this,jsonActivityMode.TOGGLE_FAVORITE).execute(CreateJsonForMarks(),
                            getString(R.string.serverAddress) + getString(R.string.toggleFavoriteUrl));
                } else {
                    LogIn();
                }
                break;
            case R.id.comment: {
                if(is_authorized) {
                    Intent i = new Intent(this,activityCommentNews.class);
                    i.putExtra("id_news",id);
                    startActivity(i);

                } else {
                    LogIn();
                }
                break;
            }
        }
    }

    private boolean OkResponse(JSONObject jsonObject) {
        try{
            if(jsonObject.getString("status").equals("ok")) {
                 return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ToggleMarked_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return false;
    }

}
