package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private String msg;
    private String pref="pref";
    private SharedPreferences sp;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear_lab3);

        Button btn=(Button) findViewById(R.id.button2);
        EditText get=(EditText)findViewById(R.id.editText4);

        //lab3 get
        super.onResume();
        get.setText(getSharedPreferences(pref,Context.MODE_APPEND).getString("Email",""));

        //lab 3 set
        btn.setOnClickListener((v)-> {
            onPause();
            //lastly added func
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("Email", getSharedPreferences(pref,Context.MODE_APPEND).getString("Email",""));
            startActivity(goToProfile);

        });
    }


    public void onPause() {
        super.onPause();
        EditText et=(EditText)findViewById(R.id.editText4);
        SharedPreferences sharedPreferences = getSharedPreferences(pref, MODE_PRIVATE);
        SharedPreferences.Editor se = sharedPreferences.edit();
        se.putString("Email", et.getText().toString().equals("")?"":et.getText().toString()).commit();
        //System.out.println("hello");
    }
}
