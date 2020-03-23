package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticlesListAdapter extends BaseAdapter {

    private Context context;
    private MyOpener dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    private List<TheGuardianArticle> elements = new ArrayList<>();

    public ArticlesListAdapter(Context context) {
        super();
        this.context = context;
    }

    public List<TheGuardianArticle> getElements() {
        return elements;
    }

    @Override
    public int getCount() {
        return getElements().size();
    }

    @Override
    public Object getItem(int position) {
        return getElements().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TheGuardianArticle itemArticle = (TheGuardianArticle) getItem(position);
        View newView;
        newView = inflater.inflate(R.layout.row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.articleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.articleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starButton);
        if (itemArticle.isStarred())
            starBtn.setImageResource(R.drawable.star_full);
        else {
            dbHelper = new MyOpener(context);
            db = dbHelper.getWritableDatabase();
            String [] columns = {MyOpener.COL_WEB_ID};
            results = db.query(false,
                    MyOpener.TABLE_NAME,
                    columns,
                    MyOpener.COL_WEB_ID + " like ?",
                    new String[] {itemArticle.getId()},
                    null, null, null, null);
            if (results.getCount() == 0)
                starBtn.setImageResource(R.drawable.star_empty);
            else
                starBtn.setImageResource(R.drawable.star_full);
        }
        starBtn.setOnClickListener( new StarButtonOnClickListener(itemArticle, context));

        newView.setOnClickListener((click) -> {
            Intent goToDetails = new Intent(context, NewsDetailsActivity.class);
            goToDetails.putExtra("SECTION", itemArticle.getSectionName());
            goToDetails.putExtra("DATE", itemArticle.getDate());
            goToDetails.putExtra("TITLE", itemArticle.getTitle());
            goToDetails.putExtra("URL", itemArticle.getUrl());
            context.startActivity(goToDetails);
        });

        return newView;
    }

}


