package com.bitco.nsuns.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Exercise implements Parcelable {

    private int id;
    private String name;
    private ArrayList<RepSet> sets;
    private float tm;

    public Exercise(String name, ArrayList<RepSet> sets, float tm) {
        this.id = -1;
        this.name = name;
        this.sets = sets;
        this.tm = tm;
        this.updateRepSets();
    }

    public Exercise(int id, String name, ArrayList<RepSet> sets, float tm) {
        this.id = id;
        this.name = name;
        this.sets = sets;
        this.tm = tm;
        this.updateRepSets();
    }


    /**
     * Constructor for a new Exercise object. This is mainly used for accessories where the TM
     * is irrelevant for the accessory's sets' weights.
     * @param name of the exercise.
     * @param sets the arraylist of RepSets that represent the exercise.
     */
    public Exercise(String name, ArrayList<RepSet> sets) {
        this.id = -1;
        this.name = name;
        this.sets = sets;
        this.tm = 0;
    }

    /**
     * Update the rep sets' weights according to the training max.
     */
    public void updateRepSets() {
        for (RepSet set : sets) {
            set.calculateWeight(this.tm);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setTm(float tm) {
        this.tm = tm;
        this.updateRepSets();
    }

    public void setName(String name) {
        this.name = name;
    }

    // Parcelables
    public Exercise(Parcel source) {
        this.id = source.readInt();
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
        dest.writeInt(id);
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
