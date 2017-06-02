package com.bhavdip.haqdarshak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bhavdip.haqdarshak.dbsqlite.UsersModel;

/**
 * Created by bhavdip on 30/5/17.
 */

public class LoginActivity_new extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 99;
    private static final String TAG = "LoginActivity";

    private EditText mobileNo;
    private EditText passwordText;
    private EditText firstName;
    private EditText lastName;
    private TextView wantRegister;
    private TextView passwordErr;
    private AppCompatButton loginButton;
    private int counter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileNo = (EditText) findViewById(R.id.mobile);
        passwordText = (EditText) findViewById(R.id.password);
        wantRegister = (TextView) findViewById(R.id.want_register);
        loginButton = (AppCompatButton) findViewById(R.id.email_sign_in_button);

        wantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity_new.this, SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }


    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity_new.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String mobile = mobileNo.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        // fetch the Password form database for respective user name
        UsersModel usersModel = new UsersModel(LoginActivity_new.this);
        String storedPassword = usersModel.getSinlgeEntry(mobile);

        // check if the Stored password matches with  Password entered by user
        if (password.equals(storedPassword)) {
            onLoginSuccess();
            progressDialog.dismiss();
        } else {

            progressDialog.dismiss();

            if (counter == 3) {
                loginButton.setEnabled(false);


                Snackbar.make(findViewById(android.R.id.content), "Login failed again!, Login Disabled for 5 mins", Snackbar.LENGTH_LONG)
                        .show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginButton.setEnabled(true);
                        counter = 0;
                    }
                }, 10000);

            } else {

                onLoginFailed();
            }
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Snackbar.make(findViewById(android.R.id.content), "Congratulation!, You get successfully Login", Snackbar.LENGTH_LONG)
                .setAction("Undo", null)
                .setActionTextColor(Color.RED)
                .show();

        Intent intent = new Intent(LoginActivity_new.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void onLoginFailed() {

        Snackbar.make(findViewById(android.R.id.content), "Login failed!, Please check entered details", Snackbar.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = mobileNo.getText().toString();
        String password = passwordText.getText().toString();

//        if (email.length()!=10) {
//            return false;
//        } else {
//            return android.util.Patterns.PHONE.matcher(email).matches();
//        }

        if (mobile.isEmpty() || !android.util.Patterns.PHONE.matcher(mobile).matches()) {
            mobileNo.setError("enter a valid mobile no.");
            valid = false;
        } else {
            mobileNo.setError(null);
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
