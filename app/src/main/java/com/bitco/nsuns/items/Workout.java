package com.bitco.nsuns.items;

import java.util.ArrayList;

public class Workout {

    private Exercise e1;
    private Exercise e2;

    // todo: implement accessories.
    private ArrayList<Exercise> accessories;

    public Workout(Exercise e1, Exercise e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public Exercise getE1() {
        return e1;
    }

    public Exercise getE2() {
        return e2;
    }
}
