package com.example.rit_system.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rit_system.R;
import com.example.rit_system.dao.CoordinationActivityDAO;
import com.example.rit_system.entities.CoordinationActivity;

import java.util.ArrayList;

public class CoordinationActivityRecyclerViewAdapter extends RecyclerView.Adapter<CoordinationActivityRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private ArrayList<CoordinationActivity> coordinationActivities;
    private OnItemClickListener listener;

    public void update(Context context) {
        CoordinationActivityDAO coordinationActivityDAO = new CoordinationActivityDAO(context);
        coordinationActivities = coordinationActivityDAO.getCoordinationActivities();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView label;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            label = view.findViewById(R.id.label);
            icon = view.findViewById((R.id.icon));

        }

        public TextView getLabel() {
            return label;
        }

        public ImageView getIcon() {
            return icon;
        }
    }

    public CoordinationActivityRecyclerViewAdapter(ArrayList<CoordinationActivity> coordinationActivities, OnItemClickListener listener) {
        this.coordinationActivities = coordinationActivities;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.d("AdapterDebug", "Binding view at position: " + position);

        viewHolder.getLabel().setText(coordinationActivities.get(position).getActivityTitle());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return coordinationActivities.size();
    }
}
