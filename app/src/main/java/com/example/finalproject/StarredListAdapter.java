package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StarredListAdapter extends BaseAdapter {

    private Context context;
    private TGNewsOpener dbHelper;
    private SQLiteDatabase db;
    private List<TheGuardianArticle> elements = new ArrayList<>();
    private boolean isTablet;

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
        newView = inflater.inflate(R.layout.tg_starred_row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.starredArticleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.starredArticleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starredStarButton);
        starBtn.setImageResource(R.drawable.star_full);

        starBtn.setOnClickListener((click) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
            alertDialogBuilder.setTitle(R.string.tg_remov_dialog_title);
            alertDialogBuilder.setMessage(itemArticle.getTitle());

            alertDialogBuilder.setPositiveButton(R.string.tg_remov_dialog_yes, (click2, arg) -> {
                dbHelper = new TGNewsOpener(context);
                db = dbHelper.getWritableDatabase();
                db.delete(TGNewsOpener.TABLE_NAME, TGNewsOpener.COL_WEB_ID + "= ?", new String[]{itemArticle.getId()});
                this.getElements().remove(position);
                this.notifyDataSetChanged();
            });

            alertDialogBuilder.setNegativeButton(R.string.tg_remov_dialog_cancel, (click3, arg) -> {
                alertDialogBuilder.create().dismiss();
            });
            alertDialogBuilder.create().show();
        });

        newView.setOnClickListener((click) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("SECTION", itemArticle.getSectionName());
            dataToPass.putString("DATE", itemArticle.getDate());
            dataToPass.putString("TITLE", itemArticle.getTitle());
            dataToPass.putString("URL", itemArticle.getUrl());


            AppCompatActivity mainActivity = (AppCompatActivity)context;
            FrameLayout detailFrameLayout = (FrameLayout)mainActivity.findViewById(R.id.DetailFrameLayout);

            if (detailFrameLayout == null)
                isTablet = false;
            else
                isTablet = true;

            if(isTablet) {
                TGNewsDetailsFragment dFragment = new TGNewsDetailsFragment();
                dFragment.setArguments(dataToPass); //pass it a bundle for information

                mainActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.DetailFrameLayout, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.

            }
            else //is phone
            {
                Intent nextActivity = new Intent(context, TGNewsEmptyFrameActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                context.startActivity(nextActivity); //make the transition
            }
        });

        return newView;
    }
}
