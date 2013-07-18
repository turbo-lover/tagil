package ru.news.tagil;

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

import java.util.concurrent.ExecutionException;

/**
 * Created by turbo_lover on 12.07.13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    EditText login,password;
    Button sign_in,to_registration;
    My_Preferences_Worker preferences_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialize_Component();
        SetEventListeners();

    }

    private void SetEventListeners() {
        sign_in.setOnClickListener(this);
        to_registration.setOnClickListener(this);
    }

    private void Initialize_Component() {
        sign_in = (Button) findViewById(R.id.enter);
        to_registration = (Button) findViewById(R.id.reg);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.pass);
        preferences_worker = new My_Preferences_Worker(this);
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
           My_AsyncTask_Worker worker = new My_AsyncTask_Worker();
           JSONObject sends_data = new JSONObject();
           try {
               sends_data.put("login", login.getText());
               sends_data.put("pass", password.getText());
               worker.execute(sends_data,getResources().getString(R.string.serverAddress)+getResources().getString(R.string.loginUrl));
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
            if(status.equals("error")){
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
       Intent intent = new Intent(this,TapeActivity.class);
       startActivity(intent);
    }

    private boolean isValidateFieldsOk() {
        if(login.getText().length()==0 || password.getText().length()==0) {
            return false;
        }
        return true;
    }

    private void Registration() {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }
}
