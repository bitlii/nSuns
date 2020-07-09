package com.bitco.nsuns.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;
import com.bitco.nsuns.items.Workout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// To access the database itself:
// Run 'adb forward tcp:8080 tcp:8080' in terminal while debugging, then localhost:8080.
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database
    private static final String DATABASE_NAME = "appDatabase";
    private static final int DATABASE_VERSION = 9;

    // Commonly used keys.
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TM = "tm";

    /*
    Table: main_lifts.
    Columns: int 'id', text 'name', float 'tm'.
     */
    private static final String TABLE_MAINLIFTS = "main_lifts";
    private static final String CREATE_TABLE_MAINLIFTS = "CREATE TABLE " + TABLE_MAINLIFTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT UNIQUE,"
            + KEY_TM + " FLOAT"
            + ")";

    /*
    Table: workouts.
    Columns: int 'id', int 'ex_one_id', int 'ex_two_id', text 'accessories'.
     */
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String KEY_EX_ONE_ID = "ex_one_id";
    private static final String KEY_EX_TWO_ID = "ex_two_id";
    private static final String KEY_ACCESSORIES = "accessories";
    private static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE " + TABLE_WORKOUTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_EX_ONE_ID + " INTEGER,"
            + KEY_EX_TWO_ID + " INTEGER,"
            + KEY_ACCESSORIES + " TEXT"
            + ")";

    /*
    Table: exercises.
    Columns: int 'id', text 'name', text 'main_lift', text 'sets'.
     */
    private static final String TABLE_EXERCISES = "exercises";
    private static final String KEY_SETS = "sets";
    private static final String KEY_MAINLIFT = "main_lift";
    private static final String CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISES
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_MAINLIFT + " TEXT,"
            + KEY_SETS + " TEXT"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUTS);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_MAINLIFTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINLIFTS);

        onCreate(db);
    }

    public void changeTemplates() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);

        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_WORKOUTS);
    }

    public void insertExercise(Exercise e, String mainLift) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, e.getName());
        values.put(KEY_MAINLIFT, mainLift);

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        values.put(KEY_SETS, object);

        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    public Exercise getExerciseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Exercise exercise = null;

        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE id = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            int exerciseId = cursor.getInt(0);
            String exerciseName = cursor.getString(1);
            String mainLift = cursor.getString(2);
            String list = cursor.getString(3);

            Gson gson = new Gson();
            Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
            ArrayList<RepSet> setList = gson.fromJson(list, setListType);

            exercise = new Exercise(exerciseId, exerciseName, setList, getMainLiftTm(mainLift));
        }

        cursor.close();
        db.close();
        return exercise;
    }

    public int getExerciseId(Exercise e) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1;

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<RepSet>>(){}.getType();
        String object = gson.toJson(e.getSets(), setListType);

        String query = "SELECT id FROM " + TABLE_EXERCISES + " WHERE name = '" + e.getName() + "' AND sets = '" + object + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        return id;
    }

    public void insertMainLift(String name, float tm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_TM, tm);

        db.insert(TABLE_MAINLIFTS, null, values);
        db.close();
    }

    public ArrayList<Pair<String, Float>> getAllMainLifts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Pair<String, Float>> lifts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_MAINLIFTS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                lifts.add(Pair.create(cursor.getString(1),cursor.getFloat(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lifts;
    }

    public float getMainLiftTm(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        float tm = 0f;

        String query = "SELECT tm FROM " + TABLE_MAINLIFTS + " WHERE name = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
           tm = cursor.getFloat(0);
        }
        cursor.close();
        db.close();
        return tm;
    }

    public void insertWorkout(Workout wo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wo.getName());
        values.put(KEY_EX_ONE_ID, wo.getPrimaryExercise().getId());
        values.put(KEY_EX_TWO_ID, wo.getSecondaryExercise().getId());

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        String object = gson.toJson(wo.getAccessories(), setListType);
        values.put("accessories", object);

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
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                String list = cursor.getString(4);
                Gson gson = new Gson();
                Type setListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
                ArrayList<Exercise> setList = gson.fromJson(list, setListType);

                Workout workout = new Workout(
                        id,
                        name,
                        getExerciseById(cursor.getInt(2)),
                        getExerciseById(cursor.getInt(3)),
                        setList);

                workouts.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return workouts;
    }

    public void updateWorkoutAccessories(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Gson gson = new Gson();
        Type setListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        String object = gson.toJson(workout.getAccessories(), setListType);
        cv.put("accessories", object);

        int id = workout.getId();

        db.update(TABLE_WORKOUTS, cv, "id = '" + id + "'", null);
        db.close();
    }

    public void updateExerciseTrainingMax(String name, float newTm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tm", newTm);

        db.update(TABLE_MAINLIFTS, cv, "name = '" + name + "'", null);
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

    public void getExerciseLastRowId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1;

        String query = "SELECT last_insert_rowid()";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
    }

}
