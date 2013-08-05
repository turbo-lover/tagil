package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.composite.compositeMakeAds;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

/**
 * Created by Alexander on 23.07.13.
 */
public class activityMakeAds extends Activity implements View.OnClickListener {
    private static final int SELECT_PICTURE = 1;
    private LinearLayout headerLL,contentLL;
    private compositeHeaderSimple header;
    private compositeMakeAds makeAds;
    private myPreferencesWorker preferencesWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        SetCompositeElements();
    }

    private void SetCompositeElements() {
        header.Set(getString(R.string.createAdvertText));
        makeAds.SetEventListeners(this);
        headerLL.addView(header);
        contentLL.addView(makeAds);
    }

    private void InitializeComponent() {
        setContentView(R.layout.activity_make_ads);
        header = new compositeHeaderSimple(this);
        makeAds = new compositeMakeAds(this);
        headerLL = (LinearLayout) findViewById(R.id.activity_make_ads_header);
        contentLL = (LinearLayout) findViewById(R.id.activity_make_ads_content);
        preferencesWorker = new myPreferencesWorker(this);
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

    private void Send() {
        if(makeAds.GetContentText().isEmpty() || makeAds.GetHeader().isEmpty()) {
            Toast.makeText(this,getString(R.string.SetFiledsWarning),Toast.LENGTH_SHORT).show();
            return;
        }
        myAsyncTaskWorker worker = new myAsyncTaskWorker();
        JSONObject sends_data = new JSONObject();
        try {
            sends_data.put("login",preferencesWorker.get_login());
            sends_data.put("pass",preferencesWorker.get_pass());
            sends_data.put("advert_header",makeAds.GetHeader());
            sends_data.put("advert_text",makeAds.GetContentText());
            Bitmap bmp = makeAds.GetImg();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] b = Base64.encode(stream.toByteArray(),0);
            sends_data.put("advert_image",new String(b,"UTF-8"));
            worker.execute(sends_data,getString(R.string.serverAddress)+getString(R.string.addAdvertUrl));
            JSONObject jo =  worker.get();
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

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath,options);
                options.inSampleSize = calculateInSampleSize(options, makeAds.GetWidth(), makeAds.GetHeight());
                options.inJustDecodeBounds = false;
                makeAds.SetImg(BitmapFactory.decodeFile(selectedImagePath,options));
            }
        }
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    private void FindImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
    }
}
