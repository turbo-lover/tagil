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
import ru.news.tagil.utility.myAsyncTaskWorker;
import ru.news.tagil.utility.myPreferencesWorker;

import java.util.concurrent.ExecutionException;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class activityLogin extends Activity implements View.OnClickListener {

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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.reg://registration
                Registration();
                break;
            case R.id.enter://sign in
                SignIn();
                break;
        }

    }

    private void SignIn() {
       if( isValidateFieldsOk()) {
           myAsyncTaskWorker worker = new myAsyncTaskWorker();
           JSONObject sends_data = new JSONObject();
           try {
               sends_data.put("login", login.getText());
               sends_data.put("pass", password.getText());
               worker.execute(sends_data,getString(R.string.serverAddress)+getString(R.string.loginUrl));
               ParsingResponse(worker.get());
           }
           catch (JSONException e) {
               e.printStackTrace();
               Log.d("SIGN_IN_METHOD_JSONException",e.getMessage() + "____________" + e.toString());
           }
           catch (InterruptedException e) {
               e.printStackTrace();
               Log.d("SIGN_IN_METHOD_Interrupted",e.getMessage() + "____________" + e.toString());
           }
           catch (ExecutionException e) {
               e.printStackTrace();
               Log.d("SIGN_IN_METHOD_Execution",e.getMessage() + "____________" + e.toString());
           }
       } else {
           Toast.makeText(this,getResources().getString(R.string.login_fields_empty),Toast.LENGTH_SHORT).show();
       }
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
       Intent intent = new Intent(this,activityNewsPreview.class);
       startActivity(intent);
    }

    private boolean isValidateFieldsOk() {
        if(login.getText().length() == 0 || password.getText().length() == 0) {
            return false;
        }
        return true;
    }

    private void Registration() {
        Intent intent = new Intent(this,activityRegistration.class);
        startActivity(intent);
    }
}
