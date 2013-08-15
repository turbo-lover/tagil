package ru.news.tagil.composite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.news.tagil.R;

/**
 * Created by Alexander on 15.08.13.
 */
public class compositeSettingFaq extends RelativeLayout {
    private TextView q,a,q_txt,a_txt;
    public compositeSettingFaq(Context context) {
        super(context);
        Initialize_Component();
    }

    private void Initialize_Component() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.composite_setting_faq, this);
        q = (TextView) findViewById(R.id.composite_settting_faq_q);
        a = (TextView) findViewById(R.id.composite_settting_faq_a);
        q_txt = (TextView) findViewById(R.id.composite_settting_faq_q_text);
        a_txt = (TextView) findViewById(R.id.composite_settting_faq_a_text);

    }

    public compositeSettingFaq(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize_Component();
    }

    public compositeSettingFaq(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize_Component();
    }

    public void Set(String q,String a,String q_txt,String a_txt) {
        this.q.setText(q);
        this.a.setText(a);
        this.q_txt.setText(q_txt);
        this.a_txt.setText(a_txt);
    }
}
