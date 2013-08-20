package ru.news.tagil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import ru.news.tagil.R;

public class activityMain extends Activity implements View.OnClickListener {
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_holder);
        rl.setOnClickListener(this);
        i  = new Intent(this,activityNewsPreview.class);
    }
    @Override
    public void onClick(View view) {
        startActivity(i);
    }
}
