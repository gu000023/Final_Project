package com.example.finalproject;

import android.view.View;
import android.widget.ImageButton;
import com.google.android.material.snackbar.Snackbar;


public class StarButtonOnClickListener implements View.OnClickListener {

    private TheGuardianArticle article;

    public StarButtonOnClickListener(TheGuardianArticle article) {
        super();
        this.article = article;
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

        Snackbar snackbar = Snackbar.make(starBtn, "News starred", 2000);
        snackbar.setAction("UNDO", click -> unStar(starBtn));
        snackbar.show();

    }

    public void unStar(ImageButton starBtn) {
        starBtn.setImageResource(R.drawable.star_empty);
        article.setStarred(false);

        Snackbar snackbar = Snackbar.make(starBtn, "News unstarred", 2000);
        snackbar.setAction("UNDO", click -> setStarred(starBtn));
        snackbar.show();

    }
}
