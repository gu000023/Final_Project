package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.material.snackbar.Snackbar;


public class StarButtonOnClickListener implements View.OnClickListener {

    private TheGuardianArticle article;
    private Context context;
    private MyOpener dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    private ContentValues newRowValues;

    public StarButtonOnClickListener(TheGuardianArticle article, Context context) {
        super();
        this.article = article;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        ImageButton starBtn = (ImageButton)v;
        if (article.isStarred() == false) {
            setStarred(starBtn);
        }
        else {
            unStar(starBtn);
        }
    }

    public void setStarred(ImageButton starBtn) {
        starBtn.setImageResource(R.drawable.star_full);
        article.setStarred(true);

        dbHelper = new MyOpener(context);
        db = dbHelper.getWritableDatabase();
        String [] columns = {MyOpener.COL_WEB_ID};
        results = db.query(false,
                MyOpener.TABLE_NAME,
                columns,
                MyOpener.COL_WEB_ID + " like ?",
                new String[] {article.getId()},
                null, null, null, null);
        if (results.getCount() == 0) {
            newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_DATE, article.getDate());
            newRowValues.put(MyOpener.COL_TITLE, article.getTitle());
            newRowValues.put(MyOpener.COL_URL, article.getUrl());
            newRowValues.put(MyOpener.COL_SECTION, article.getSectionName());
            newRowValues.put(MyOpener.COL_WEB_ID, article.getId());
            db.insert(MyOpener.TABLE_NAME, null, newRowValues);
        }

        Snackbar snackbar = Snackbar.make(starBtn, "News starred", 2000);
        snackbar.setAction("UNDO", click -> unStar(starBtn));
        snackbar.show();

    }

    public void unStar(ImageButton starBtn) {
        starBtn.setImageResource(R.drawable.star_empty);
        article.setStarred(false);

        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_WEB_ID + "= ?", new String[] {article.getId()});

        Snackbar snackbar = Snackbar.make(starBtn, "News unstarred", 2000);
        snackbar.setAction("UNDO", click -> setStarred(starBtn));
        snackbar.show();

    }
}
