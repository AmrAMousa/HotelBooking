package com.example.hotelbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelModel implements Parcelable {
    String hotelDescription;
    String hotelId;
    String hotelImage;
    String hotelLocation;
    String hotelName;
    String hotelPrice;
    String hotelRate;
    String hotelType;
    String hotelLocationOnMap;

    public HotelModel(String hotelDescription, String hotelId, String hotelImage, String hotelLocation, String hotelName, String hotelPrice, String hotelRate, String hotelType, String hotelLocationOnMap) {
        this.hotelDescription = hotelDescription;
        this.hotelId = hotelId;
        this.hotelImage = hotelImage;
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.hotelPrice = hotelPrice;
        this.hotelRate = hotelRate;
        this.hotelType = hotelType;
        this.hotelLocationOnMap = hotelLocationOnMap;
    }

    protected HotelModel(Parcel in) {
        hotelDescription = in.readString();
        hotelId = in.readString();
        hotelImage = in.readString();
        hotelLocation = in.readString();
        hotelName = in.readString();
        hotelPrice = in.readString();
        hotelRate = in.readString();
        hotelType = in.readString();
        hotelLocationOnMap = in.readString();
    }

    public static final Creator<HotelModel> CREATOR = new Creator<HotelModel>() {
        @Override
        public HotelModel createFromParcel(Parcel in) {
            return new HotelModel(in);
        }

        @Override
        public HotelModel[] newArray(int size) {
            return new HotelModel[size];
        }
    };

    public String getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getHotelRate() {
        return hotelRate;
    }

    public void setHotelRate(String hotelRate) {
        this.hotelRate = hotelRate;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getHotelLocationOnMap() {
        return hotelLocationOnMap;
    }

    public void setHotelLocationOnMap(String hotelLocationOnMap) {
        this.hotelLocationOnMap = hotelLocationOnMap;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelDescription);
        dest.writeString(hotelId);
        dest.writeString(hotelImage);
        dest.writeString(hotelLocation);
        dest.writeString(hotelName);
        dest.writeString(hotelPrice);
        dest.writeString(hotelRate);
        dest.writeString(hotelType);
        dest.writeString(hotelLocationOnMap);
    }
}
