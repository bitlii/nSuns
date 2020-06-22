package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.WorkoutAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;

public class WorkoutActivity extends AppCompatActivity {
    public DatabaseHandler db;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        Exercise primaryExercise = intent.getParcelableExtra("primaryExercise");
        Exercise secondaryExercise = intent.getParcelableExtra("secondaryExercise");

        TextView header1 = findViewById(R.id.exercise1Header);
        header1.setText(primaryExercise.getName());

        // Primary exercise recycler
        recyclerView = findViewById(R.id.setRecycler1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WorkoutAdapter(primaryExercise);
        recyclerView.setAdapter(adapter);

        TextView header2 = findViewById(R.id.exercise2Header);
        header2.setText(secondaryExercise.getName());

        // Secondary exercise recycler
        recyclerView2 = findViewById(R.id.setRecycler2);
        adapter2 = new WorkoutAdapter(secondaryExercise);
        recyclerView2.setAdapter(adapter2);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_done:
                finish();
        }
        return true;
    }

}