package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import ru.news.tagil.R;
import ru.news.tagil.utility.jsonActivity;
import ru.news.tagil.utility.jsonActivityMode;
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

import java.util.concurrent.ExecutionException;

/**
 * Created by Alexander on 16.07.13.
 */
public class activityRegistration extends Activity implements View.OnClickListener,jsonActivity {

    Button registration;
    EditText pass,mail,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        SetEventListeners();
    }

    private void SetEventListeners() {
        registration.setOnClickListener(this);
    }

    private void InitializeComponent() {
        setContentView(R.layout.activity_reg);
        registration = (Button) findViewById(R.id.button_reg);
        pass = (EditText) findViewById(R.id. reg_pass);
        mail = (EditText) findViewById(R.id. reg_mail);
        login = (EditText) findViewById(R.id. reg_login);
    }

    @Override
    public void onClick(View view) {
        if(!Validate_Email()) return;
        if(!Validate_Pass()) return;
        if(!Validate_Login()) return;
        new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJson(),
                getString(R.string.serverAddress) + getString(R.string.registrationUrl));
    }

    private JSONObject CreateJson() {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("login",login.getText());
            jObj.put("pass",pass.getText());
            jObj.put("email",mail.getText());
            return jObj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void ToTapeActivity() {
        setResult(RESULT_OK);
        finish();
    }

    private boolean Validate_Email() {
        CharSequence target= mail.getText() ;
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) return true;
        login.requestFocusFromTouch();
        Toast.makeText(this, getString(R.string.wrong_mail), Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean Validate_Login() {
        int length = login.getText().toString().length();
        if(length >=3) return true;
        login.requestFocusFromTouch();
        Toast.makeText(this, getString(R.string.empty_login), Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean Validate_Pass() {
        int length = pass.getText().toString().length();
        if(length >=3) return true;
        pass.requestFocusFromTouch();
        Toast.makeText(this, getString(R.string.empty_pass), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void Set(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("ok")) {
                myPreferencesWorker preferences_worker = new myPreferencesWorker(this);
                preferences_worker.set_login(login.getText().toString());
                preferences_worker.set_pass(pass.getText().toString());
                ToTapeActivity();
            }
            if(status.equals("denied")){
                Toast.makeText(this, getString(R.string.DeniedRegistration),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void FinishedRequest(JSONObject returned, jsonActivityMode mode) {
        Set(returned);
    }
}


