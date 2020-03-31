package com.example.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class enterlatlon extends AppCompatActivity {
    public static String latinput="";
    public static String loninput="";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterlatlon);
        new AlertDialog.Builder(enterlatlon.this)
                .setTitle("System Alert")
                .setMessage("Entering NASA DB IMAGE SEARCH, CLICK YES TO PROCEED, NO TO EXIT")

                .setPositiveButton(android.R.string.yes, null)

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        Button search=(Button) findViewById(R.id.serachnasa);
        search.setOnClickListener((v)->{
            Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show();
            EditText latitude=(EditText) findViewById(R.id.editText10);
            EditText longtitude=(EditText) findViewById(R.id.editText11);
            latinput=latitude.getText().toString();
            loninput=longtitude.getText().toString();

            Intent goToNasa = new Intent(enterlatlon.this, NasaEarthDB1.class);
            //goToProfile.putExtra("Email", getSharedPreferences(pref,Context.MODE_PRIVATE).getString("Email",""));
            startActivity(goToNasa);
        });

    }
}
