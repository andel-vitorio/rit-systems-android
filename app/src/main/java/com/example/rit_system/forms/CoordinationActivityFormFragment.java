package com.example.rit_system.forms;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rit_system.CoordinationActivityListFragment;
import com.example.rit_system.R;
import com.example.rit_system.entities.CoordinationActivity;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.rit_system.entities.CoordinationActivityFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoordinationActivityFormFragment extends Fragment {

    public static final String TAG = "CoordinationActivityForm";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "coordinatioActivity";
    private static final String ARG_PARAM2 = "recycler";

    // TODO: Rename and change types of parameters
    private CoordinationActivity coordinatioActivity;
    private RecyclerView recyclerView;

    public CoordinationActivityFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment com.example.rit_system.entities.CoordinationActivityFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoordinationActivityFormFragment newInstance(CoordinationActivity coordinatioActivity) {
        CoordinationActivityFormFragment fragment = new CoordinationActivityFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, coordinatioActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coordinatioActivity = (CoordinationActivity) getArguments().getSerializable(ARG_PARAM1);
            recyclerView = (RecyclerView) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coordination_activity_form_layout, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            CoordinationActivityListFragment coordinatioActivityListFragment = new CoordinationActivityListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, coordinatioActivityListFragment).commit();
        });

        if (coordinatioActivity != null) {
            ((TextInputEditText) view.findViewById(R.id.InputActivityName)).setText(coordinatioActivity.getActivityTitle());
            ((TextInputEditText) view.findViewById(R.id.InputNameOfPersonResponsible)).setText(coordinatioActivity.getNameOfPersonResponsible());
            ((TextInputEditText) view.findViewById(R.id.InputStartDate)).setText(coordinatioActivity.getStartDate().toString());
            ((TextInputEditText) view.findViewById(R.id.InputEndDate)).setText(coordinatioActivity.getEndDate().toString());
            ((TextInputEditText) view.findViewById(R.id.InputPriority)).setText(coordinatioActivity.getPriority());
            ((TextInputEditText) view.findViewById(R.id.InputStatus)).setText(coordinatioActivity.getStatus());
            ((TextInputEditText) view.findViewById(R.id.InputDescription)).setText(coordinatioActivity.getDescription());
        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            CoordinationActivityListFragment coordinatioActivityListFragment = new CoordinationActivityListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, coordinatioActivityListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {

            boolean isNew = (coordinatioActivity == null);

            if (isNew) coordinatioActivity = new CoordinationActivity();

            coordinatioActivity.setActivityTitle(((TextInputEditText) view.findViewById(R.id.InputActivityName)).getText().toString());
            coordinatioActivity.setNameOfPersonResponsible(((TextInputEditText) view.findViewById(R.id.InputNameOfPersonResponsible)).getText().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                coordinatioActivity.setStartDate(LocalDate.parse(((TextInputEditText) view.findViewById(R.id.InputStartDate)).getText().toString()));
            coordinatioActivity.setEndDate(LocalDate.parse(((TextInputEditText) view.findViewById(R.id.InputEndDate)).getText().toString()));
            }
            coordinatioActivity.setPriority(((TextInputEditText) view.findViewById(R.id.InputPriority)).getText().toString());
            coordinatioActivity.setStatus(((TextInputEditText) view.findViewById(R.id.InputStatus)).getText().toString());
            coordinatioActivity.setDescription(((TextInputEditText) view.findViewById(R.id.InputDescription)).getText().toString());


           /* CoordinationActivityDAO coordinatioActivityDAO = new CoordinationActivityDAO(getContext());
            if(isNew) coordinatioActivityDAO.add(coordinatioActivity);
            else coordinatioActivityDAO.update(coordinatioActivity);*/

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            CoordinationActivityListFragment coordinationActivityListFragment = new CoordinationActivityListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, coordinationActivityListFragment).commit();
        });
    }
}