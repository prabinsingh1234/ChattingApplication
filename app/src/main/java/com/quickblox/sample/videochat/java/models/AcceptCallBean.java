package com.quickblox.sample.videochat.java.models;

public class AcceptCallBean {

    private int status;
    private String  number;
    private String purpose;
    private String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //{"status": 1, "data": {"number": "9947670277", "purpose": "gdff", "name": "Rahim"}}
}
