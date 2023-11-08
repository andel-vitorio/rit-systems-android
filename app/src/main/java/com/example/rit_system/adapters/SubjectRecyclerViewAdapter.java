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
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.entities.Subject;

import java.util.ArrayList;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private ArrayList<Subject> subjects = new ArrayList<>();
    private OnItemClickListener listener;

    public void update(Context context) {
        SubjectDAO subjectDAO = new SubjectDAO(context);
        subjects = subjectDAO.getList();
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

    public SubjectRecyclerViewAdapter(ArrayList<Subject> subjects, OnItemClickListener listener) {
        this.subjects = subjects;
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

        viewHolder.getLabel().setText(subjects.get(position).getName());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
