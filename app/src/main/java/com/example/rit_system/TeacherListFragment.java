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

import com.example.rit_system.adapters.TeacherRecyclerViewAdapter;
import com.example.rit_system.bottom_sheets.TeacherInfoFragment;
import com.example.rit_system.dao.TeacherDAO;
import com.example.rit_system.entities.Teacher;
import com.example.rit_system.forms.TeacherFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherListFragment newInstance(String param1, String param2) {
        TeacherListFragment fragment = new TeacherListFragment();
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
        return inflater.inflate(R.layout.fragment_teacher_list, container, false);
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
                TeacherFormFragment subjectFormFragment = TeacherFormFragment.newInstance(null);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectFormFragment).commit();
                return true;
            }
            return false;
        });

        TeacherDAO teacherDAO = new TeacherDAO(getContext());
        ArrayList<Teacher> teachers = teacherDAO.getList();

        TeacherRecyclerViewAdapter teacherRecyclerViewAdapter = new TeacherRecyclerViewAdapter(teachers, position -> {
            TeacherInfoFragment teacherInfoFragment = TeacherInfoFragment.newInstance(teachers.get(position), position);
            teacherInfoFragment.show(getChildFragmentManager(), TeacherInfoFragment.TAG);
        });

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getItemIndex().observe(getViewLifecycleOwner(), position -> {
            teacherRecyclerViewAdapter.update(getContext());
            teacherRecyclerViewAdapter.notifyItemRemoved(position);
        });

        RecyclerView recyclerView = view.findViewById(R.id.TeacherListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(teacherRecyclerViewAdapter);

    }
}