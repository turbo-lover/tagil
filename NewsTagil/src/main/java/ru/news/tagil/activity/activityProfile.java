package ru.news.tagil.activity;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeContactContent;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.mainFrameJsonActivity;

public class activityProfile extends mainFrameJsonActivity implements View.OnClickListener{

    String NOT_INSTALLED_STRING;
    compositeContactContent contactContent;
    compositeHeaderSimple headerSimple;
    Boolean isMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NOT_INSTALLED_STRING = getString(R.string.not_insatalled);

//        Intent intent = getIntent();
//        isMy = intent.getBooleanExtra("isMy",false);
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

        scriptAddress = getString(R.string.addPersonalInfo);
        contactContent = new compositeContactContent(this,true);
        headerSimple = new compositeHeaderSimple(this);
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(true) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            CharSequence[] params  = new CharSequence[]{};
            switch (view.getId()) {
                // обработка нажатий изменений профиля
                case R.id.contact_seek :

                    params = new CharSequence[]{getString(R.string.man),getString(R.string.woman), NOT_INSTALLED_STRING};
                    builder.setTitle(getString(R.string.seek));

                    break;
                case R.id.contact_purpose_for_seeking:

                    params = new CharSequence[]{getString(R.string.family),getString(R.string.communication),getString(R.string.sex),getString(R.string.dosug), NOT_INSTALLED_STRING};
                    builder.setTitle(getString(R.string.purpose_for_seek));

                    break;
                case R.id.contact_marriage:

                    params = new CharSequence[]{getString(R.string.in_marriage),getString(R.string.not_in_marriage), NOT_INSTALLED_STRING};
                    builder.setTitle(getString(R.string.seek));

                    break;
                case R.id.contact_hobby:
                    //TODO запилить ввод!
                    return;
                case R.id.contact_music:
                    //TODO запилить ввод!
                    return;
                case R.id.contact_about:
                    //TODO запилить ввод!
                    return;

            }

            builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setSingleChoiceItems(params,params.length-1,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        }
    }
}