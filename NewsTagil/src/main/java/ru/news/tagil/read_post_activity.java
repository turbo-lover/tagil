package ru.news.tagil;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Alexander on 15.07.13.
 */
public class read_post_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_read_post);
       // RelativeLayout rl = (RelativeLayout) findViewById(R.id.read_post_main_layout);
        //TODO сначала реализуется получение превью новостей

/*
        My_AsyncTask_Worker worker = new My_AsyncTask_Worker();
        JSONObject jo = new JSONObject();
        try{
            jo.put("login","vasya");
            jo.put("id_news",1);
            worker.execute(jo, "http://192.168.1.110/get_news.php");
            jo = worker.get();
            if(jo.getString("status") == "ok") {
                JSONArray arr = jo.getJSONArray("result");
                Composite_tape_activity news = new Composite_tape_activity(this,jo.getString(""))


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/

    }
}
