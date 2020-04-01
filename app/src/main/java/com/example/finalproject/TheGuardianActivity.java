package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static android.widget.Toast.LENGTH_LONG;


//Author: Lilia Ramalho Martins
//Student # 040952491
public class TheGuardianActivity extends AppCompatActivity implements OnTGQueryCompleted, NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_guardian);

        editText = (EditText)findViewById(R.id.editText_term);
        button = (Button)findViewById(R.id.search_term_button);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        listView = (ListView)findViewById(R.id.list_results);

        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        TGArticlesListAdapter myArticleListAdapter = new TGArticlesListAdapter(this);
        for (TheGuardianArticle element : articles) {
            myArticleListAdapter.getElements().add(element);
        }
        listView.setAdapter(myArticleListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                Intent gotToStarred = new Intent(TheGuardianActivity.this, StarredActivity.class);
                startActivity(gotToStarred);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tg_news_menu, menu);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item11:
                Intent gotToStarred = new Intent(TheGuardianActivity.this, StarredActivity.class);
                startActivity(gotToStarred);
                break;
            case R.id.item12:
                Toast.makeText(this, "message", Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }
}
