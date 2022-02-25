package com.techaircraft.captcha.ModelsNew;

public class Captcha {

    private String id, image, captcha_type, right_count, wrong_count, skip_count, is_right, extra_time;

    public Captcha(String id, String image, String captcha_type, String right_count, String wrong_count, String skip_count, String is_right) {
        this.id = id;
        this.image = image;
        this.captcha_type = captcha_type;
        this.right_count = right_count;
        this.wrong_count = wrong_count;
        this.skip_count = skip_count;
        this.is_right = is_right;
    }

    public String getExtra_time() {
        return extra_time;
    }

    public void setExtra_time(String extra_time) {
        this.extra_time = extra_time;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getCaptcha_type() {
        return captcha_type;
    }

    public String getRight_count() {
        return right_count;
    }

    public String getWrong_count() {
        return wrong_count;
    }

    public String getSkip_count() {
        return skip_count;
    }

    public String getIs_right() {
        return is_right;
    }
}
