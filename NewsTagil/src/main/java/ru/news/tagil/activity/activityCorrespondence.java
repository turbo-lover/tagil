package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeInterlocutor;
import ru.news.tagil.utility.ScrollUpdateActivity;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityCorrespondence extends ScrollUpdateActivity implements View.OnClickListener {
    private compositeHeaderSimple headerSimple;
    private compositeFirstButton cfb;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        needAutoUpdate = headerSimple.GetUpdateButtonVisibility();
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityMessages.class);
        compositeInterlocutor interlocutor = (compositeInterlocutor) view;
        i.putExtra("interlocutor",interlocutor.GetInterlocutor());
        startActivity(i);
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeInterlocutor interlocutor = new compositeInterlocutor(this);
        try{
            byte[] e = obj.getString("img").getBytes();
            byte[] imgbyte = Base64.decode(e, 0);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            interlocutor.Set(obj.getString("login"),obj.getString("last_msg_login"),obj.getString("last_msg_text"),bmp);
            interlocutor.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return interlocutor;
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }

    @Override
    protected JSONObject CreateJsonForGetNew() { return null; } //Not implemented
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        headerSimple = new compositeHeaderSimple(this);
        cfb = new compositeFirstButton(this);
        scriptAddress = getString(R.string.getInterlocutorsUrl);
        tableName = "interlocutors";
        totalCount = GetTotalCount(preferencesWorker.get_login(),null);
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        headerSimple.SetHeaderButtonsListener(this);
    }
    @Override
    protected void SetCompositeElements() {
        headerSimple.Set(getString(R.string.dialogText));
        headerSimple.SetUpdateButtonVisibility(false);
        headerSimple.UpdateWeather(weatherToday, weatherTomorow);
        header.addView(headerSimple);
        footer.addView(cfb);
    }
}
