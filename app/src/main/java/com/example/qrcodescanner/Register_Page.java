package com.example.qrcodescanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register_Page extends AppCompatActivity {

    private static final String TAG = "Register_Page";

    EditText nameText;
    EditText emailText;
    EditText passwordText;
    EditText mobileText;

    private ProgressDialog pDialog;
    Api api;
    Button signupButton;
    TextView loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        nameText = findViewById(R.id.input_name);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        mobileText = findViewById(R.id.input_mobile);

        signupButton = findViewById(R.id.btn_signup);

        loginLink = findViewById(R.id.link_login);
        api = ApiUtils.getAPIService();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the<a class="Eyqj4pXnY " style="z-index: 2147483647;" title="Click to Continue > by Advertise" href="#2293366"> registration<img src="http://cdncache-a.akamaihd.net/items/it/img/arrow-10x10.png" /></a> screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void signup() {
        Log.d(TAG, "registeruser");

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String mobile = mobileText.getText().toString();

        if (validate() == false) {
            onSignupFailed();
            return;
        }

        sendPost(name, email, password, mobile);

    }

    private void sendPost(String name, String email, String password, String mobile) {

        api.registeruser(email, password, name, mobile)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                        Toast.makeText(getApplicationContext(), "Registration Success fully completed! Please Login", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(intent);

                        Log.i(TAG, "post submitted to API." + response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Please Enter Valid Login Details", Toast.LENGTH_LONG).show();
//                        Log.e(TAG, "Unable to submit post to API.");
                    }
                });

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String mobile = mobileText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10) {
            mobileText.setError("enter mobile number");
            valid = false;
        } else {
            mobileText.setError(null);
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




//    private void saveToServerDB() {
//
//        String name = nameText.getText().toString().trim();
//        String email = emailText.getText().toString().trim();
//        String password = passwordText.getText().toString().trim();
//        String mobile = mobileText.getText().toString().trim();
//
//        validate();



//        Call<LoginResponse> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .registeruser(email, password, name, mobile);
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//
//                Log.i(TAG, "post submitted to API." + response.body().toString());
//
//                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
//                startActivity(intent);
//
//            }

//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                Log.e(TAG, "Unable to submit post to API.");            }
//        });



//        _signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "Signup");
//
//
//                final ProgressDialog progressDialog = new ProgressDialog(Register_Page.this,
//                        R.style.AppTheme);
//                progressDialog.setIndeterminate(true);
//                progressDialog.setMessage("Creating Account...");
//                progressDialog.show();
//
//                String name = _nameText.getText().toString();
//                String email = _emailText.getText().toString();
//                String password = _passwordText.getText().toString();
//                String mobile = _mobile.getText().toString();
//                validate();
//
//
//                Call<ResponseBody> call = RetrofitClient
//                        .getInstance()
//                        .getApi()
//                        .registeruser(email, password, name, mobile);
//
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
//                            String s = response.body().string();
//                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            Toast.makeText(Register_Page.this, s, Toast.LENGTH_LONG).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        Toast.makeText(Register_Page.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        });
//        _loginLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void validate() {
//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//        String mobile = _mobile.getText().toString();
//
//        if (name.isEmpty() || name.length() < 3) {
//            _nameText.setError("at least 3 characters");
//
//        } else {
//            _nameText.setError(null);
//        }
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//
//        } else {
//            _passwordText.setError(null);
//        }
//        if (mobile.isEmpty() || mobile.length() < 10) {
//            _mobile.setError("enter mobile number");
//
//        } else {
//            _mobile.setError(null);
//        }
//    }

//    private void signup() {
//        Log.d(TAG, "Signup");
//
//
//
//        _signupButton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(Register_Page.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//
//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//        String mobile = _mobile.getText().toString();
//
//        if (name.isEmpty() || name.length() < 3) {
//            _nameText.setError("at least 3 characters");
//
//        } else {
//            _nameText.setError(null);
//        }
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//
//        } else {
//            _passwordText.setError(null);
//        }
//        if (mobile.isEmpty() || mobile.length() < 10) {
//            _mobile.setError("enter mobile number");
//
//        } else {
//            _mobile.setError(null);
//        }
//


//    }
