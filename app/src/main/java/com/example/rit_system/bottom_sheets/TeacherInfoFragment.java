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
import com.example.rit_system.dao.TeacherDAO;
import com.example.rit_system.entities.Teacher;
import com.example.rit_system.forms.TeacherFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherInfoFragment extends BottomSheetDialogFragment {

    public static final String TAG = "TeacherInfo";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "teacher";
    private static final String ARG_PARAM2 = "position";

    // TODO: Rename and change types of parameters
    private Teacher teacher;
    private int position;

    public TeacherInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param teacher Parameter 1.
     * @param position Parameter 2.
     * @return A new instance of fragment TeacherInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherInfoFragment newInstance(Teacher teacher, int position) {
        TeacherInfoFragment fragment = new TeacherInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, teacher);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teacher = (Teacher) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (teacher != null) {
            Log.d("Teacher", teacher.toString());

            ((TextView) (view.findViewById(R.id.FullName))).setText(teacher.getName());
            ((TextView) (view.findViewById(R.id.BirthDay))).setText(teacher.getBirthDay().toString());
            ((TextView) (view.findViewById(R.id.IdentificationNumber))).setText(teacher.getIndentificatorNumber());
            ((TextView) (view.findViewById(R.id.Email))).setText(teacher.getEmail());
            ((TextView) (view.findViewById(R.id.Telefone))).setText(teacher.getPhone());
            ((TextView) (view.findViewById(R.id.TrainingArea))).setText(teacher.getTrainingArea());
            ((TextView) (view.findViewById(R.id.YearOfExperience))).setText(String.valueOf(teacher.getYearsOfExperience()));
        }

        view.findViewById(R.id.EditButton).setOnClickListener(v -> {
            TeacherFormFragment teacherFormFragment = TeacherFormFragment.newInstance(teacher);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, teacherFormFragment).commit();
        });

        view.findViewById(R.id.DeleteButton).setOnClickListener(v -> {
            TeacherDAO teacherDAO = new TeacherDAO(getContext());
            teacherDAO.delete(String.valueOf(teacher.getId()));
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(position);
            dismiss();
        });
    }
}