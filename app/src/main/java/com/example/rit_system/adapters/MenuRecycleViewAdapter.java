package com.example.rit_system.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rit_system.R;

import org.w3c.dom.Text;

public class MenuRecycleViewAdapter extends RecyclerView.Adapter<MenuRecycleViewAdapter.ViewHolder> {

    private final MenuDataset[] localDataSet;

    public static class MenuDataset {
        private String label;
        private int iconId;

        public MenuDataset() {}

        public MenuDataset(String label, int iconId) {
            this.iconId = iconId;
            this.label = label;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getIconId() {
            return iconId;
        }

        public String getLabel() {
            return label;
        }
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

    public MenuRecycleViewAdapter(MenuDataset[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.d("AdapterDebug", "Binding view at position: " + position);

        viewHolder.getLabel().setText(localDataSet[position].getLabel());
        viewHolder.getIcon().setImageResource(localDataSet[position].getIconId());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
