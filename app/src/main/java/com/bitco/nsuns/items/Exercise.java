package com.bitco.nsuns.items;

import java.util.ArrayList;

public class Exercise {

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
}
