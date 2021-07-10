package com.quickblox.sample.videochat.java.data;

public class InsertRecharge {

    private int plan_id;
    private int amount;
    private int transaction_id;
    private String date_of_recharge;
    private String phone_number;

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDate_of_recharge() {
        return date_of_recharge;
    }

    public void setDate_of_recharge(String date_of_recharge) {
        this.date_of_recharge = date_of_recharge;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
