package com.example.desy.everlanceproject2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by desy on 2/19/16.
 */
public class GasStation implements Parcelable{
    private String gasStation;
    private String price;
    private String distance;
    private String address;
    private String lat;
    private String lng;

    public GasStation() {

    }

    protected GasStation(Parcel in) {
        gasStation = in.readString();
        price = in.readString();
        distance = in.readString();
        address = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<GasStation> CREATOR = new Creator<GasStation>() {
        @Override
        public GasStation createFromParcel(Parcel in) {
            return new GasStation(in);
        }

        @Override
        public GasStation[] newArray(int size) {
            return new GasStation[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gasStation);
        dest.writeString(price);
        dest.writeString(distance);
        dest.writeString(address);
        dest.writeString(lat);
        dest.writeString(lng);
    }

}
