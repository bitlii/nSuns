package com.bitco.nsuns.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Workout implements Parcelable {

    private int id;
    private String name;
    private Exercise primaryExercise;
    private Exercise secondaryExercise;

    private ArrayList<Exercise> accessories;

    public Workout(Exercise e1, Exercise e2) {
        this.id = -1;
        this.name = "";
        this.primaryExercise = e1;
        this.secondaryExercise = e2;
        this.accessories = new ArrayList<>();
    }

    public Workout(Exercise e1, Exercise e2, ArrayList<Exercise> accessories) {
        this.id = -1;
        this.name = "";
        this.primaryExercise = e1;
        this.secondaryExercise = e2;
        this.accessories = accessories;
    }

    public Workout(int id, String name, Exercise primaryExercise, Exercise secondaryExercise, ArrayList<Exercise> accessories) {
        this.id = id;
        this.name = name;
        this.primaryExercise = primaryExercise;
        this.secondaryExercise = secondaryExercise;
        this.accessories = accessories;
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

    public void setName(String name) {
        this.name = name;
    }

    public Exercise getPrimaryExercise() {
        return primaryExercise;
    }

    public Exercise getSecondaryExercise() {
        return secondaryExercise;
    }

    public ArrayList<Exercise> getAccessories() {
        return accessories;
    }

    public Workout(Parcel source) {
        this.id = source.readInt();
        this.primaryExercise = source.readParcelable(Exercise.class.getClassLoader());
        this.secondaryExercise = source.readParcelable(Exercise.class.getClassLoader());
        this.accessories = new ArrayList<>();
        source.readList(this.accessories, Exercise.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeParcelable(primaryExercise, i);
        dest.writeParcelable(secondaryExercise, i);
        dest.writeList(this.accessories);
    }

    public static final Parcelable.Creator<Workout> CREATOR = new Parcelable.Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

}
