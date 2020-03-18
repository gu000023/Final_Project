package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//Author: Lilia Ramalho Martins
//Student # 040952491
public class TheGuardianActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;
    private List<TheGuardianArticle> articles = new ArrayList<>();

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
           TheGuardianQuery req = new TheGuardianQuery(progressBar);
            try {
                articles = req.execute(term).get();
            } catch (Exception e) {
                Log.e("Error message: ", e.getMessage());
            }

            ArticlesListAdapter myArticleListAdapter = new ArticlesListAdapter(this);
            for (TheGuardianArticle element : articles) {
                myArticleListAdapter.getElements().add(element);
            }
            listView.setAdapter(myArticleListAdapter);

        });

    }
}
