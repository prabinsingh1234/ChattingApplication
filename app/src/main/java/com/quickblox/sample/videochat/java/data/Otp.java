package com.quickblox.sample.videochat.java.data;

public class Otp {
    String phone_number;
    String otp;
    int status;

    String token;

    private String  name;
    private String  email;
    private String  dob;
    private String gender;
    private String  sign_language;
    private String  type;
    private String  city;
    private String image;
    private String id;
    String phone;

    int count;

    public void setcount(int count) {
        this.count = count;
    }
    public int getcount() {
        return count;
    }

    public void setToken(String token) {
       this.token = token;
    }
    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPhone_number(String a)
    {
        phone_number = a;
    }

    public void setOtp(String a){
        otp = a;
    }

    public String getPhone_number()
    {
        return phone_number;
    }

    public String getOtp()
    {
        return otp;
    }

    public void setStatus(int s)
    {
        status = s;
    }

    public int getStatus()
    {
        return status;
    }

}
