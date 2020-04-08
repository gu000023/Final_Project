package com.example.finalproject;

/**
 * define adapter for new item
 * author : Yun Zhu
 */
public class News_object {
    private long id;
    private String subject;
    private String intro;
    private String url;
    private String date;


    public News_object(long id,String subject, String intro, String url, String date){
        this.setId(id);
        this.setSubject(subject);
        this.setIntro(intro);
        this.setUrl(url);
        this.setDate(date);
    }


    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getSubject(){return subject;}
    public void setSubject(String subject){this.subject = subject;}

    public String getIntro(){return intro;}
    public void setIntro(String intro){this.intro = intro;}

    public String getUrl(){return url;}
    public void setUrl(String url){this.url = url;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}



}
