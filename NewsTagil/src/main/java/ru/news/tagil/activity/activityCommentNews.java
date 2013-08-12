package ru.news.tagil.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeComment;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeMessage;
import ru.news.tagil.composite.compositeMessageTextArea;
import ru.news.tagil.utility.ScrollUpdateActivity;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityCommentNews  extends ScrollUpdateActivity implements View.OnClickListener{
    private compositeHeaderSimple h_simple;
    private compositeMessageTextArea msgArea;

    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        Set(Get(CreateJsonForGetNew()),true);
    }

    @Override
    protected void InitializeComponent(){
        super.InitializeComponent();
        h_simple = new compositeHeaderSimple(this);
        msgArea = new compositeMessageTextArea(this);
        scriptAddress = getString(R.string.getCommentsUrl);
        tableName = "comments";
        searchStr = getIntent().getStringExtra("id_news");
        totalCount = GetTotalCount(searchStr,null);
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeComment message = new compositeComment(this);
        try {
            String[] s = obj.getString("comment_time").split(" ");
            message.Set(obj.getString("login"),obj.getString("comment_text"),s[0],s[1]);
            byte[] e = obj.getString("image").getBytes();
            byte[] imgbyte = Base64.decode(e, 0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            message.SetAvatar(bmp);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return message;
    }
    @Override
    protected JSONObject CreateJsonForGetNew() {
        JSONObject jo = new JSONObject();
        try {
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeComment c = (compositeComment) container.getChildAt(0);
                send_time = c.getDateTime();
            }
            jo.put("comment_time",send_time);
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("id_news",searchStr);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("comment_count",GET_COUNT);
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("id_news",searchStr);
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeComment c = (compositeComment) container.getChildAt(container.getChildCount() - 1);
                send_time = c.getDateTime();
            }
            jo.put("comment_time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    private JSONObject CreateJsonForAdd() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("id_news",searchStr);
            jo.put("comment_text",msgArea.GetText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    private void AddNew() {
        try{
            scriptAddress = getString(R.string.addCommentUrl);
            JSONObject jo = Get(CreateJsonForAdd());
            if(jo.getString("status").equals("ok")) {
                scriptAddress = getString(R.string.getCommentsUrl);
                UpdateButtonClicks();
            } else {
                Toast.makeText(this,getString(R.string.addCommentError),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("AddNew_Exception", ex.getMessage() + "\n\n" + ex.toString());
        } finally {
            scriptAddress = getString(R.string.getCommentsUrl);
            msgArea.eraseInput();
            msgArea.BlockInput(true);
        }
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        h_simple.SetHeaderButtonsListener(this);
        msgArea.SetEventListeners(this);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.commentText));
        header.addView(h_simple);
        footer.addView(msgArea);
    }
    @Override
    public void onClick(View view) {
        if(msgArea.GetText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.emptyMsg), Toast.LENGTH_SHORT).show();
            return;
        }
        msgArea.BlockInput(false);
        AddNew();
    }
}
