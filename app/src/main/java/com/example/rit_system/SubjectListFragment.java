package com.example.rit_system;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rit_system.adapters.SubjectRecyclerViewAdapter;
import com.example.rit_system.bottom_sheets.SubjectInfoFragment;
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.entities.Subject;
import com.example.rit_system.forms.SubjectFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubjectListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectListFragment newInstance(String param1, String param2) {
        SubjectListFragment fragment = new SubjectListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, homeFragment).commit();
        });

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add) {
                SubjectFormFragment subjectFormFragment = SubjectFormFragment.newInstance(null);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectFormFragment).commit();
                return true;
            }
            return false;
        });

        SubjectDAO subjectDAO = new SubjectDAO(getContext());
        ArrayList<Subject> subjects = subjectDAO.getList();

        SubjectRecyclerViewAdapter subjectRecyclerViewAdapter = new SubjectRecyclerViewAdapter(subjects, position -> {
            SubjectInfoFragment subjectInfoFragment = SubjectInfoFragment.newInstance(subjects.get(position), position);
            subjectInfoFragment.show(getChildFragmentManager(), SubjectInfoFragment.TAG);
        });

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getItemIndex().observe(getViewLifecycleOwner(), position -> {
            subjectRecyclerViewAdapter.update(getContext());
            subjectRecyclerViewAdapter.notifyItemRemoved(position);
        });

        RecyclerView recyclerView = view.findViewById(R.id.SubjectListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subjectRecyclerViewAdapter);

    }
}