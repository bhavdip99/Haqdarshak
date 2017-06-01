package com.bhavdip.haqdarshak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bhavdip.haqdarshak.dbsqlite.UsersModel;

/**
 * Created by bhavdip on 30/5/17.
 */

public class SignupActivity extends AppCompatActivity {

    EditText edtName, edtPassword, edtConfirmPassword, edtMobile, edtGender, edtEmail;
    RadioGroup radioGroup;
    Button btnSignUp;

    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        // Get Refferences of Views
        edtName = (EditText) findViewById(R.id.name);
        edtEmail = (EditText) findViewById(R.id.email);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrp);
        edtMobile = (EditText) findViewById(R.id.mobile);
        edtPassword = (EditText) findViewById(R.id.password);
        edtConfirmPassword = (EditText) findViewById(R.id.comfirm_password);
        btnSignUp = (Button) findViewById(R.id.mobile_sign_up_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String mobile = edtMobile.getText().toString();
                String email = edtEmail.getText().toString();
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                String gender = radioButton == null ? "" : radioButton.getText().toString();

                // check if any of the fields are vaccant
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")
                        || mobile.equals("") || email.equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Field should not Empty", Snackbar.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (mobile.length() != 10) {
                    Snackbar.make(findViewById(android.R.id.content), "enter correct mobile no", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (!password.equals(confirmPassword)) {
                    Snackbar.make(findViewById(android.R.id.content), "Password does not match", Snackbar.LENGTH_LONG).show();
                    return;

                } else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    edtPassword.setError("between 4 and 10 alphanumeric characters");
                    return;
                } else {
                    // Save the Data in Database
                    UsersModel usersModel = new UsersModel(SignupActivity.this);
                    usersModel.insertEntry(userName, email, gender, mobile, password);
                    Snackbar.make(findViewById(android.R.id.content), "Account Successfully Created ", Snackbar.LENGTH_LONG).show();

                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

//                    Intent intent = new Intent(SignupActivity.this,LoginActivity_new.class);
//                    startActivity(intent);
                }
            }
        });
    }
}
