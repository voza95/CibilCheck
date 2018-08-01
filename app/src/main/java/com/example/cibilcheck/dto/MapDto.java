package com.example.cibilcheck.dto;

import android.os.Parcel;
import android.os.Parcelable;


public class MapDto implements Parcelable {

    public String Lat;
    public String Lng;
    public String measureName;
    public String measureValue;

    public MapDto() {
    }

    protected MapDto(Parcel in) {
        Lat = in.readString();
        Lng = in.readString();
        measureName = in.readString();
        measureValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Lat);
        dest.writeString(Lng);
        dest.writeString(measureName);
        dest.writeString(measureValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MapDto> CREATOR = new Creator<MapDto>() {
        @Override
        public MapDto createFromParcel(Parcel in) {
            return new MapDto(in);
        }

        @Override
        public MapDto[] newArray(int size) {
            return new MapDto[size];
        }
    };
}
