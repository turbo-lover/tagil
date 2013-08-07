package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeaderSimple;
import ru.news.tagil.utility.ScrollUpdateActivity;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityCorrespondence extends ScrollUpdateActivity implements View.OnClickListener {
    private compositeHeaderSimple headerSimple;
    private compositeFirstButton cfb;


    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityMessages.class);
       // i.putExtra("interluctor",)
        startActivity(i);
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) { return null; }
    @Override
    protected JSONObject CreateJsonForGet() { return null; }
    @Override
    protected JSONObject CreateJsonForGetNew() { return null; }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        headerSimple = new compositeHeaderSimple(this);
        cfb = new compositeFirstButton(this);
        scriptAddress = getString(R.string.getInterlocutorsUrl);
    }
    @Override
    protected void SetEventListeners() {}
    @Override
    protected void SetCompositeElements() {}
}
