package com.example.finalproject;
/**
 * This class download news form rss feeds and show news on a list.
 * Author :Yun Zhu
 */

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.util.ArrayList;


import static android.view.View.GONE;

public class bbc_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TITLE="title";
    private static final String ACTIVITY_NAME = "BBC News";
    ArrayList<News_object> news_object = new ArrayList<>();
    public static final String SUBJECT = "subject";
    public static final String INTRO = "intro";
    public static final String URL = "url";
    public static final String DATE = "date";
    ProgressBar bar;
    MyAdapter adapter;
    ListView newsList;


    /**
     * show list
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_main);

        newsList = (ListView)findViewById(R.id.bbc_lv1);
        bar= (ProgressBar)findViewById(R.id.bbc_bar1);
        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences prefs = getSharedPreferences("UserEmail", MODE_PRIVATE);
        String preEmail = prefs.getString("Email", "");
        EditText input = findViewById(R.id.bbc_et1);
        input.setText(preEmail);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
  //      newsList.setAdapter(adapter = new MyAdapter(this, R.layout.bbc_news_row, news_object));
        newsList.setAdapter(adapter = new MyAdapter());
        NewsRequest getNews = new NewsRequest();
        getNews.execute();
        Snackbar.make(newsList, this.getString(R.string.snackbar), Snackbar.LENGTH_SHORT).show();

        newsList.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();

            dataToPass.putString(SUBJECT, news_object.get(position).getSubject());
            dataToPass.putString(INTRO, news_object.get(position).getIntro());
            dataToPass.putString(DATE, news_object.get(position).getDate());
            dataToPass.putString(URL, news_object.get(position).getUrl());

            Intent detailPage = new Intent (this,BbcEmptyActivity.class);
            detailPage.putExtras(dataToPass);
            startActivity(detailPage);
                });

        Button btn_fav = (Button) findViewById(R.id.bbc_bt2);
        if(btn_fav != null) {
            btn_fav.setOnClickListener(click -> {
                //toast
                Toast.makeText(this, this.getString(R.string.main_toast), Toast.LENGTH_SHORT).show();
                //snack bar
     //           Snackbar.make(news_object, "Processing...", Snackbar.LENGTH_LONG).show();
                Intent goToFav = new Intent(this, bbc_fav.class);
                goToFav.putExtra("typeEmail", input.getText().toString());
                startActivityForResult(goToFav, 30);
            });
        }

        Button btn_help = (Button) findViewById(R.id.bbc_bt_help);
  //      Snackbar.make(bbcMainList, "Processing...", Snackbar.LENGTH_LONG).show();
        if(btn_help != null){
            btn_help.setOnClickListener(click -> {
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                //alertDialog Notification
                alertDialogBuilder.setTitle(this.getString(R.string.help_title))
                        .setMessage(this.getString(R.string.help_content))
                        .setNegativeButton(this.getString(R.string.help_yes), (clk, arg) -> { }).create().show();
            });
        }
    }

    /**
     * show menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * deal with toolbar item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                androidx.appcompat.app.AlertDialog.Builder
                        alertDialogBuilder = new AlertDialog.Builder(this);
                //final int positionToRemove = pos;

                alertDialogBuilder.setTitle(this.getString(R.string.help_title))

                        .setMessage(this.getString(R.string.help_content))

                        .setPositiveButton(this.getString(R.string.help_yes), (click, arg) -> {

                        })

                        .setNegativeButton(this.getString(R.string.help_no), (click, arg) -> {
                        })
                        //    .setNeutralButton("Maybe",(click,arg)->{})
                        //     .setView(getLayoutInflater().inflate(R.layout.activity_chat_room,null))
                        .create().show();;
                break;

            case R.id.item2:
                Intent goToFav = new Intent(this, bbc_fav.class);
                startActivityForResult(goToFav, 30);
                break;

            case R.id.item3:
//                Intent goToItem3 = new Intent(this, bbc_fav.class);
//                startActivityForResult(goToItem3, 30);
                break;



        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;

    }

    /**
     * deal with navigation item
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;

        switch (item.getItemId()) {

            case R.id.item2:
                //               message = "You clicked on mail";
                Intent nextPage = new Intent(this, bbc_fav.class);
                startActivityForResult(nextPage, 30);
                break;
            case R.id.item1:
                //               message = "You clicked item 1";
                //            Intent result = new Intent();
                androidx.appcompat.app.AlertDialog.Builder
                        alertDialogBuilder = new AlertDialog.Builder(this);
                //final int positionToRemove = pos;

                alertDialogBuilder.setTitle(this.getString(R.string.help_title))

                        .setMessage(this.getString(R.string.help_content))

                        .setPositiveButton(this.getString(R.string.help_yes), (click, arg) -> {

                        })

                        .setNegativeButton(this.getString(R.string.help_no), (click, arg) -> {
                        })
                        //    .setNeutralButton("Maybe",(click,arg)->{})
                        //     .setView(getLayoutInflater().inflate(R.layout.activity_chat_room,null))
                        .create().show();;
                break;

        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

 //       Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }

    /**
     * download news form "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
     * get title,description,url, pubDate from rss
     * reference : https://www.androidauthority.com/simple-rss-reader-full-tutorial-733245/
     */
    private class NewsRequest extends AsyncTask<String, Integer, String>{
        String subject = null;
        String intro = null;
        String date = null;
        String url = null;
        boolean isItem = false;

        @Override
        protected  String doInBackground(String... args){
            publishProgress(25);
            String newsUrl = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
            try {
                URL fecthUrl = new URL(newsUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) fecthUrl.openConnection();
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , null);
                xpp.nextTag();

                while(xpp.next() != XmlPullParser.END_DOCUMENT) {
                    long id=0;
                    int eventType = xpp.getEventType();
                    String name = xpp.getName();
                    if (name == null)
                        continue;

                    if (eventType == XmlPullParser.END_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }
                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                    }

                    String result = "";
                    if (xpp.next() == XmlPullParser.TEXT) {
                        result = xpp.getText();
                        xpp.nextTag();
                    }
                    if (name.equalsIgnoreCase("title")) {
                        subject = result;
                    } else if (name.equalsIgnoreCase("description")) {
                        intro = result;
                    } else if (name.equalsIgnoreCase("link")) {
                        url = result;
                    } else if (name.equalsIgnoreCase("pubDate")) {
                        date = result;
                    }
                    if (subject != null && url != null && intro != null&& date !=null) {
                        if (isItem) {
                            News_object newsObject = new News_object(id,subject, intro, url, date);
                            news_object.add(newsObject);
                        }
                        subject = null;
                        url = null;
                        intro = null;
                        date = null;
                        isItem = false;
                        publishProgress(50);
                    }
                }

                response.close();
            }catch (Exception e){}

            publishProgress(100);

            return "Done";
        }
        /**
         * Update progress bar, list, and adapter
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {

            bar.setVisibility(View.INVISIBLE);

            adapter.notifyDataSetChanged();
//            newsList.setAdapter(newsAdapter = new MyListAdapter());
//            Toast.makeText(BbcNewsActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
        }

        /**
         * display progress bar
         */
        @Override
        protected void onPreExecute() {

            //           super.onPreExecute();
           // bar.setVisibility(View.VISIBLE);
            //          newsAdapter.notifyDataSetChanged();
        }

        /**
         * update progress
         * @param value
         */
        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            bar.setProgress(value[0]);
            bar.setVisibility(View.VISIBLE);

        }
    }

    /**
     * List view adapter
     */

   private class MyAdapter extends BaseAdapter {
        /**
         * get item number
         * @return
         */

        @Override
       public int getCount(){
            return news_object.size();
        }

        /**
         * get item position
         * @param position
         * @return
         */

        @Override
       public News_object getItem(int position){
            return news_object.get(position);
        }

        /**
         * get item id
         * @param position
         * @return
         */

        @Override
       public long getItemId(int position){
            return position;
        }

        /**
         * put row view into list view
         * @param pos
         * @param old
         * @param parent
         * @return
         */
        @Override
       public View getView(int pos, View old, ViewGroup parent){
            View row = old;
            News_object rowNews = getItem(pos);
            row = getLayoutInflater().inflate(R.layout.bbc_news_row,null);
            TextView itemTitle = row.findViewById(R.id.news_content);
            itemTitle.setText(rowNews.getSubject());
            return row;

        }

   }
}
