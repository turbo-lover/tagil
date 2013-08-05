package ru.news.tagil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import ru.news.tagil.R;
import ru.news.tagil.utility.myPreferencesWorker;

public class activityMain extends Activity implements View.OnClickListener {
    myPreferencesWorker preferencesWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View rl = findViewById(R.id.main_layout);
        preferencesWorker = new myPreferencesWorker(this);
        rl.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) { //TODO поменять на постоянный переход к странице авторизации.
        Intent intent;
        intent = new Intent(this,activityLogin.class);
        startActivity(intent);
    }
}
