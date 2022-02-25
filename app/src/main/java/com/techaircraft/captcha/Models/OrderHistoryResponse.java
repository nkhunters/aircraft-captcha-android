package com.techaircraft.captcha.Models;

import java.util.ArrayList;

public class OrderHistoryResponse {

    private boolean error;
    private ArrayList<Order> orders;

    public OrderHistoryResponse(boolean error, ArrayList<Order> orders) {
        this.error = error;
        this.orders = orders;
    }

    public boolean isError() {
        return error;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
