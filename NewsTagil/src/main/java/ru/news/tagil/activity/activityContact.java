package ru.news.tagil.activity;/**
 * Created by turbo_lover on 23.07.13.
 */

import android.app.Activity;
import android.os.Bundle;
import ru.news.tagil.R;

public class activityContact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize_Component();
    }

    private void SetEventListeners() {
        setContentView(R.layout.activity_contact);
    }

    private void Initialize_Component() {
        SetEventListeners();
    }
}