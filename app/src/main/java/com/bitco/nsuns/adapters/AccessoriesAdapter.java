package com.bitco.nsuns.adapters;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.AccessoriesViewHolder>{

    private Workout workout;
    private ArrayList<Exercise> accessories;
    private Exercise selectedAccessory;
    private View view;
    private ActionMode actionMode = null;

    public static class AccessoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView setCount;
        private LinearLayout layout;
        private MaterialCardView card;
        private RecyclerView recycler;

        public AccessoriesViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.accessory_name);
            setCount = v.findViewById(R.id.text_set_count);
            layout = v.findViewById(R.id.layout);
            card = v.findViewById(R.id.card);
            recycler = v.findViewById(R.id.recycler);
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
        holder.card.setOnLongClickListener(view1 -> {
            if (actionMode != null) {
                return false;
            }
            actionMode = view.startActionMode(actionModeCallBack);
            view.setSelected(true);
            selectedAccessory = accessory;
            return true;
        });

        holder.recycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return accessories.size();
    }

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_context_exercise, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.delete:
                    accessories.remove(selectedAccessory);
                    workout.getAccessories().remove(selectedAccessory);

                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                    db.updateWorkoutAccessories(workout);
                    db.close();

                    actionMode.finish();
                    notifyDataSetChanged();
                    return true;
                default:
                    return false;

            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }

    };


}
