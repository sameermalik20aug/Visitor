package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Visit implements Parcelable {
    String toMeet;
    String purpose;
    String date;

    protected Visit(Parcel in) {
        toMeet = in.readString();
        purpose = in.readString();
        date = in.readString();
    }

    public Visit(String toMeet, String purpose, String date) {
        this.toMeet = toMeet;
        this.purpose = purpose;
        this.date = date;
    }

    public Visit() {
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toMeet);
        dest.writeString(purpose);
        dest.writeString(date);
    }
}
