package com.example.finalproject;
/**
 * deal with favorite page
 * Author: Yun Zhu
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class bbc_fav extends AppCompatActivity {
    SQLiteDatabase db;
    ArrayList<News_object> news_object = new ArrayList<>();
    MyAdapter adapter;
    ListView newsList;
    /**
     * show favorite page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_fav);

        EditText favEdit = findViewById(R.id.bbc_et3);
        Intent fromMain  = getIntent();
        String emailFromPage1 = fromMain.getStringExtra("typeEmail");
        favEdit.setText(emailFromPage1);
        ListView bbcFavList = (ListView)findViewById(R.id.bbc_lv2);
      //  bbcFavList.setAdapter(adapter = new BbcAdapter(this, R.layout.bbc_news_row, news_object));
        bbcFavList.setAdapter(adapter= new MyAdapter());
        BbcOpener opener = new BbcOpener(this);
        db = opener.getWritableDatabase();

        String[] dbColumns = {BbcOpener.COL_ID, BbcOpener.COL_TITLE, BbcOpener.COL_INTRO, BbcOpener.COL_DATE, BbcOpener.COL_URL};
        Cursor results = db.query(false, BbcOpener.TABLE_NAME, dbColumns, null, null, null, null, null, null);

        while (results.moveToNext()){
            String title = results.getString(results.getColumnIndex(BbcOpener.COL_TITLE));
            String intro = results.getString(results.getColumnIndex(BbcOpener.COL_INTRO));
            String date = results.getString(results.getColumnIndex(BbcOpener.COL_DATE));
            String url = results.getString(results.getColumnIndex(BbcOpener.COL_URL));
            long id = results.getLong(results.getColumnIndex(BbcOpener.COL_ID));
            news_object.add(new News_object(id,title,intro,url,date));
        }
        adapter.notifyDataSetChanged();
        //snack bar
        Snackbar.make(bbcFavList, this.getString(R.string.fav_snack), Snackbar.LENGTH_SHORT).show();

        Button btn_back_main = (Button) findViewById(R.id.bbc_bt7);
        if(btn_back_main != null) {
            btn_back_main.setOnClickListener(click -> {
                //toast
                Toast.makeText(this, this.getString(R.string.fav_toast), Toast.LENGTH_SHORT).show();

                //AlertDialog
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(this.getString(R.string.fav_alert_title))
                        //What is the message:

                        .setMessage(this.getString(R.string.fav_alert_message))
                        .setNegativeButton(this.getString(R.string.fav_alert_close), (clk, arg) -> { }).create().show();
                Intent goToMain = new Intent(this, bbc_main.class);
                startActivityForResult(goToMain, 30);
            });
        }

        bbcFavList.setOnItemClickListener((list,item,position,id)->{
            Bundle dataToPass = new Bundle();
            BbcFragment bbcFragment = new BbcFragment();

            dataToPass.putString(bbc_main.SUBJECT, news_object.get(position).getSubject());
            dataToPass.putString(bbc_main.INTRO, news_object.get(position).getIntro());
            dataToPass.putString(bbc_main.DATE, news_object.get(position).getDate());
            dataToPass.putString(bbc_main.URL, news_object.get(position).getUrl());

            Intent detailPage = new Intent (this,BbcEmptyActivity.class);
            detailPage.putExtras(dataToPass);
            startActivity(detailPage);
        });

        bbcFavList.setOnItemLongClickListener((p,b,pos,id) -> {
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(this.getString(R.string.fav_delete))
                    .setPositiveButton(this.getString(R.string.fav_yes),(click, arg) -> {
                        db.delete(BbcOpener.TABLE_NAME, BbcOpener.COL_ID + "=?", new String[] {Long.toString(news_object.get(pos).getId())});
                        news_object.remove(pos);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(this.getString(R.string.fav_no), (click,arg)->{}).create().show();
            return true;
        });
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
