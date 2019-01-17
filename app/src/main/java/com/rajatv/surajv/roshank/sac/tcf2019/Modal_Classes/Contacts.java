package com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacts implements Parcelable {


    private String name, profile_pic, designation, phone, whatsapp,userUID;
    int value;

    public Contacts() {
    }

    public Contacts(String name, String profile_pic, String designation, String phone, String whatsapp,String userUID,int value) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.designation = designation;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.userUID=userUID;
        this.value = value;
    }

    protected Contacts(Parcel in) {
        name = in.readString();
        profile_pic = in.readString();
        designation = in.readString();
        phone = in.readString();
        whatsapp = in.readString();
        userUID = in.readString();
        value = in.readInt();
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setWhatsapp(String whatsapp) {

        this.whatsapp = whatsapp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(profile_pic);
        dest.writeString(designation);
        dest.writeString(phone);
        dest.writeString(whatsapp);
        dest.writeString(userUID);
        dest.writeInt(value);
    }
}