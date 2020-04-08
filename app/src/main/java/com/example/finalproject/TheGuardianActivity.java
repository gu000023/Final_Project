package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences lastTerm = getSharedPreferences("lastTerm.xml", Context.MODE_PRIVATE);
        String sharedEmail = lastTerm.getString("lastTerm", "");
        editText.setText(sharedEmail);

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
                Toast.makeText(TheGuardianActivity.this, getString(R.string.tg_toast_no_term), LENGTH_LONG).show();
            else {
                String[] words = term.split("\\s+");
                if (words.length > 1)
                    Toast.makeText(TheGuardianActivity.this, getString(R.string.tg_toast_many_terms), LENGTH_LONG).show();
                else {
                    term.trim();
                    TheGuardianQuery req = new TheGuardianQuery(progressBar, this);
                    req.execute(term);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        SharedPreferences lastTerm = getSharedPreferences("lastTerm.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = lastTerm.edit();
        editText = (EditText)findViewById(R.id.editText_term);
        edit.putString("lastTerm", String.valueOf(editText.getText()));
        edit.commit();

        super.onPause();
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
                //Toast.makeText(this, "message", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.tg_help_dialog_title));
                alertDialogBuilder.setMessage(getString(R.string.tg_help_dialog_line1) + "\n" +
                        getString(R.string.tg_help_dialog_line2) + "\n" +
                        getString(R.string.tg_help_dialog_line3) + "\n" +
                        getString(R.string.tg_help_dialog_line4));

                alertDialogBuilder.setPositiveButton(R.string.tg_help_dialog_dismiss, (click, arg) -> {
                    alertDialogBuilder.create().dismiss();
                });
                alertDialogBuilder.create().show();
                break;
            case R.id.item13:
                Intent gotToBBC = new Intent(TheGuardianActivity.this, MainActivity.class);
                startActivity(gotToBBC);
                break;
            case R.id.item14:
                Intent gotToNasa = new Intent(TheGuardianActivity.this, MainActivity.class);
                startActivity(gotToNasa);
                break;
            case R.id.item15:
                Intent gotToSat = new Intent(TheGuardianActivity.this, MainActivity.class);
                startActivity(gotToSat);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }
}
