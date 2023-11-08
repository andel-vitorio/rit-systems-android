package com.example.rit_system.bottom_sheets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rit_system.R;
import com.example.rit_system.entities.CoordinationActivity;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoordinationActivityInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoordinationActivityInfoFragment extends BottomSheetDialogFragment {

    public static final String TAG = "CoordinationActivityInfo";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "coordinationActivity";
    private static final String ARG_PARAM2 = "position";

    // TODO: Rename and change types of parameters
    private CoordinationActivity coordinationActivity;
    private int position;

    public CoordinationActivityInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param coordinationActivity Parameter 1.
     * @param position Parameter 2.
     * @return A new instance of fragment CoordinationActivityInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoordinationActivityInfoFragment newInstance(CoordinationActivity coordinationActivity, int position) {
        CoordinationActivityInfoFragment fragment = new CoordinationActivityInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, coordinationActivity);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coordinationActivity = (CoordinationActivity) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coordination_activity_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (coordinationActivity != null) {
            Log.d("CoordinationActivity", coordinationActivity.toString());

            ((TextView) view.findViewById(R.id.ActivityTitle)).setText(coordinationActivity.getActivityTitle());
            ((TextView) view.findViewById(R.id.NameOfPersonResponsible)).setText(coordinationActivity.getNameOfPersonResponsible());
            ((TextView) view.findViewById(R.id.StartDate)).setText(coordinationActivity.getStartDate().toString());
            ((TextView) view.findViewById(R.id.EndDate)).setText(coordinationActivity.getEndDate().toString());
            ((TextView) view.findViewById(R.id.Priority)).setText(coordinationActivity.getPriority());
            ((TextView) view.findViewById(R.id.Status)).setText(coordinationActivity.getStatus());
            ((TextView) view.findViewById(R.id.Description)).setText(coordinationActivity.getDescription());
        }

        view.findViewById(R.id.EditButton).setOnClickListener(v -> {
          /*  CoordinationActivityFormFragment coordinationActivityFormFragment = CoordinationActivityFormFragment.newInstance(coordinationActivity);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, coordinationActivityFormFragment).commit();*/
        });

        view.findViewById(R.id.DeleteButton).setOnClickListener(v -> {
            /*
            CoordinationActivityDAO coordinationActivityDAO = new CoordinationActivityDAO(getContext());
            coordinationActivityDAO.delete(String.valueOf(coordinationActivity.getId()));
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(position);
            dismiss();*/
        });
    }
}