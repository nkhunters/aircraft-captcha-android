package com.techaircraft.captcha.ModelsNew;

public class CaptchaResponse {

    private boolean error;
    private Captcha captcha;

    public CaptchaResponse(boolean error, Captcha captcha) {
        this.error = error;
        this.captcha = captcha;
    }

    public boolean isError() {
        return error;
    }

    public Captcha getCaptcha() {
        return captcha;
    }
}
