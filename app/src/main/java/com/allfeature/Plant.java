package com.allfeature;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 12/3/2017.
 */

public class Plant implements Parcelable {
    private int id;
    private String name;
    private String type;
    private float temp;
    private float humadity;
    private String image;

    public Plant(String name, String type, float temp, float humadity, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.temp = temp;
        this.humadity = humadity;
        this.image = image;
    }

    protected Plant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        temp = in.readFloat();
        humadity = in.readFloat();
        image = in.readString();
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public  float getTemp() { return  temp; }

    public float getHumadity() { return humadity; }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeFloat(temp);
        parcel.writeFloat(humadity);
        parcel.writeString(image);
    }
}
