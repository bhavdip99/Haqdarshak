package com.bhavdip.haqdarshak;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bhavdip.haqdarshak.dbsqlite.UsersModel;

/**
 * Created by bhavdip on 30/5/17.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int REQUEST_CROP = 127;
    public static final int REQUEST_CAMERA = 128;
    public static final int REQUEST_GALLERY = 129;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 990;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 991;

    EditText edtName, edtPassword, edtConfirmPassword, edtMobile, edtGender, edtEmail;
    RadioGroup radioGroup;
    Button btnSignUp;
    ImageView imageViewProfilePic;
    private Uri picUri;

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
        imageViewProfilePic = (ImageView) findViewById(R.id.imgViw_profile_pic);
        imageViewProfilePic.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(SignupActivity.this, R.style.DialogSlideAnim);

        dialog.setContentView(R.layout.dialog_profile_pic_menu);

        dialog.setTitle("Profile photo");

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        //Grab the window of the dialog, and change the width
        wlp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        // set the dialog_profile_pic_menu dialog components - text, image and button
        TextView btnGallery = (TextView) dialog.findViewById(R.id.btn_gallery);
        btnGallery.setText("Gallery");
        TextView btnCamera = (TextView) dialog.findViewById(R.id.btn_camera);
        btnCamera.setText("Camera");
        TextView btnRemovePhoto = (TextView) dialog.findViewById(R.id.btn_remove_photo);
        btnRemovePhoto.setText("Remove photo");
/*
        //for Adobetracking events
        if (btnProfilePicUpdt.getText().toString()==getResources().getString(R.string.edit_profile_photo)){
            NykaaAnalytics.trackAdobeProfileClicks(MyAccountActivity.this, "EditProfilePicture","profilepicedit");
            btnRemovePhoto.setVisibility(View.VISIBLE);
        }else{
            NykaaAnalytics.trackAdobeProfileClicks(MyAccountActivity.this, "AddProfilePicture","profilepicadded");
            btnRemovePhoto.setVisibility(View.GONE);
        }*/


        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.img_profile_pic));

                if (dialog != null)
                    dialog.dismiss();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                } else {
                    launchCamera();

                }

                if (dialog != null)
                    dialog.dismiss();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(findViewById(android.R.id.content), "Oopps! This functionality coming Soon :)", Snackbar.LENGTH_LONG).show();

                if (dialog != null)
                    dialog.dismiss();
            }
        });

        dialog.show();

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    launchCamera();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "The app was not allowed to write to your storage. " +
                            "Hence, it cannot function properly." +
                            " Please consider granting it this permission", Snackbar.LENGTH_LONG).show();
                }
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // get the Uri for the captured image
                performCrop();
                if (data != null) {
                    if (data.getData() != null) {
                        picUri = data.getData();
                    } else if (data.getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        picUri = getImageUri(this, bitmap);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Returned data is null", Snackbar.LENGTH_LONG).show();
                }
            }
            // user is returning from cropping the image
            else if (requestCode == REQUEST_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
//                ImageView picView = (ImageView) findViewById(R.id.picture);
                imageViewProfilePic.setImageBitmap(thePic);
            }
        }
    }

    /**
     * this function does the crop operation.
     */
    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void launchCamera() {
        try {
            //use standard intent to capture an image
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            //we will handle the returned data in onActivityResult

            startActivityForResult(captureIntent, REQUEST_CAMERA);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast toast = Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
