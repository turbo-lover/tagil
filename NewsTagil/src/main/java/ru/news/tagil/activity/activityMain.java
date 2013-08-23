package ru.news.tagil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.RelativeLayout;
import ru.news.tagil.R;

public class activityMain extends Activity  {
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_holder);
        i  = new Intent(this,activityNewsPreview.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(1000*30, 1000*3) {
            @Override
            public void onTick(long l) {
                startActivity(i);
                cancel();
            }
            @Override
            public void onFinish() {}
        }.start();
    }

}
