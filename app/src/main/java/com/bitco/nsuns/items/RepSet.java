package com.bitco.nsuns.items;

public class RepSet {

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



}
