package com.example.finalproject;

import android.content.Context;
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
        //TODO see database id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView;
        TheGuardianArticle itemArticle = (TheGuardianArticle) getItem(position);
        newView = inflater.inflate(R.layout.row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.articleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.articleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starButton);
        if (itemArticle.isStarred())
            starBtn.setImageResource(R.drawable.star_full);
        else
            starBtn.setImageResource(R.drawable.star_empty);
        starBtn.setOnClickListener( new StarButtonOnClickListener(itemArticle));
        return newView;
    }

}


