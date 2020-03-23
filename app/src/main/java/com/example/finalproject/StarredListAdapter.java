package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class StarredListAdapter extends BaseAdapter {

    private Context context;
    private MyOpener dbHelper;
    private SQLiteDatabase db;
    private List<TheGuardianArticle> elements = new ArrayList<>();

    public StarredListAdapter(Context context) {
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
        newView = inflater.inflate(R.layout.starred_row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.starredArticleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.starredArticleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starredStarButton);
        starBtn.setImageResource(R.drawable.star_full);

        starBtn.setOnClickListener((click) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
            alertDialogBuilder.setTitle("Remove this News from Starred?");
            alertDialogBuilder.setMessage(itemArticle.getTitle());

            alertDialogBuilder.setPositiveButton("Yes", (click2, arg) -> {
                dbHelper = new MyOpener(context);
                db = dbHelper.getWritableDatabase();
                db.delete(MyOpener.TABLE_NAME, MyOpener.COL_WEB_ID + "= ?", new String[]{itemArticle.getId()});
                this.getElements().remove(position);
                this.notifyDataSetChanged();
            });

            alertDialogBuilder.setNegativeButton("Cancel", (click3, arg) -> {
                alertDialogBuilder.create().dismiss();
            });
            alertDialogBuilder.create().show();
        });

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
