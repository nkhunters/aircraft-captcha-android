package com.techaircraft.captcha.Models;

public class UserCount {

    private String right_count, wrong_count, skip_count, total_earning, captcha_count, captcha_rate, unique_id, auto_approve;

    public UserCount(String right_count, String wrong_count, String skip_count, String total_earning, String captcha_count, String captcha_rate, String unique_id, String auto_approve) {
        this.right_count = right_count;
        this.wrong_count = wrong_count;
        this.skip_count = skip_count;
        this.total_earning = total_earning;
        this.captcha_count = captcha_count;
        this.captcha_rate = captcha_rate;
        this.unique_id = unique_id;
        this.auto_approve = auto_approve;
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

    public String getTotal_earning() {
        return total_earning;
    }

    public String getCaptcha_count() {
        return captcha_count;
    }

    public String getCaptcha_rate() {
        return captcha_rate;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getAuto_approve() {
        return auto_approve;
    }
}
