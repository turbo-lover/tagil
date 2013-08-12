package ru.news.tagil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import ru.news.tagil.utility.mainFrameJsonActivity;

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
        scriptAddress = getString((i.getExtras().isEmpty())?R.string.addAdvertUrl:R.string.updateAdvertUrl);
    }
    @Override
    protected void SetCompositeElements() {
        h_simple.Set(getString(R.string.createAdvertText));
        if(!i.getExtras().isEmpty()) {
            makeAds.Set(i.getStringExtra("advert_header"),i.getStringExtra("advert_text"));
            makeAds.SetImg((Bitmap)i.getParcelableExtra("advert_image"));
        }
        header.addView(h_simple);
        container.addView(makeAds);
    }
    @Override
    protected void SetEventListeners() {
        makeAds.SetEventListeners(this);
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
            if(!i.getExtras().isEmpty()) {
                jo.put("id_advert",i.getStringExtra("id_advert"));
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

    private void Send() {
        if(makeAds.GetContentText().isEmpty() || makeAds.GetHeader().isEmpty()) {
            Toast.makeText(this,getString(R.string.SetFiledsWarning),Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            JSONObject jo =  Get(CreateJsonForGet());
            if(jo.getString("status").equals("ok")){
                Intent i = new Intent(this,activityMyAds.class);
                startActivity(i);
            } else {
                Toast.makeText(this,jo.getString("errormsg"),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Send_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
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
