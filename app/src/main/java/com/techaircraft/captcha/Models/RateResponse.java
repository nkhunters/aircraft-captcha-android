package com.techaircraft.captcha.Models;

public class RateResponse {

    private Boolean error;
    private Rates rates;

    public RateResponse(Boolean error, Rates rates) {
        this.error = error;
        this.rates = rates;
    }

    public Boolean getError() {
        return error;
    }

    public Rates getRates() {
        return rates;
    }
}
