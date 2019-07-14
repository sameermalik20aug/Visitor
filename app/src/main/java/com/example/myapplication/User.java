package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    String name;
    String address;
    String phoneNum;
    ArrayList<Visit> visits;

    public void AddVisit(Visit visit){
        visits.add(visit);
    }

    public User(String name, String address, String phoneNum, ArrayList<Visit> visits) {
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.visits = visits;
    }

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        address = in.readString();
        phoneNum = in.readString();
        visits = in.createTypedArrayList(Visit.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phoneNum);
        dest.writeTypedList(visits);
    }
}
