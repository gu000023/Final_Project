package com.example.androidlabs;

class Message {
    long id;
    String message;
    boolean issent;

    public Message(String message, boolean issent, long id){
        this.message=message;
        this.issent=issent;
        this.id=id;
    }

    public Message(String message, boolean issent){
        this(message,issent,0);
    }



    public String getMessage(){
        return this.message;
    }

    public boolean getSendReceive(){return this.issent;}

    public boolean isSent(){
        return this.issent;
    }

    public long getId(){return this.id;}

    public void update(String m){
        this.message=m;
    }
}