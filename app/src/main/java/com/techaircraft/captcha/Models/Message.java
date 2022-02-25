package com.techaircraft.captcha.Models;

public class Message {

    private String title, body, date_time;

    public Message(String title, String body, String date_time) {
        this.title = title;
        this.body = body;
        this.date_time = date_time;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate_time() {
        return date_time;
    }
}
