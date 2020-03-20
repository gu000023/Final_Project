package com.example.finalproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TheGuardianArticle {
    private String date = "";
    private String title = "";
    private String url = "";
    private String sectionName = "";
    private String id = "";
    private boolean starred = false;

    public TheGuardianArticle(String date, String title, String url, String id, String sectionName, boolean starred) throws ParseException {
        if (date != null && date != "") {
            if (date.charAt(date.length() - 1) == 'Z')
                date = date.substring(0, date.length() - 1);
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date tempDate = sdfIn.parse(date);
            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.date = sdfOut.format(tempDate);
        }
        this.title = title;
        this.url = url;
        this.sectionName = sectionName;
        this.id = id;
        this.starred = starred;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
