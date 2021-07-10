package com.quickblox.sample.videochat.java.data;

public class RechargeHistory {
    private int Status;
    private String phone_number;
    private int Total_minutes;
    private int Remaining_minutes;
    private int Total_Amount;
    private String Date_of_recharge;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getTotal_minutes() {
        return Total_minutes;
    }

    public void setTotal_minutes(int total_minutes) {
        Total_minutes = total_minutes;
    }

    public int getRemaining_minutes() {
        return Remaining_minutes;
    }

    public void setRemaining_minutes(int remaining_minutes) {
        Remaining_minutes = remaining_minutes;
    }

    public int getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(int total_Amount) {
        Total_Amount = total_Amount;
    }

    /* public int getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(int total_amount) {
        Total_amount = total_amount;
    }*/

    public String getDate_of_recharge() {
        return Date_of_recharge;
    }

    public void setDate_of_recharge(String date_of_recharge) {
        Date_of_recharge = date_of_recharge;
    }
}
