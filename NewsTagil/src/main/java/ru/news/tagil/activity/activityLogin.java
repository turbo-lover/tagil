package ru.news.tagil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityLogin extends Activity implements View.OnClickListener,jsonActivity {

    private EditText login,password;
    private Button sign_in,to_registration;
    private myPreferencesWorker preferences_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        SetEventListeners();
    }

    private void SetEventListeners() {
        sign_in.setOnClickListener(this);
        to_registration.setOnClickListener(this);
    }

    private void InitializeComponent() {
        setContentView(R.layout.activity_login);
        sign_in = (Button) findViewById(R.id.enter);
        to_registration = (Button) findViewById(R.id.reg);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.pass);
        preferences_worker = new myPreferencesWorker(this);
        if(!preferences_worker.get_login().isEmpty() && !preferences_worker.get_pass().isEmpty()) {
            login.setText(preferences_worker.get_login());
            password.setText(preferences_worker.get_pass());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.reg:
                Registration();
                break;
            case R.id.enter:
                new myAsyncTaskWorker(this,jsonActivityMode.GET).execute(CreateJson(),
                        getString(R.string.serverAddress) + getString(R.string.loginUrl));
                break;
        }
    }

    private JSONObject CreateJson() {
        if( isValidateFieldsOk()) {
            JSONObject sends_data = new JSONObject();
            try {
                sends_data.put("login", login.getText());
                sends_data.put("pass", password.getText());
                return sends_data;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("CreateJson_Exception",e.getMessage() + "\n\n" + e.toString());
            }
        }
        return null;
    }

    private void ParsingResponse(JSONObject response) {
        try {
            String status = response.getString("status");
            if(status.equals("ok")){
                preferences_worker.set_login(login.getText().toString());
                preferences_worker.set_pass(password.getText().toString());
                toNextActivity();
            }
            if(status.equals("denied")){
                Toast.makeText(this,getResources().getString(R.string.login_error),Toast.LENGTH_SHORT).show();
            }
            if(status.equals("errormsg")){
                Toast.makeText(this,getResources().getString(R.string.login_error),Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.d("PARSING_RESPONsE_JSON",e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("PARSING_RESPONsE_EXEP",e.getMessage());
        }
    }

    private void toNextActivity() {
       setResult(RESULT_OK);
       finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                toNextActivity();
            }
        }
    }

    private boolean isValidateFieldsOk() {
        if(login.getText().length() == 0 || password.getText().length() == 0) {
            return false;
        }
        return true;
    }

    private void Registration() {
        Intent intent = new Intent(this,activityRegistration.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void Set(JSONObject jsonObject) {
        ParsingResponse(jsonObject);
    }

    @Override
    public void FinishedRequest(JSONObject returned, jsonActivityMode mode) {
        Set(returned);
    }
}
