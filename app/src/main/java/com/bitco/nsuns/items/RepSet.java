package com.bitco.nsuns.items;

import android.os.Parcel;
import android.os.Parcelable;

public class RepSet implements Parcelable {

    private float percent;
    private float weight;
    private int reps;
    private boolean isAmrap;


    public RepSet(float percent, int reps, boolean isAmrap) {
        this.percent = percent;
        this.reps = reps;
        this.isAmrap = isAmrap;
    }

    public RepSet(float percent, float weight, int reps, boolean isAmrap) {
        this.percent = percent;
        this.weight = weight;
        this.reps = reps;
        this.isAmrap = isAmrap;
    }

    /**
     * Calculates weight from a given training max.
     * @param tm to calculate set weight.
     */
    public void calculateWeight(float tm) {
        this.weight = Calculate.round(tm * this.percent, 2.5f);
    }

    public float getPercent() {
        return percent;
    }

    public float getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public boolean isAmrap() {
        return isAmrap;
    }


    // Parcelables
    public RepSet(Parcel source) {
        this.percent = source.readFloat();
        this.weight = source.readFloat();
        this.reps = source.readInt();
        this.isAmrap = source.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeFloat(percent);
        dest.writeFloat(weight);
        dest.writeInt(reps);
        dest.writeBoolean(isAmrap);
    }

    public static final Parcelable.Creator<RepSet> CREATOR = new Parcelable.Creator<RepSet>() {
        @Override
        public RepSet createFromParcel(Parcel source) {
            return new RepSet(source);
        }

        @Override
        public RepSet[] newArray(int size) {
            return new RepSet[size];
        }
    };
}
