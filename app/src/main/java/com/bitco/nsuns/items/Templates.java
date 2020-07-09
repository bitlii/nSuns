package com.bitco.nsuns.items;

import java.util.ArrayList;
import java.util.Collections;

public class Templates {

    private static final float[] PRIMARY_PERCENTS_1 = {
            0.75f, 0.85f, 0.95f,
            0.9f, 0.85f, 0.8f,
            0.75f, 0.7f, 0.65f
    };

    private static final float[] PRIMARY_PERCENTS_2 = {
            0.65f, 0.75f, 0.85f,
            0.85f, 0.85f, 0.8f,
            0.75f, 0.7f, 0.65f
    };

    private static final float[] PRIMARY_PERCENTS_FOCUS = {
            0.725f, 0.725f, 0.725f, 0.725f, 0.725f, 0.725f, 0.725f, 0.725f
    };

    private static final int[] PRIMARY_REPS_1 = {
            5, 3, 1, 3, 3, 3, 3, 3, 3
    };

    private static final int[] PRIMARY_REPS_2 = {
            5, 3, 1, 3, 5, 3, 5, 3, 5
    };

    private static final int[] PRIMARY_REPS_3 = {
            5, 3, 1, 3, 3, 3, 5, 5, 5
    };

    private static final int[] PRIMARY_REPS_4 = {
            8, 6, 4, 4, 4, 5, 6, 7, 8
    };

    private static final int[] PRIMARY_REPS_FOCUS = {
            3, 3, 3, 3, 3, 3, 3, 3
    };


    private static final boolean[] PRIMARY_ISAMRAP_1 = {
            false, false, true, false, false, false, false, false, true
    };

    private static final boolean[] PRIMARY_ISAMRAP_2 = {
            false, false, false, false, false, false, false, false, true
    };

    private static final float[] SECONDARY_PERCENTS_1 = {
            0.5f, 0.6f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f
    };

    private static final float[] SECONDARY_PERCENTS_2 = {
            0.35f, 0.45f, 0.55f, 0.55f, 0.55f, 0.55f, 0.55f, 0.55f
    };

    private static final float[] SECONDARY_PERCENTS_3 = {
            0.4f, 0.5f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f
    };

    private static final float[] SECONDARY_PERCENTS_FOCUS = {
            0.5625f, 0.5625f, 0.5625f, 0.5625f, 0.5625f, 0.5625f
    };

    private static final int[] SECONDARY_REPS_1 = {
            5, 5, 3, 5, 7, 4, 6, 8
    };

    private static final int[] SECONDARY_REPS_2 = {
            6, 5, 3, 5, 7, 4, 6, 8
    };

    private static final int[] SECONDARY_REPS_FOCUS = {
            3, 3, 3, 3, 3, 3
    };

    private static final boolean[] SECONDARY_ISAMRAP = {
            false, false, false, false, false, false, false, false, false
    };

    private static final boolean[] SECONDARY_ISAMRAP_FOCUS = {
            false, false, false, false, false, false
    };


