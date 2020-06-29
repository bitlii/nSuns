package com.bitco.nsuns.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.AccessoriesFragment;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.AccessoriesViewHolder>{

    private ArrayList<Exercise> accessories;
    private View view;

    public static class AccessoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private LinearLayout layout;
        private RecyclerView recycler;

        public AccessoriesViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.accessoryName);
            layout = v.findViewById(R.id.parent);
            recycler = v.findViewById(R.id.recycler);
        }
    }

    public AccessoriesAdapter(ArrayList<Exercise> a) {
        accessories = a;
    }

    @NonNull
    @Override
    public AccessoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_accessory, parent, false);
        view = v;
        AccessoriesViewHolder vh = new AccessoriesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesViewHolder holder, int position) {
        Exercise accessory = accessories.get(position);

        InnerAccessoriesAdapter adapter = new InnerAccessoriesAdapter(accessory);
        holder.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        holder.title.setText(accessory.getName());
        holder.layout.setOnClickListener(view -> {
            if (holder.recycler.getVisibility() == View.GONE) {
                holder.recycler.setVisibility(View.VISIBLE);
            }
            else {
                holder.recycler.setVisibility(View.GONE);
            }
        });

        holder.recycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return accessories.size();
    }


}
