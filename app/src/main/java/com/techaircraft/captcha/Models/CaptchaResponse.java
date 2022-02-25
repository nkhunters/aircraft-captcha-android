package com.techaircraft.captcha.Models;

import java.util.ArrayList;

public class CaptchaResponse {

    private Boolean error;
    private ArrayList<Captcha> captchas;

    public CaptchaResponse(Boolean error, ArrayList<Captcha> captchas) {
        this.error = error;
        this.captchas = captchas;
    }

    public Boolean getError() {
        return error;
    }

    public ArrayList<Captcha> getCaptchas() {
        return captchas;
    }
}
