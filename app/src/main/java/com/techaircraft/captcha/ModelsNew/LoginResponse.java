package com.techaircraft.captcha.ModelsNew;

public class LoginResponse {

    private boolean error;
    private User message;

    public LoginResponse(boolean error, User message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public User getMessage() {
        return message;
    }
}