    public static ArrayList<Workout> create4day(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Workout> workouts = new ArrayList<>();

        workouts.add(new Workout(
                new Exercise("Bench", createRepSets(PRIMARY_PERCENTS_2, PRIMARY_REPS_4, PRIMARY_ISAMRAP_2), benchTm),
                new Exercise("OHP", createRepSets(SECONDARY_PERCENTS_1, SECONDARY_REPS_2, SECONDARY_ISAMRAP), ohpTm)
        ));
        workouts.add(new Workout(
                new Exercise("Squat", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_3, PRIMARY_ISAMRAP_1), squatTm),
                new Exercise("Sumo Deadlift", createRepSets(SECONDARY_PERCENTS_1, SECONDARY_REPS_1, SECONDARY_ISAMRAP), dlTm)
        ));
        workouts.add(new Workout(
                new Exercise("Bench", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_2, PRIMARY_ISAMRAP_1), benchTm),
                new Exercise("CG Bench", createRepSets(SECONDARY_PERCENTS_3, PRIMARY_REPS_2, SECONDARY_ISAMRAP), benchTm)
        ));
        workouts.add(new Workout(
                new Exercise("Deadlift", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_1, PRIMARY_ISAMRAP_1), dlTm),
                new Exercise("Front Squat", createRepSets(SECONDARY_PERCENTS_2, SECONDARY_REPS_1, SECONDARY_ISAMRAP), squatTm)
        ));

        return workouts;
    }

    public static ArrayList<Workout> create5day(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Workout> workouts = new ArrayList<>();

        workouts.add(new Workout(
                new Exercise("Bench", createRepSets(PRIMARY_PERCENTS_2, PRIMARY_REPS_4, PRIMARY_ISAMRAP_2), benchTm),
                new Exercise("OHP", createRepSets(SECONDARY_PERCENTS_1, SECONDARY_REPS_2, SECONDARY_ISAMRAP), ohpTm)
        ));
        workouts.add(new Workout(
                new Exercise("Squat", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_3, PRIMARY_ISAMRAP_1), squatTm),
                new Exercise("Sumo Deadlift", createRepSets(SECONDARY_PERCENTS_1, SECONDARY_REPS_1, SECONDARY_ISAMRAP), dlTm)
        ));
        workouts.add(new Workout(
                new Exercise("OHP", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_3, PRIMARY_ISAMRAP_1), ohpTm),
                new Exercise("Incline Bench", createRepSets(SECONDARY_PERCENTS_3, SECONDARY_REPS_2, SECONDARY_ISAMRAP), benchTm)
        ));
        workouts.add(new Workout(
                new Exercise("Deadlift", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_1, PRIMARY_ISAMRAP_1), dlTm),
                new Exercise("Front Squat", createRepSets(SECONDARY_PERCENTS_2, SECONDARY_REPS_1, SECONDARY_ISAMRAP), squatTm)
        ));
        workouts.add(new Workout(
                new Exercise("Bench", createRepSets(PRIMARY_PERCENTS_1, PRIMARY_REPS_2, PRIMARY_ISAMRAP_1), benchTm),
                new Exercise("CG Bench", createRepSets(SECONDARY_PERCENTS_3, PRIMARY_REPS_2, SECONDARY_ISAMRAP), benchTm)
        ));

        return workouts;
    }

    public static ArrayList<Workout> create6daySquat(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Workout> workouts = create5day(dlTm, squatTm, benchTm, ohpTm);

        workouts.add(new Workout(
                new Exercise("Squat", createRepSets(PRIMARY_PERCENTS_FOCUS, PRIMARY_REPS_FOCUS, SECONDARY_ISAMRAP), squatTm),
                new Exercise("Sumo Deadlift", createRepSets(SECONDARY_PERCENTS_FOCUS, SECONDARY_REPS_FOCUS, SECONDARY_ISAMRAP_FOCUS), dlTm)
        ));

        return workouts;
    }


    public static ArrayList<Workout> create6dayDeadlift(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Workout> workouts = create5day(dlTm, squatTm, benchTm, ohpTm);
        Collections.swap(workouts, 1, 3);

        workouts.add(new Workout(
                new Exercise("Deadlift", createRepSets(PRIMARY_PERCENTS_FOCUS, PRIMARY_REPS_FOCUS, SECONDARY_ISAMRAP), dlTm),
                new Exercise("Front Squat", createRepSets(SECONDARY_PERCENTS_FOCUS, SECONDARY_REPS_FOCUS, SECONDARY_ISAMRAP_FOCUS), squatTm)
        ));

        return workouts;
    }

    public static ArrayList<RepSet> createRepSets(float[] percentages, int[] reps, boolean[] isAmrap) {
        ArrayList<RepSet> repSets = new ArrayList<>();
        for (int i=0; i<percentages.length; i++) {
            repSets.add(new RepSet(percentages[i], reps[i], isAmrap[i]));
        }
        return repSets;
    }

}
