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
import com.example.rit_system.dao.GraduateStudentDAO;
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.entities.GraduateStudent;
import com.example.rit_system.entities.Subject;
import com.example.rit_system.forms.GraduateStudentFormFragment;
import com.example.rit_system.forms.SubjectFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraduateStudentInfoFragment extends BottomSheetDialogFragment {

    public static final String TAG = "GraduateStudentInfo";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "student";
    private static final String ARG_PARAM2 = "position";

    // TODO: Rename and change types of parameters
    private GraduateStudent student;
    private int position;

    public GraduateStudentInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subject Parameter 1.
     * @param position Parameter 2.
     * @return A new instance of fragment SubjectInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraduateStudentInfoFragment newInstance(GraduateStudent student, int position) {
        GraduateStudentInfoFragment fragment = new GraduateStudentInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, student);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = (GraduateStudent) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graduate_student_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (student != null) {
            Log.d("Subject", student.toString());

            ((TextView) (view.findViewById(R.id.StudentName))).setText(student.getName());
            ((TextView) (view.findViewById(R.id.DateOfEntryStudent))).setText(student.getDateOfEntry().toString());
            ((TextView) (view.findViewById(R.id.RegistrationStudent))).setText(student.getRegistration());
            ((TextView) (view.findViewById(R.id.Email))).setText(student.getEmail());
            ((TextView) (view.findViewById(R.id.Phone))).setText(student.getPhoneNumber());
            ((TextView) (view.findViewById(R.id.NameOfMentee))).setText(student.getNameOfMentee());
            ((TextView) (view.findViewById(R.id.GraduateProgram))).setText(student.getGraduateProgram());
            ((TextView) (view.findViewById(R.id.ResearchTitle))).setText(student.getResearchTitle());
            ((TextView) (view.findViewById(R.id.DefenseDate))).setText(student.getDefenseDate().toString());
            ((TextView) (view.findViewById(R.id.Status))).setText(student.getStatus());
        }

        view.findViewById(R.id.EditButton).setOnClickListener(v -> {
            GraduateStudentFormFragment studentFormFragment = GraduateStudentFormFragment.newInstance(student);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, studentFormFragment).commit();
        });

        view.findViewById(R.id.DeleteButton).setOnClickListener(v -> {
            GraduateStudentDAO graduateStudentDAO = new GraduateStudentDAO(getContext());
            graduateStudentDAO.deleteGraduateStudentById(student.getId());

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(position);
            dismiss();
        });
    }
}