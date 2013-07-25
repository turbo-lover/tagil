package ru.news.tagil.activity;/**
 * Created by turbo_lover on 24.07.13.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Launcher extends Activity {

    boolean FLAG = true; // первый вход )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this,activityNewsPreview.class);
        startActivity(intent);
        this.finish();
        return;
    }
}