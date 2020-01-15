package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);

        //added requirements for lab 2
        Button btn=findViewById(R.id.button3);//change to btn 3 4 if needed
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
            }
        });

        CheckBox cb=(CheckBox) findViewById(R.id.checkBox);
        Switch s=(Switch) findViewById(R.id.switch1);
        //change cb 2 3, s 2 3 if needed
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(final CompoundButton cBtn, final boolean isChecked){
                class UndoListener implements View.OnClickListener{
                    public void onClick(View v){
                        cBtn.setChecked(!isChecked);
                    }
                }
                if(isChecked) {//change rl to gr li if needed
                    Snackbar.make(findViewById(R.id.gr), R.string.on, Snackbar.LENGTH_SHORT).setAction(R.string.undo, new UndoListener()).show();
                }
                else {
                    Snackbar.make(findViewById(R.id.gr), R.string.off, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(final CompoundButton cBtn, final boolean isChecked){
                class UndoListener implements View.OnClickListener{
                    public void onClick(View v){
                        cBtn.setChecked(!isChecked);
                    }
                }
                if(isChecked) {//change rl to gr li if needed
                    Snackbar.make(findViewById(R.id.gr), R.string.on, Snackbar.LENGTH_SHORT).setAction(R.string.undo, new UndoListener()).show();
                }
                else {
                    Snackbar.make(findViewById(R.id.gr), R.string.off, Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}
