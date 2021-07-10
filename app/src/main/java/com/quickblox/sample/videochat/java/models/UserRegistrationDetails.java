package com.quickblox.sample.videochat.java.models;

public class UserRegistrationDetails {

    private String phone_number;
    private String  name;
    private String  email;
    private String  dob;
    private String  sign_language;
    private String  type;
    private String  city;
    private String image;

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone) {
        this.phone_number = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSign_language() {
        return sign_language;
    }

    public void setSign_language(String sign_language) {
        this.sign_language = sign_language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
