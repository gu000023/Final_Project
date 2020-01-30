package com.example.androidlabs;

class Message {
    String message;
    boolean issent;

    public Message(String message, boolean issent){
        this.message=message;
        this.issent=issent;
    }

    public String getMessage(){
        return this.message;
    }

    public boolean isSent(){
        return this.issent;
    }
}
