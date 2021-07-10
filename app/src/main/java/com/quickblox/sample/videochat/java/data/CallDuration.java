package com.quickblox.sample.videochat.java.data;

public class CallDuration {

    private String phone_number;
    private double minutes;
    private int status;
    private int Price;
    private int Remaining_Amount;
    private int Remaining_minutes;

    public int getRemaining_Amount() {
        return Remaining_Amount;
    }

    public void setRemaining_Amount(int remaining_Amount) {
        Remaining_Amount = remaining_Amount;
    }

    public int getRemaining_minutes() {
        return Remaining_minutes;
    }

    public void setRemaining_minutes(int remaining_minutes) {
        Remaining_minutes = remaining_minutes;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
