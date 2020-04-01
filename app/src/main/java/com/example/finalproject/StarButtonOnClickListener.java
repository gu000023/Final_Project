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
    private TGNewsOpener dbHelper;
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

        dbHelper = new TGNewsOpener(context);
        db = dbHelper.getWritableDatabase();
        String [] columns = {TGNewsOpener.COL_WEB_ID};
        results = db.query(false,
                TGNewsOpener.TABLE_NAME,
                columns,
                TGNewsOpener.COL_WEB_ID + " like ?",
                new String[] {article.getId()},
                null, null, null, null);
        if (results.getCount() == 0) {
            newRowValues = new ContentValues();
            newRowValues.put(TGNewsOpener.COL_DATE, article.getDate());
            newRowValues.put(TGNewsOpener.COL_TITLE, article.getTitle());
            newRowValues.put(TGNewsOpener.COL_URL, article.getUrl());
            newRowValues.put(TGNewsOpener.COL_SECTION, article.getSectionName());
            newRowValues.put(TGNewsOpener.COL_WEB_ID, article.getId());
            db.insert(TGNewsOpener.TABLE_NAME, null, newRowValues);
        }

        Snackbar snackbar = Snackbar.make(starBtn, R.string.tg_snack_starred, 2000);
        snackbar.setAction(R.string.tg_snack_undo_btn, click -> unStar(starBtn));
        snackbar.show();

    }

    public void unStar(ImageButton starBtn) {
        starBtn.setImageResource(R.drawable.star_empty);
        article.setStarred(false);

        db.delete(TGNewsOpener.TABLE_NAME, TGNewsOpener.COL_WEB_ID + "= ?", new String[] {article.getId()});

        Snackbar snackbar = Snackbar.make(starBtn, R.string.tg_snack_star_remov, 2000);
        snackbar.setAction(R.string.tg_snack_undo_btn, click -> setStarred(starBtn));
        snackbar.show();

    }
}
