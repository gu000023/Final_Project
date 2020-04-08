package com.example.finalproject;
/**
 * show news detail
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class bbc_details extends AppCompatActivity {
    /**
     * show news detail information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_details);

        Bundle dataToPass = getIntent().getExtras();

        TextView title=(TextView)findViewById((R.id.bbc_tv2));
        title.setText(dataToPass.getString(bbc_main.SUBJECT));

        TextView date=(TextView)findViewById((R.id.bbc_tv3));
        date.setText(dataToPass.getString(bbc_main.DATE));

        TextView intro=(TextView)findViewById((R.id.bbc_tv4));
        intro.setText(dataToPass.getString(bbc_main.INTRO));

        TextView url=(TextView)findViewById((R.id.bbc_tv5));
        url.setText(dataToPass.getString(bbc_main.URL));




        Button btn_fav = (Button) findViewById(R.id.bbc_bt4);
        if(btn_fav != null) {
            btn_fav.setOnClickListener(click -> {
                //toast
                Toast.makeText(this, "Going to favourite list...", Toast.LENGTH_SHORT).show();

                Intent goToFav = new Intent(this, bbc_fav.class);
                startActivityForResult(goToFav, 30);
            });
        }

        Button btn_back_main = (Button) findViewById(R.id.bbc_bt3);
        if(btn_back_main != null) {
            btn_back_main.setOnClickListener(click -> {
                //toast
                Toast.makeText(this, "Going to news list...", Toast.LENGTH_SHORT).show();

                //AlertDialog
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Notice")
                        //What is the message:

                        .setMessage("You're going back to the news list!")
                        .setNegativeButton("Close", (clk, arg) -> {
                        }).create().show();
                Intent goToMain = new Intent(this, bbc_main.class);
                startActivityForResult(goToMain, 30);
            });
        }
    }
}
