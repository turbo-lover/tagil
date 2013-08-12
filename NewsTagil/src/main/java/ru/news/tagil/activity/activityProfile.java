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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeContactContent;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.mainFrameJsonActivity;
import ru.news.tagil.utility.myPreferencesWorker;

import java.util.ArrayList;
import java.util.List;

public class activityProfile extends mainFrameJsonActivity implements View.OnClickListener{

    compositeContactContent contactContent;
    compositeHeaderSimple headerSimple;
    Boolean isMy;
    myPreferencesWorker pw;
    String current_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        // задается в compositeMyProfileSelector
        isMy = intent.getBooleanExtra("isMy",false);

        scriptAddress = getString(R.string.getProfile);
        if(isMy)
        {
            current_login = pw.get_login();
            GetProfile(current_login);

//            изменяем адресс скрипта, так как это наш профиль и мы можем его редактировать!
            scriptAddress = getString(R.string.addPersonalInfo);
            return;
        }
        // задается в activityContact
        current_login = intent.getStringExtra("login");

        GetProfile(current_login);
    }

    void GetProfile(String login) {
        JSONObject obj  = new JSONObject();
        try {
            obj.put("login",login);
            obj = Get(obj);

            /*
            * {status:"ok/denied",
            * "result": {
            *       looking_for,purpose_of_seeking,hobby,about_me,marriage,favorite_music,
            *       selected_pic_id, images:[{id,image},]
            *   }
            *
            * }
            * */
            if(obj.getString("status").equals("ok")) {
                JSONObject result = obj.getJSONObject("result");
                contactContent._setSeek(result.getString("looking_for"));
                contactContent._setPurpose(result.getString("purpose_of_seeking"));
                contactContent._setMarriage(result.getString("marriage"));
                contactContent._setHobby(result.getString("hobby"));
                contactContent._setMusic(result.getString("favorite_music"));
                contactContent._setAbout( result.getString("about_me"));
                String avatar_id = result.getString("selected_pic_id");
                try {
                     JSONArray arr  = result.getJSONArray("images");
                     for(int i = 0; i<arr.length(); i++) {
                         JSONObject image = arr.getJSONObject(i);
                          byte[] b_image = Base64.decode( image.getString("image"),0);
                         Bitmap bmp_image = BitmapFactory.decodeByteArray(b_image,0,b_image.length);

                         if(image.getString("id").equals(avatar_id)) {
                             contactContent._setAvatar(bmp_image);
                             continue;
                         }
                         contactContent._putImageInHorizontalTape(bmp_image);
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

    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();

        contactContent = new compositeContactContent(this,true);
        headerSimple = new compositeHeaderSimple(this);

        pw = new myPreferencesWorker(this);
    }

    private Dialog CreateDialog(CharSequence[] params,String title,int income_id)
    {
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
                        sendSeekResult(currentSelection);
                        break;
                    case R.id.contact_purpose_for_seeking:
                        sendPurposeResult(currentSelection);
                        break;
                    case R.id.contact_marriage:
                        sendMarriageResult(currentSelection);
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

    private void changePersonalInfo(JSONObject jo) {

        try {
            jo.put("login",pw.get_login());
            jo.put("pass",pw.get_pass());

            jo = Get(jo);

            if(jo.getString("status").equals("ok")) {
                Toast.makeText(this,getString(R.string.personalInfoChanged),Toast.LENGTH_LONG).show();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMarriageResult(List selection) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("marriage", selection.get(0));
            changePersonalInfo(jo);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendPurposeResult(List selection) {

    }

    private void sendSeekResult(List selection) {

    }

    private Dialog CreateDialog(String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(true) { //Todo поставить if(isMy) эта часть для редактирования
            Dialog d  = new Dialog(this);


            switch (view.getId()) {
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

                    CreateDialog(
                            getResources().getStringArray(R.array.marriage),
                            getString(R.string.marriage),R.id.contact_marriage);

                    break;
                case R.id.contact_hobby:
                   // LayoutInflater inflater = getLayoutInflater();
                    try {
                        LayoutInflater li = getLayoutInflater();
                        RelativeLayout  rl = (RelativeLayout) li.inflate(R.layout.dialog_layout, null);
                        RelativeLayout.LayoutParams lp =
                                new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.MATCH_PARENT);
                        TextView tv = (TextView) rl.findViewById(R.id.dialog_header);
                        d.addContentView(rl,lp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case R.id.contact_music:

                    break;
                case R.id.contact_about:

                    break;
            }
            d.show();




        }
    }
}