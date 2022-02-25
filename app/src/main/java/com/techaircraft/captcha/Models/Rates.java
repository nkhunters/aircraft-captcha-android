package com.techaircraft.captcha.Models;

public class Rates {

    private String captcha_count, captcha_rate;

    public Rates(String captcha_count, String captcha_rate) {
        this.captcha_count = captcha_count;
        this.captcha_rate = captcha_rate;
    }

    public String getCaptcha_count() {
        return captcha_count;
    }

    public String getCaptcha_rate() {
        return captcha_rate;
    }
}
