package ru.news.tagil.activity;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeContactContent;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.imageGetter;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myPreferencesWorker;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class activityProfile extends mainFrameJsonActivity implements View.OnClickListener {
    compositeContactContent contactContent;
    compositeHeaderSimple headerSimple;
    Boolean isMy;
    myPreferencesWorker pw;
    String current_login;
    private final int SELECT_PICTURE = 666;
    private imageGetter imgGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scriptAddress = getString(R.string.getProfile);

        GetProfile(current_login);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();

        Intent intent = getIntent();

        pw = new myPreferencesWorker(this);

        current_login = intent.getStringExtra("login");
        // задается в compositeMyProfileSelector
        isMy = current_login.equals(pw.get_login()) ;

        contactContent = new compositeContactContent(this,isMy);
        contactContent.SetName(current_login);
        headerSimple = new compositeHeaderSimple(this);
        imgGetter = new imageGetter(this);
    }

    private void GetProfile(String login) {
        JSONObject obj  = new JSONObject();
        try {
            obj.put("login",login);
            obj = Get(obj);

/*status:,result:{looking_for,purpose_of_seeking,hobby,about_me,marriage,favorite_music,selected_pic_id,images:[{id,image}*/
            if(obj.getString("status").equals("ok")) {
                JSONObject result = obj.getJSONObject("result");

                String seek = result.getString("looking_for");
                contactContent._setSeek(seek);
                contactContent._setPurpose(result.getString("purpose_of_seeking"));
                contactContent._setMarriage(result.getString("marriage"));

                contactContent._setHobby(result.getString("hobby"));
                contactContent._setMusic(result.getString("favorite_music"));
                contactContent._setAbout(result.getString("about_me"));
                String avatar_id = result.getString("selected_pic_id");
                try {
                     JSONArray arr  = result.getJSONArray("images");
                     for(int i = 0; i<arr.length(); i++) {
                         JSONObject image = arr.getJSONObject(i);
                          byte[] b_image = Base64.decode( image.getString("image"),0);
                         Bitmap bmp_image = BitmapFactory.decodeByteArray(b_image,0,b_image.length);
                        String id = image.getString("id");
                         if(id.equals(avatar_id)) {
                             contactContent._setAvatar(bmp_image);
                         }
                         contactContent._putImageInHorizontalTape(bmp_image,id);
                     }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        contactContent.SetEventListeners(this);
    }

    @Override
    protected void SetCompositeElements() {
        super.SetCompositeElements();
        container.addView(contactContent);
        header.addView(headerSimple);
    }



    private void sendResult(String content, String category, int id) {

        JSONObject jo = new JSONObject();
        try {
            jo.put(category, content);
            jo = changePersonalInfo(jo);

            if(jo.getString("status").equals("ok")) {
                Toast.makeText(this, getString(R.string.personalInfoChanged), Toast.LENGTH_LONG).show();
                switch(id) {
                    case R.id.contact_hobby:
                        contactContent._setHobby(content);
                        break;
                    case R.id.contact_music:
                        contactContent._setMusic(content);
                        break;
                    case R.id.contact_about:
                        contactContent._setAbout(content);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //{login,pass,looking_for?,purpose_of_seeking?,marriage?,hobby?,favorite_music?,about_me?};
    private void sendResult(List selection, String category, int id) {
        JSONObject jo = new JSONObject();
        try {
            jo.put(category, selection.get(0));
            jo = changePersonalInfo(jo);

            if(jo.getString("status").equals("ok")) {
                Toast.makeText(this, getString(R.string.personalInfoChanged), Toast.LENGTH_LONG).show();
                switch(id) {
                    case R.id.contact_seek:
                        contactContent._setSeek(selection.get(0).toString());
                        break;
                    case R.id.contact_purpose_for_seeking:
                        contactContent._setPurpose(selection.get(0).toString());
                        break;
                    case R.id.contact_marriage:
                        contactContent._setMarriage(selection.get(0).toString());
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //hobby?,favorite_music?,about_me?
    private Dialog CreateDialog(String title, int income_id) {
        final int id = income_id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater li = getLayoutInflater();
        RelativeLayout  rl = (RelativeLayout) li.inflate(R.layout.dialog_layout, null);

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        TextView tv = (TextView) rl.findViewById(R.id.dialog_header);
        final EditText text = (EditText) rl.findViewById(R.id.dialog_text);

        tv.setText(title);
        builder.setView(rl);
        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (id) {
                    case R.id.contact_hobby:
                        sendResult(text.getText().toString(), "hobby", R.id.contact_hobby);
                        break;
                    case R.id.contact_music:
                        sendResult(text.getText().toString(), "favorite_music", R.id.contact_music);
                        break;
                    case R.id.contact_about:
                        sendResult(text.getText().toString(), "about_me", R.id.contact_about);
                        break;
                }
            }
        });
        return builder.create();
    }

    private Dialog CreateDialog(CharSequence[] params,String title,int income_id) {
        final List currentSelection = new ArrayList(); // только так можно загрузить сраные индексы выборки)
        currentSelection.add(""+(params.length-1));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final int id = income_id;

        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(id) {
                    case R.id.contact_seek :
                        sendResult(currentSelection, "looking_for",R.id.contact_seek);
                        break;
                    case R.id.contact_purpose_for_seeking:
                        sendResult(currentSelection, "purpose_of_seeking", R.id.contact_purpose_for_seeking);
                        break;
                    case R.id.contact_marriage:
                        sendResult(currentSelection, "marriage", R.id.contact_marriage);
                        break;
                }
            }
        });
        builder.setSingleChoiceItems(params,params.length-1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentSelection.clear();
                currentSelection.add(""+i);
            }
        });
        return builder.create();
    }

    private JSONObject changePersonalInfo(JSONObject jo) {
        scriptAddress = getString(R.string.addPersonalInfo);
        try {
            jo.put("login",pw.get_login());
            jo.put("pass",pw.get_pass());

            return Get(jo);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void sendImage(Bitmap bmp) {
        scriptAddress = getString(R.string.addUserPic);
        JSONObject jo = new JSONObject();
        try {
            byte[] b = null;
            if(bmp != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 99, stream);
                b = Base64.encode(stream.toByteArray(),0);
            }
            jo.put("image",(bmp == null)?"NULL":new String(b,"UTF-8"));
            jo.put("login",pw.get_login());
            jo.put("pass",pw.get_pass());

            jo = Get(jo);

            if(jo.getString("status").equals("ok")) {
                Toast.makeText(this,getString(R.string.imageAddedSuccess),Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                sendImage(imgGetter.getImg(data,150,150));
            }
        }
    }
    private void FindImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        Toast.makeText(this,view.getClass().toString(),Toast.LENGTH_LONG ).show() ;
        if(isMy) {
            Dialog d  = new Dialog(this);
            switch (id) {
                // обработка нажатий изменений профиля
                case R.id.contact_seek :
                    d = CreateDialog(
                            getResources().getStringArray(R.array.seek),
                            getString(R.string.seek),R.id.contact_seek);
                    break;
                case R.id.contact_purpose_for_seeking:
                    d = CreateDialog(
                            getResources().getStringArray(R.array.purpose),
                            getString(R.string.purpose_for_seek), R.id.contact_purpose_for_seeking );
                    break;
                case R.id.contact_marriage:
                    d = CreateDialog(
                            getResources().getStringArray(R.array.marriage),
                            getString(R.string.marriage),R.id.contact_marriage);
                    break;
                case R.id.contact_hobby:
                    d = CreateDialog(getString(R.string.hobby),R.id.contact_hobby);
                    break;
                case R.id.contact_music:
                    d = CreateDialog(getString(R.string.favorite_music),R.id.contact_music);
                    break;
                case R.id.contact_about:
                    d = CreateDialog(getString(R.string.about_me),R.id.contact_about);
                    break;
                //обработка нажатия добавления фотографии
                case R.id.add_photo_button:
                    FindImage();
                    return;
            }
            d.show();
        }
    }

}