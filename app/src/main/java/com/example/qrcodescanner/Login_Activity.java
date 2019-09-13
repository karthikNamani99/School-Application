package com.example.qrcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_SIGNUP = 0;


    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        api = ApiUtils.getAPIService();
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);


        loginButton = findViewById(R.id.btn_login);

        signupLink = findViewById(R.id.link_signup);



        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Register_Page.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        Log.d(TAG, "loginuser");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        sendPost(email, password);


    }

    public void sendPost(String email, String password) {

        api.loginuser(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Please Enter Valid Login Details", Toast.LENGTH_LONG).show();

//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }





//
//        Call<LoginResponse> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .loginuser(email, password);
//
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if(response.isSuccess()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    Intent intent=new Intent(getApplicationContext(),LoginPage.class);
//                    startActivity(intent);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                Log.d("onFailure", t.toString());
//            }
//        });




    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;

    }
}
