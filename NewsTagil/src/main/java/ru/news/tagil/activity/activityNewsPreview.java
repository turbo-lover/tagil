package ru.news.tagil.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.composite.compositeFirstButton;
import ru.news.tagil.composite.compositeHeader;
import ru.news.tagil.composite.compositeTapePreview;
import ru.news.tagil.utility.ScrollUpdateActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexander on 15.07.13.
 */
public class activityNewsPreview extends ScrollUpdateActivity implements View.OnClickListener{
    private compositeFirstButton cfb;
    private compositeHeader compositeHeader;
    @Override
    protected JSONObject CreateJsonForGet() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("news_count",GET_COUNT);
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
            compositeTapePreview c = (compositeTapePreview) container.getChildAt(container.getChildCount() - 1);
            send_time = c.getDateTime();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected JSONObject CreateJsonForGetNew() {
        JSONObject jo = new JSONObject();
        try {
            String send_time = null;
            if(container.getChildCount() == 0) {
                send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            } else {
                compositeTapePreview c = (compositeTapePreview) container.getChildAt(0);
                send_time = c.getDateTime();
            }
            jo.put("time",send_time);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateJsonForGet_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return jo;
    }
    @Override
    protected View CreateViewToAdd(JSONObject obj) {
        compositeTapePreview tapePreview = new compositeTapePreview(this);
        try {
            String[] s = obj.getString("pub_time").split(" ");
            tapePreview.Set(s[0],s[1],obj.getString("header"));
            tapePreview.setOnClickListener(this);
            tapePreview.setTag(obj.getString("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CreateViewToAdd_Exception", ex.getMessage() + "\n\n" + ex.toString());
        }
        return tapePreview;
    }
    @Override
    protected void SetEventListeners() {
        super.SetEventListeners();
        compositeHeader.SetUpdateListener(this);
    }
    @Override
    protected void SetCompositeElements() {
        compositeHeader.Set(getString(R.string.mainText),getString(R.string.contactText),getString(R.string.advertText));
        compositeHeader.UpdateWeather("0","2");//TODO допилить получение погоды
        footer.addView(cfb);
        header.addView(compositeHeader);
    }
    @Override
    protected void InitializeComponent() {
        super.InitializeComponent();
        cfb = new compositeFirstButton(this);
        compositeHeader = new compositeHeader(this);
        scriptAddress = getString(R.string.getNewsHeadersUrl);
        tableName = "news";
        totalCount = GetTotalCount(null);
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,activityNewsContent.class);
        compositeTapePreview c = (compositeTapePreview) view;
        i.putExtra("time",c.getTime());
        i.putExtra("date",c.getDate());
        i.putExtra("header",c.getHeader());
        i.putExtra("id_news",(String) c.getTag());
        startActivity(i);
    }

}
