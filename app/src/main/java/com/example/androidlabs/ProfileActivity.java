package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton;
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private String pref="pref";
    private SharedPreferences sp;
//oncreate, onstart, onresume, take photo to onpause, onstop, onactivityresults, oncreate, onresume, back? ondestroy,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Oncreate");
        //
            setContentView(R.layout.activity_profile);
    }

    public void onStart(){
        super.onStart();
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Onstart");
        //
        ImageButton ib = (ImageButton) findViewById(R.id.imageButton4);
        ib.setOnClickListener((v) -> {

            //private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            //}

        });

        //get email from MA.java
        Intent fromMain = getIntent();
        String msg = fromMain.getStringExtra("Email");
        EditText et = (EditText) findViewById(R.id.editText6);
        et.setText(msg);

        //lab 4 below
        Button b=(Button) findViewById(R.id.button4);
        b.setOnClickListener((v)->{
            Intent goToProfile = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(goToProfile);

        });

        //lab6 below
        Button bt=(Button) findViewById(R.id.button7);
        bt.setOnClickListener((v)->{
            Intent goToProfile=new Intent(ProfileActivity.this,WeatherForecast.class);
            startActivity(goToProfile);
        });

        //lab8 below
        Button btn9=(Button) findViewById(R.id.button9);
        btn9.setOnClickListener((v)->{
            Intent go=new Intent(ProfileActivity.this,TestToolbar.class);
            startActivityForResult(go,2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"OnActivityResult");
        //
        //hit back button and if will be omitted
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton=(ImageButton) findViewById(R.id.imageButton4);
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    public void onPause() {
        super.onPause();
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Onpause");
        //
    }

    public void onResume(){
        super.onResume();
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Onresume");
        //
    }

    public void onStop(){
        super.onStop();
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Onstop");
        //
    }

    public void onDestroy(){
        super.onDestroy();
        //
        Log.e(ProfileActivity.ACTIVITY_NAME,"In function: "+"Ondestroy");
        //
    }


}
