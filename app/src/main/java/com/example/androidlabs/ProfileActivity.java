package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton;
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private Exception e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImageButton=(ImageButton)findViewById(R.id.imageButton4);
        ImageButton ib=(ImageButton)findViewById(R.id.imageButton4);
        ib.setOnClickListener((v)->{

            //private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            //}

        });

        //get email from MA.java
        Intent fromMain = getIntent();
        String msg=fromMain.getStringExtra("Email");
        EditText et=(EditText)findViewById(R.id.editText6);
        et.setText(msg);

        Log.e(ACTIVITY_NAME,"In function: onCreate",e);
        Log.e(ACTIVITY_NAME,"In function: onStart",e);
        Log.e(ACTIVITY_NAME,"In function: onResume",e);
        Log.e(ACTIVITY_NAME,"In function: onPause",e);
        Log.e(ACTIVITY_NAME,"In function: onStop",e);
        Log.e(ACTIVITY_NAME,"In function: onDestroy",e);
        Log.e(ACTIVITY_NAME,"In function: onActivityResult",e);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //hit back button and if will be omitted
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


}
