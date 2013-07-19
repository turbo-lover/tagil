package ru.news.tagil.composite;/**
 * Created by turbo_lover on 19.07.13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import ru.news.tagil.R;

public class compositeHeader extends RelativeLayout implements View.OnClickListener {

    Button main;
    Button contact;
    Button ads;
    Button update;

    public compositeHeader(Context context) {
        super(context);
        Initialize_Component();
        SetEventListeners();
    }

    private void SetEventListeners() {
        main.setOnClickListener(this);
        contact.setOnClickListener(this);
        ads.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    private void Initialize_Component() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_header,this);

        main = (Button) findViewById(R.id.composite_header_main);
        contact = (Button) findViewById(R.id.composite_header_contact );
        ads = (Button) findViewById(R.id.composite_header_ads );
        update = (Button) findViewById(R.id.composite_header_update );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.composite_header_main :
            break;
            case R.id.composite_header_contact:
            break;
            case R.id.composite_header_ads :
            break;
            case R.id.composite_header_update :
            break;

        }
    }
}