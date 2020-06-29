package com.bitco.nsuns.items;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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

    private static final boolean[] PRIMARY_ISAMRAP_1 = {
            false, false, true, false, false, false, false, false, true
    };

    private static final boolean[] PRIMARY_ISAMRAP_2 = {
            false, false, false, false, false, false, false, false, true
    };

    private static final float[] SECONDARY_PERCENTS = {
            0.5f, 0.6f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f
    };

    private static final int[] SECONDARY_REPS_1 = {
            5, 5, 3, 5, 7, 4, 6, 8
    };

    private static final int[] SECONDARY_REPS_2 = {
            6, 5, 3, 5, 7, 4, 6, 8
    };

    private static final boolean[] SECONDARY_ISAMRAP = {
            false, false, false, false, false, false, false, false, false
    };

    public static final HashMap<String, Pair<int[], boolean[]>> exerciseInfo = new HashMap<>();

    static {
        exerciseInfo.put("Deadlifts", new Pair(PRIMARY_REPS_1, PRIMARY_ISAMRAP_1));
        exerciseInfo.put("Squats", new Pair(PRIMARY_REPS_1, PRIMARY_ISAMRAP_1));
        exerciseInfo.put("Bench1", new Pair(PRIMARY_REPS_1, PRIMARY_ISAMRAP_1));
        exerciseInfo.put("Bench2", new Pair(PRIMARY_REPS_4, PRIMARY_ISAMRAP_2));
        exerciseInfo.put("OHP1", new Pair(SECONDARY_REPS_2, SECONDARY_ISAMRAP));
        exerciseInfo.put("Sumo Deadlifts", new Pair(SECONDARY_REPS_1, SECONDARY_ISAMRAP));
        exerciseInfo.put("Front Squats", new Pair(SECONDARY_REPS_1, SECONDARY_ISAMRAP));
        exerciseInfo.put("CG Bench", new Pair(SECONDARY_REPS_2, SECONDARY_ISAMRAP));
    }

    public static ArrayList<Workout> create4dayWorkouts(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Workout> workouts = new ArrayList<>();
        ArrayList<Exercise> primaryExercises = create4DayPrimaryExercises(dlTm, squatTm, benchTm, ohpTm);
        ArrayList<Exercise> secondaryExercises = create4DaySecondaryExercises(dlTm, squatTm, benchTm, ohpTm);

        workouts.add(new Workout(primaryExercises.get(0), secondaryExercises.get(1)));
        workouts.add(new Workout(secondaryExercises.get(0), primaryExercises.get(3)));
        workouts.add(new Workout(primaryExercises.get(1), secondaryExercises.get(3)));
        workouts.add(new Workout(primaryExercises.get(2), secondaryExercises.get(2)));

        return workouts;
    }

    public static ArrayList<Exercise> create4DayPrimaryExercises(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Deadlifts", createRepSets(PRIMARY_PERCENTS_1, exerciseInfo.get("Deadlifts").first, exerciseInfo.get("Deadlifts").second), dlTm));
        exercises.add(new Exercise("Squats", createRepSets(PRIMARY_PERCENTS_1, exerciseInfo.get("Squats").first, exerciseInfo.get("Squats").second), squatTm));
        exercises.add(new Exercise("Bench 1", createRepSets(PRIMARY_PERCENTS_1, exerciseInfo.get("Bench1").first, exerciseInfo.get("Bench2").second), benchTm));
        exercises.add(new Exercise("OHP", createRepSets(SECONDARY_PERCENTS, exerciseInfo.get("OHP1").first, exerciseInfo.get("OHP1").second), ohpTm));

        return exercises;
    }

    public static ArrayList<Exercise> create4DaySecondaryExercises(float dlTm, float squatTm, float benchTm, float ohpTm) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Bench 2", createRepSets(PRIMARY_PERCENTS_2, exerciseInfo.get("Bench2").first, exerciseInfo.get("Bench2").second), benchTm));
        exercises.add(new Exercise("Front Squats", createRepSets(SECONDARY_PERCENTS, exerciseInfo.get("Front Squats").first, exerciseInfo.get("Front Squats").second), squatTm));
        exercises.add(new Exercise("CG Bench", createRepSets(SECONDARY_PERCENTS, exerciseInfo.get("CG Bench").first, exerciseInfo.get("CG Bench").second), benchTm));
        exercises.add(new Exercise("Sumo Deadlifts", createRepSets(SECONDARY_PERCENTS, exerciseInfo.get("Sumo Deadlifts").first, exerciseInfo.get("Sumo Deadlifts").second), dlTm));

        return exercises;
    }

    public static ArrayList<RepSet> createRepSets(float[] percentages, int[] reps, boolean[] isAmrap) {
        ArrayList<RepSet> repSets = new ArrayList<>();
        for (int i=0; i<percentages.length; i++) {
            repSets.add(new RepSet(percentages[i], reps[i], isAmrap[i]));
        }
        return repSets;
    }

}
