package com.bitco.nsuns.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;
import com.bitco.nsuns.items.Workout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// To access the database itself:
// Run 'adb forward tcp:8080 tcp:8080' in terminal while debugging, then localhost:8080.
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database
    private static final String DATABASE_NAME = "appDatabase";
    private static final int DATABASE_VERSION = 7;

    // Workout Table
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String KEY_ID = "id";
    private static final String KEY_PRIMARYEXERCISE = "primaryExercise";
    private static final String KEY_SECONDARYEXERCISE = "secondaryExercise";
    // isPrimary: 1 = true, 0 = false;
    private static final String KEY_ISPRIMARY = "isPrimary";

    private static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE " + TABLE_WORKOUTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRIMARYEXERCISE + " TEXT,"
            + KEY_SECONDARYEXERCISE + " TEXT"
            + ")";

    // Exercises Table
    private static final String TABLE_EXERCISES = "exercises";
    // key_id is also used.
    private static final String KEY_NAME = "name";
    private static final String KEY_TM = "tm";
    private static final String KEY_SETS = "sets";
    private static final String CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_ISPRIMARY + " INTEGER,"
            + KEY_TM + " FLOAT,"
            + KEY_SETS + " TEXT"
            + ")";

    private static final String TABLE_SECONDARYEXERCISES = "secondaryExercises";
    private static final String CREATE_TABLE_SECONDARYEXERCISES = "CREATE TABLE " + TABLE_SECONDARYEXERCISES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PRIMARYEXERCISE + " TEXT"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUTS);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_SECONDARYEXERCISES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECONDARYEXERCISES);

        onCreate(db);
    }

    public void insertPrimaryExercise(Exercise e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName());
        values.put(KEY_TM, e.getTm());
        values.put(KEY_ISPRIMARY, 1);

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        values.put(KEY_SETS, object);

        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    public void insertSecondaryExercise(Exercise e, String primaryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName());
        values.put(KEY_TM, e.getTm());
        values.put(KEY_ISPRIMARY, 0);

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        values.put(KEY_SETS, object);
        db.insert(TABLE_EXERCISES, null, values);

        ContentValues secondaryValues = new ContentValues();
        secondaryValues.put(KEY_NAME, e.getName());
        secondaryValues.put(KEY_PRIMARYEXERCISE, primaryName);
        db.insert(TABLE_SECONDARYEXERCISES, null, secondaryValues);

        db.close();
    }

    public void insertWorkout(Workout wo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRIMARYEXERCISE, wo.getPrimaryExercise().getName());
        values.put(KEY_SECONDARYEXERCISE, wo.getSecondaryExercise().getName());

        db.insert(TABLE_WORKOUTS, null, values);
        db.close();
    }

    public ArrayList<Workout> getAllWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Workout> workouts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_WORKOUTS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        getExercise(cursor.getString(1)),
                        getExercise(cursor.getString(2)));

                workouts.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return workouts;
    }

    public Exercise getExercise(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Exercise exercise = null;

        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE name = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                String exerciseName = cursor.getString(1);
                Float trainingMax = cursor.getFloat(3);
                String list = cursor.getString(4);

                Gson gson = new Gson();
                Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
                ArrayList<RepSet> setList = gson.fromJson(list, setListType);

                exercise = new Exercise(exerciseName, setList, trainingMax);
            }
        }

        cursor.close();
        db.close();
        return exercise;
    }

    public ArrayList<Exercise> getPrimaryExerciseList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE isPrimary = '" + 1 + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String exerciseName = cursor.getString(1);
                float trainingMax = cursor.getFloat(3);
                String list = cursor.getString(4);

                Gson gson = new Gson();
                Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
                ArrayList<RepSet> setList = gson.fromJson(list, setListType);

                Exercise exercise = new Exercise(exerciseName, setList, trainingMax);

                exercises.add(exercise);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exercises;
    }

    public void updateExerciseTrainingMax(String name, float newTm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tm", newTm);

        db.update(TABLE_EXERCISES, cv, "name = '" + name + "'", null);
        db.close();

    }

    public void updateRepSets(Exercise e) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);
        cv.put("sets", object);

        db.update(TABLE_EXERCISES, cv, "name = '" + e.getName() + "'", null);
        db.close();
    }

}
