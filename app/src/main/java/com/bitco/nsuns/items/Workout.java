package com.bitco.nsuns.items;

import java.util.ArrayList;

public class Workout {

    private Exercise primaryExercise;
    private Exercise secondaryExercise;

    // todo: implement accessories.
    private ArrayList<Exercise> accessories;

    public Workout(Exercise e1, Exercise e2) {
        this.primaryExercise = e1;
        this.secondaryExercise = e2;
    }

    public Exercise getPrimaryExercise() {
        return primaryExercise;
    }

    public Exercise getSecondaryExercise() {
        return secondaryExercise;
    }
}
