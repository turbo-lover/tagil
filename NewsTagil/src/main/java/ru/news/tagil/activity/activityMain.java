package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    protected void onStart() {
        super.onStart();
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(i);
                finish();
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        startActivity(i);
    }

}
