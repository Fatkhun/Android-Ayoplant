package com.allfeature;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 12/3/2017.
 */

public class EncyPlant implements Parcelable {

    public static final Creator<EncyPlant> CREATOR = new Creator<EncyPlant>() {
        @Override
        public EncyPlant createFromParcel(Parcel in) {
            return new EncyPlant(in);
        }

        @Override
        public EncyPlant[] newArray(int size) {
            return new EncyPlant[size];
        }
    };

    private String type;
    private String image;
    private String name;
    private String desc;
    private float temp;
    private float humidity;

    public EncyPlant(String type, String image, String name, String desc, float temp, float humidity){
        this.type =type;
        this.image=image;
        this.name=name;
        this.desc=desc;
        this.temp=temp;
        this.humidity=humidity;
    }

    protected EncyPlant(Parcel in) {
        type = in.readString();
        image = in.readString();
        name = in.readString();
        desc = in.readString();
        temp = in.readFloat();
        humidity = in.readFloat();
    }

    public String getType(){
        return type;
    }

    public String getImage(){ return image;}

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public Float getTemp(){
        return temp;
    }

    public Float getHumidity(){ return humidity;}

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeFloat(temp);
        dest.writeFloat(humidity);
    }
}
