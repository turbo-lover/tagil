package ru.news.tagil.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeMessage;
import ru.news.tagil.composite.compositeMessageTextArea;
import ru.news.tagil.utility.ScrollUpdateActivity;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myScrollView;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityMessages extends ScrollUpdateActivity implements View.OnClickListener {
    private compositeHeaderSimple headerSimple;
    private compositeMessageTextArea msgArea;

    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        needAutoUpdate = headerSimple.GetUpdateButtonVisibility();
    }
    public void FinishedRequest(JSONObject returned,jsonActivityMode mode) {
        super.FinishedRequest(returned, mode);
        try{
            switch (mode) {
                case ADD:
                    if(returned.getString("status").equals("ok")) {
                        UpdateButtonClicks();
                    } else {
                        Toast.makeText(this,getString(R.string.addCommentError),Toast.LENGTH_SHORT).show();
                    }
                    msgArea.eraseInput();
                    msgArea.BlockInput(true);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("FinishedRequest_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeMessage message = new compositeMessage(this);
        try {
            message.Initialize_Component((obj.getString("sender_login").equals(preferencesWorker.get_login()))?
                    R.layout.composite_message_out:R.layout.composite_message_in);
            String[] s = obj.getString("sent_time").split(" ");
            message.Set(obj.getString("sender_login"),obj.getString("msg_text"),s[0],s[1]);
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
                compositeMessage c = (compositeMessage) container.getChildAt(container.getChildCount() - 1);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("interlocutor",searchStr);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        headerSimple.SetHeaderButtonsListener(this);
        msgArea.SetEventListeners(this);
    }
    @Override
    protected void SetCompositeElements() {
        headerSimple.Set(getString(R.string.dialogText));
        headerSimple.UpdateWeather(weatherToday, weatherTomorow);
        header.addView(headerSimple);
        footer.addView(msgArea);
    }

    private JSONObject CreateJsonForAdd() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("sender_login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("receiver_login",searchStr);
            jo.put("msg_text",msgArea.GetText().toString());
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
            jo.put("message_count",GET_COUNT);
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("interlocutor",searchStr);
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeMessage c = (compositeMessage) container.getChildAt(0);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }

    @Override
    public void onScrollHitBottom(myScrollView scrollView, int x, int y, int oldx, int oldy) { }
    @Override
    public void onScrollHitTop(myScrollView myScrollView, int l, int t, int oldl, int oldt) {
        if( container.getChildCount() == totalCount) {
            return; }
        new myAsyncTaskWorker(this, jsonActivityMode.GET).execute(CreateJsonForGet(),
                getString(R.string.serverAddress)+scriptAddress);
    }

    @Override
    protected void InitializeComponent(){
        super.InitializeComponent();
        headerSimple = new compositeHeaderSimple(this);
        msgArea = new compositeMessageTextArea(this);
        scriptAddress = getString(R.string.getMessagesUrl);
        tableName = "messages";
        searchStr = getIntent().getStringExtra("interlocutor");
        new myAsyncTaskWorker(this, jsonActivityMode.COUNT).execute(CreateJsonForGetTotalCount(preferencesWorker.get_login(), searchStr),
                getString(R.string.serverAddress)+getString(R.string.getTotalIdCountUrl));
    }
    @Override
    public void onClick(View view) {
        if(msgArea.GetText().toString().isEmpty()) {
            Toast.makeText(this,getString(R.string.emptyMsg),Toast.LENGTH_SHORT).show();
            return;
        }
        msgArea.BlockInput(false);
        new myAsyncTaskWorker(this, jsonActivityMode.ADD).execute(CreateJsonForAdd(),
                getString(R.string.serverAddress)+getString(R.string.addMessageUrl));
    }
}
