package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeMakeAds;
import ru.news.tagil.utility.imageGetter;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myAsyncTaskWorker;

/**
 * Created by Alexander on 23.07.13.
 */
public class activityMakeAds extends mainFrameJsonActivity implements View.OnClickListener {
    private static final int SELECT_PICTURE = 1;
    private compositeHeaderSimple h_simple;
    private compositeMakeAds makeAds;
    private imageGetter imgGetter;
    private Intent i;
    @Override
    protected void InitializeComponent(){
        super.InitializeComponent();
        h_simple = new compositeHeaderSimple(this);
        makeAds = new compositeMakeAds(this);
        imgGetter = new imageGetter(this);
        i = getIntent();
        scriptAddress = getString((i.getExtras() == null || i.getExtras().isEmpty())?R.string.addAdvertUrl:R.string.updateAdvertUrl);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.createAdvertText));
        h_simple.SetUpdateButtonVisibility(false);
        h_simple.UpdateWeather(weatherToday, weatherTomorow);
        Bundle b =i.getExtras();
        if(b != null ) {
            if(!i.getExtras().isEmpty()) {
                makeAds.Set(i.getStringExtra("advert_header"),i.getStringExtra("advert_text"));
                makeAds.SetImg((Bitmap)i.getParcelableExtra("advert_image"));
            }
        }
        header.addView(h_simple);
        container.addView(makeAds);
    }
    @Override
    protected void SetEventListeners() {
        makeAds.SetEventListeners(this);
        h_simple.SetHeaderButtonsListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_make_ads_img:
                FindImage();
                break;
            case R.id.composite_make_ads_sent:
                Send();
                break;
        }
    }
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("login",preferencesWorker.get_login());
            jo.put("pass",preferencesWorker.get_pass());
            jo.put("advert_header",makeAds.GetHeader());
            jo.put("advert_text",makeAds.GetContentText());
            Bundle bundle = i.getExtras();
            if(bundle != null) {
                if(!i.getExtras().isEmpty()) {
                    jo.put("id_advert",i.getStringExtra("id_advert"));
                }
            }
            Bitmap bmp = makeAds.GetImg();
            byte[] b = null;
            if(bmp != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                b = Base64.encode(stream.toByteArray(),0);
            }
            jo.put("advert_image",(bmp == null)?"NULL":new String(b,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CreateJsonForGet_Exception", e.getMessage() + "\n\n" + e.toString());
        }
        return jo;
    }
    @Override
    public void FinishedRequest(JSONObject returned,jsonActivityMode mode) {
        try{
            switch (mode) {
                case GET:
                    if(OkResponse(returned)) {
                        Intent i = new Intent(this,activityMyAds.class);
                        startActivity(i);
                        finish();
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("FinishedRequest_Exception", ex.getMessage() + "\n\n" + ex.toString());
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

    private void Send() {
        if(makeAds.GetContentText().isEmpty() || makeAds.GetHeader().isEmpty()) {
            Toast.makeText(this,getString(R.string.SetFiledsWarning),Toast.LENGTH_SHORT).show();
            return;
        }
        new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJsonForGet(),
                getString(R.string.serverAddress) + scriptAddress);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                makeAds.SetImg(imgGetter.getImg(data,makeAds.GetImgWidth(),makeAds.GetImgHeight()));
            }
        }
    }

    private void FindImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
    }
}
