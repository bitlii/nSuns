package com.bitco.nsuns.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class WorkoutFragment extends Fragment {

    Workout workout;
    Exercise primaryExercise;
    Exercise secondaryExercise;
    View v;
    ViewPager2 pager;
    WorkoutPagerAdapter workoutPagerAdapter;

    private TabLayout tabLayout;

    public WorkoutFragment(Workout workout) {
        super();
        this.workout = workout;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_content, container, false);

        primaryExercise = workout.getPrimaryExercise();
        secondaryExercise = workout.getSecondaryExercise();

        tabLayout = view.findViewById(R.id.tabLayout);

        pager = view.findViewById(R.id.pager);
        workoutPagerAdapter = new WorkoutPagerAdapter(this);
        pager.setAdapter(workoutPagerAdapter);

        new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(primaryExercise.getName());
                    break;
                case 1:
                    tab.setText(secondaryExercise.getName());
                    break;
                case 2:
                    tab.setText("Accessories");
                    break;
            }

        }).attach();

        return view;
    }

    protected class WorkoutPagerAdapter extends FragmentStateAdapter {

        public WorkoutPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public WorkoutPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position) {
                case 0:
                    MainWorkoutFragment frag = new MainWorkoutFragment(primaryExercise);
                    return frag;
                case 1:
                    return new MainWorkoutFragment(secondaryExercise);
                default:
                    return new AccessoriesFragment(workout);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}