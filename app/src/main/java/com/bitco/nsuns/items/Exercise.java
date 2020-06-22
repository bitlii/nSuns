package com.bitco.nsuns.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Exercise implements Parcelable {

    private String name;
    private ArrayList<RepSet> sets;
    private float tm;

    public Exercise(String name, ArrayList<RepSet> sets, float tm) {
        this.name = name;
        this.sets = sets;
        this.tm = tm;
    }

    /**
     * Update the rep sets' weights according to the training max.
     */
    public void updateRepSets() {
        for (RepSet set : sets) {
            set.calculateWeight(tm);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<RepSet> getSets() {
        return sets;
    }

    public float getTm() {
        return tm;
    }


    // Parcelables
    public Exercise(Parcel source) {
        this.name = source.readString();
        this.sets = new ArrayList<>();
        source.readList(this.sets, RepSet.class.getClassLoader());
        this.tm = source.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeList(sets);
        dest.writeFloat(tm);

    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

}
