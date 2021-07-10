package com.quickblox.sample.videochat.java.models;

import android.graphics.Bitmap;


public class ContactModels  implements  Comparable<ContactModels>{

    public String id = "";
    public String name = "";
    public String mobileNumber = "";
    public Bitmap photo;

    public ContactModels (String number,String name,Bitmap photo){
        this.name=name;
        this.mobileNumber=number;
        this.photo = photo;
    }

    public String getName(){

        return name;

    }

    public String getMobileNumber(){
        return mobileNumber;
    }

    public boolean equals( Object o){
        ContactModels b = (ContactModels)o;
        return this.name.equals(b.name) && this.mobileNumber.equals(b.mobileNumber);
    }



    @Override
    public int compareTo(ContactModels contactModels) {
        return this.name.compareTo(contactModels.name);
    }
}
