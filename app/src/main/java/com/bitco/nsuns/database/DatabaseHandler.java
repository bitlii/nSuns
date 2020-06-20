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
    private static final int DATABASE_VERSION = 2;

    // Workout Table
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String KEY_ID = "id";
    private static final String KEY_E1 = "e1";
    private static final String KEY_E2 = "e2";

    private static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE " + TABLE_WORKOUTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_E1 + " TEXT,"
            + KEY_E2 + " TEXT"
            + ")";

    // Exercises Table
    private static final String TABLE_PRIMARYEXERCISES = "primaryExercises";
    // id
    private static final String KEY_NAME = "name";
    private static final String KEY_TM = "tm";
    private static final String KEY_SETS = "sets";

    private static final String CREATE_TABLE_PRIMARYEXERCISES = "CREATE TABLE " + TABLE_PRIMARYEXERCISES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_TM + " FLOAT,"
            + KEY_SETS + " TEXT"
            + ")";

    private static final String TABLE_SECONDARYEXERCISES = "secondaryExercises";
    private static final String CREATE_TABLE_SECONDARYEXERCISES = "CREATE TABLE " + TABLE_SECONDARYEXERCISES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_TM + " FLOAT,"
            + KEY_SETS + " TEXT"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUTS);
        db.execSQL(CREATE_TABLE_PRIMARYEXERCISES);
        db.execSQL(CREATE_TABLE_SECONDARYEXERCISES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIMARYEXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECONDARYEXERCISES);

        onCreate(db);
    }

    public void insertPrimaryExercise(Exercise e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName());
        values.put(KEY_TM, e.getTm());

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        values.put(KEY_SETS, object);

        db.insert(TABLE_PRIMARYEXERCISES, null, values);
        db.close();
    }

    public void insertSecondaryExercise(Exercise e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName());
        values.put(KEY_TM, e.getTm());

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        values.put(KEY_SETS, object);

        db.insert(TABLE_SECONDARYEXERCISES, null, values);
        db.close();
    }

    public void insertWorkout(Workout wo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_E1, wo.getE1().getName());
        values.put(KEY_E2, wo.getE2().getName());

        db.insert(TABLE_WORKOUTS, null, values);
        db.close();
    }

    public ArrayList<Exercise> getPrimaryExercises() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PRIMARYEXERCISES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String exerciseName = cursor.getString(1);
                float trainingMax = cursor.getFloat(2);
                String list = cursor.getString(3);

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

    public ArrayList<Exercise> getSecondaryExercises() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SECONDARYEXERCISES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String exerciseName = cursor.getString(1);
                float trainingMax = cursor.getFloat(2);
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
}
