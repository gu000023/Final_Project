package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//Author: Lilia Ramalho Martins
//Student # 040952491
public class TheGuardianActivity extends AppCompatActivity {

    private URL url;
    private HttpURLConnection urlConnection;
    private InputStream response;
    private ListView listView;
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_guardian);

        editText = (EditText)findViewById(R.id.editText_term);
        button = (Button)findViewById(R.id.search_term_button);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        listView = (ListView)findViewById(R.id.list_results);

        button.setOnClickListener((click) -> {
           String term = String.valueOf(editText.getText());
           term.trim();
           TheGuardianQuery req = new TheGuardianQuery(this, listView, progressBar);
           req.execute(term);
        });

    }
}
