package com.techaircraft.captcha.Models;

public class Order {

    private String id, order_date, approval_date, total_earning, paid_amount, status;

    public Order(String id, String order_date, String approval_date, String total_earning, String paid_amount, String status) {
        this.id = id;
        this.order_date = order_date;
        this.approval_date = approval_date;
        this.total_earning = total_earning;
        this.paid_amount = paid_amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getApproval_date() {
        return approval_date;
    }

    public String getTotal_earning() {
        return total_earning;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public String getStatus() {
        return status;
    }
}
