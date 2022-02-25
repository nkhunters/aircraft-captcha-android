package com.techaircraft.captcha.Models;

public class Captcha {

    private String id, user_id, image, time, captcha_type, captcha_text;

    public Captcha(String id, String user_id, String image, String time, String captcha_type, String captcha_text) {
        this.id = id;
        this.user_id = user_id;
        this.image = image;
        this.time = time;
        this.captcha_type = captcha_type;
        this.captcha_text = captcha_text;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getCaptcha_type() {
        return captcha_type;
    }

    public String getCaptcha_text() {
        return captcha_text;
    }
}
