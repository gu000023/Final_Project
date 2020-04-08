package com.example.finalproject;

/**
 * This Image class is to instantiate an object of Image. It has constructor of Image, and getters for the members.
 * @author  Zhe Lei
 * @version 1.0
 */
public class Image {
    protected long id;
    private String date;
    private String url;
    private String hdurl;
    private  String title;

    /**
     * constructor of Image class
     * @param id
     * @param date
     * @param url
     * @param hdurl
     */
    public Image(long id, String date, String url, String hdurl){
        this.id = id;
        this.date = date;
        this.url = url;
        this.hdurl = hdurl;
    }

    /**
     * getter of id
     * @return id
     */
    public long getId(){
        return id;
    }

    /**
     * getter of date
     * @return date
     */
    public String getDate(){return date;}

    /**
     * getter of url
     * @return url
     */
    public String getUrl(){return url;}

    /**
     * gtter of hdurl
     * @return hdurl
     */
    public String getHdurl(){return hdurl;}


}
