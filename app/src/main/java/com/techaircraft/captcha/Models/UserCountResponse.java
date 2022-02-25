package com.techaircraft.captcha.Models;

public class UserCountResponse {

    private Boolean error;
    private UserCount counts;

    public UserCountResponse(Boolean error, UserCount counts) {
        this.error = error;
        this.counts = counts;
    }

    public Boolean getError() {
        return error;
    }

    public UserCount getCounts() {
        return counts;
    }
}
