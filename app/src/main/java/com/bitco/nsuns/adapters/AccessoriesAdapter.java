package com.bitco.nsuns.adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.fragments.NewAccessoryFragment;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.AccessoriesViewHolder> {

    private Workout workout;
    private ArrayList<Exercise> accessories;
    private View view;

    public static class AccessoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView setCount;
        private MaterialCardView card;
        private RecyclerView recycler;
        private ImageView overflowButton;

        public AccessoriesViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.accessory_name);
            setCount = v.findViewById(R.id.text_set_count);
            card = v.findViewById(R.id.card);
            recycler = v.findViewById(R.id.recycler);
            overflowButton = v.findViewById(R.id.overflow_menu);
        }
    }

    public AccessoriesAdapter(Workout wo) {
        workout = wo;
        accessories = wo.getAccessories();
    }

    @NonNull
    @Override
    public AccessoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accessory, parent, false);
        view = v;
        AccessoriesViewHolder vh = new AccessoriesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesViewHolder holder, int position) {
        Exercise accessory = accessories.get(position);
        holder.setCount.setText(accessory.getSets().size() + " sets");

        InnerAccessoriesAdapter adapter = new InnerAccessoriesAdapter(accessory);
        holder.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        holder.title.setText(accessory.getName());
        holder.card.setOnClickListener(view -> {
            if (holder.recycler.getVisibility() == View.GONE) {
                holder.recycler.setVisibility(View.VISIBLE);
            }
            else {
                holder.recycler.setVisibility(View.GONE);
            }
        });

        holder.recycler.setAdapter(adapter);

        holder.overflowButton.setOnClickListener(view1 -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.overflowButton);
            popupMenu.getMenuInflater().inflate(R.menu.menu_accessory_details, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.edit:
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new NewAccessoryFragment(accessory)).addToBackStack("Main").commit();
                            break;
                        case R.id.delete:
                            accessories.remove(accessory);
                            workout.getAccessories().remove(accessory);
                            DatabaseHandler db = new DatabaseHandler(view.getContext());
                            db.updateWorkoutAccessories(workout);
                            db.close();
                            notifyItemRemoved(position);
                            break;
                    }
                    return true;
                }
            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount() {
        return accessories.size();
    }


}
