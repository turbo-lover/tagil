package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeAdsContent;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.mainFrameJsonActivity;

/**
 * Created by Alexander on 30.07.13.
 */
public class activityReadAds extends mainFrameJsonActivity implements View.OnClickListener {
    private compositeHeaderSimple h_simple;
    private compositeAdsContent content;
    private Intent i;
    private boolean isMine,isFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Set(Get(CreateJsonForGet()));
    }
    @Override
    protected void SetEventListeners() {
        content.SetEventListeners(this);
        h_simple.SetHeaderButtonsListener(this);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        scriptAddress = getString(R.string.getAdvertTextUrl);
        i = getIntent();
        isMine = i.getStringExtra("login").equals(preferencesWorker.get_login());
        isFavorite = i.getBooleanExtra("favorite",false);
        h_simple = new compositeHeaderSimple(this);
        content = new compositeAdsContent(this);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.advertText));
        h_simple.UpdateWeather(weatherToday, weatherTomorow);
        h_simple.SetUpdateButtonVisibility(false);
        content.SetButtonsTxt(getString((isMine)?R.string.editText:R.string.startDialogText),
                getString((isMine)?R.string.delText:R.string.favoriteText));
        header.addView(h_simple);
        container.addView(content);
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("id_advert",i.getStringExtra("id_advert"));
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    public void Set(JSONObject jsonObject) {
        try {
            if(jsonObject.getString("status").equals("error")){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return ;
            }
            content.Set(i.getStringExtra("title"),jsonObject.getString("result"),(Bitmap)i.getParcelableExtra("img"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_ads_content_first_button:
                if(isMine) {
                    GoToEdit();
                } else {
                    StartDialog();
                }
                break;
            case R.id.read_ads_content_second_button:
                if(isMine) {
                    DeleteAds();
                } else {
                    ToggleFavorite();
                }
                break;
        }
    }

    private void ToggleFavorite() {
        try{
            scriptAddress = getString(R.string.toggleFavoriteUrl);
            JSONObject jo = Get(CreateJsonForGet());
            if(jo.getString("status").equals("ok")) {
                isFavorite = !isFavorite;
                Toast.makeText(this,getString((isFavorite)?R.string.advertAddedToFavorite:R.string.advertDeletedFromFavorite),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ToggleFavorite_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    private void DeleteAds() {
        try{
            scriptAddress = getString(R.string.delAdvertUrl);
            JSONObject jo = Get(CreateJsonForGet());
            if (jo.getString("status").equals("ok")) {
                Intent i = new Intent(this,activityAds.class);
                startActivity(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("ToggleFavorite_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
    }

    private void StartDialog() {
        Intent i = new Intent(this,activityMessages.class);
        i.putExtra("interlocutor",this.i.getStringExtra("login"));
        startActivity(i);
    }

    private void GoToEdit() {
        Intent i = new Intent(this,activityMakeAds.class);
        i.putExtra("advert_text",content.getText());
        i.putExtra("advert_header",content.getHeader());
        i.putExtra("advert_image",content.getImg());
        i.putExtra("id_advert",this.i.getStringExtra("id_advert"));
        startActivity(i);
    }
}
