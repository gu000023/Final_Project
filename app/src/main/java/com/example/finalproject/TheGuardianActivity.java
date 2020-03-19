package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;


//Author: Lilia Ramalho Martins
//Student # 040952491
public class TheGuardianActivity extends AppCompatActivity implements OnQueryCompleted {

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
        progressBar.setVisibility(View.INVISIBLE);
        listView = (ListView)findViewById(R.id.list_results);

        button.setOnClickListener((click) -> {
           String term = String.valueOf(editText.getText());
            if (term == null || term.isEmpty())
                Toast.makeText(TheGuardianActivity.this, "Please, enter search term", LENGTH_LONG).show();
            else {
                String[] words = term.split("\\s+");
                if (words.length > 1)
                    Toast.makeText(TheGuardianActivity.this, "Please, enter only one term", LENGTH_LONG).show();
                else {
                    term.trim();
                    TheGuardianQuery req = new TheGuardianQuery(progressBar, this);
                    req.execute(term);
                }
            }
        });

    }

    @Override
    public void onQueryCompleted(List<TheGuardianArticle> articles) {
        ArticlesListAdapter myArticleListAdapter = new ArticlesListAdapter(this);
        for (TheGuardianArticle element : articles) {
            myArticleListAdapter.getElements().add(element);
        }
        listView.setAdapter(myArticleListAdapter);
    }
}
