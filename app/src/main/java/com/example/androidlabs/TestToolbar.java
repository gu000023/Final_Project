package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
//import android.widget.Toolbar;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tb=(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);
        //setActionBar(tb);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tb, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mi=getMenuInflater();

        mi.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                message = "You clicked item 1";
                break;
            case R.id.item2:
                message = "You clicked item2";
                break;
            case R.id.item3:
                message = "You clicked item3";
                break;

            case R.id.item4:
                message = "You clicked the overflow item";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item01:
               Intent goToProfile = new Intent(TestToolbar.this, ChatRoomActivity.class);
                startActivity(goToProfile);
                break;
           case R.id.item02:
                Intent goToProfile2=new Intent(TestToolbar.this,WeatherForecast.class);
               startActivity(goToProfile2);
                break;
           case R.id.item03:
                finish();
                break;
        }
        //DrawerLayout d=(DrawerLayout) findViewById(R.id.drawer_layout);
        //d.closeDrawer(GravityCompat.START);
        Toast.makeText(this, "NavigationDrawer: " + "clicked", Toast.LENGTH_LONG).show();


        return false;
    }
}
