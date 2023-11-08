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
import android.widget.TextView;

import com.example.rit_system.R;
import com.example.rit_system.StudentListFragment;
import com.example.rit_system.dao.GraduateStudentDAO;
import com.example.rit_system.entities.GraduateStudent;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraduateStudentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraduateStudentFormFragment extends Fragment {

    public static final String TAG = "GraduateStudentForm";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "student";
    private static final String ARG_PARAM2 = "recycler";

    // TODO: Rename and change types of parameters
    private GraduateStudent student;
    private RecyclerView recyclerView;

    public GraduateStudentFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraduateStudentFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraduateStudentFormFragment newInstance(GraduateStudent student) {
        GraduateStudentFormFragment fragment = new GraduateStudentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, student);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = (GraduateStudent) getArguments().getSerializable(ARG_PARAM1);
            recyclerView = (RecyclerView) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graduate_student_form, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            StudentListFragment studentListFragment = new StudentListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, studentListFragment).commit();
        });

        if (student != null) {
            ((TextInputEditText) view.findViewById(R.id.InputFullName)).setText(student.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((TextInputEditText) view.findViewById(R.id.InputDataOfEntry)).setText(student.getDateOfEntry().toString());
            }
            ((TextInputEditText) view.findViewById(R.id.InputRegistration)).setText(student.getRegistration());
            ((TextInputEditText) view.findViewById(R.id.InputEmail)).setText(student.getEmail());
            ((TextInputEditText) view.findViewById(R.id.InputPhone)).setText(student.getPhoneNumber());
            ((TextInputEditText) view.findViewById(R.id.InputNameOfMentee)).setText(student.getNameOfMentee());
            ((TextInputEditText) view.findViewById(R.id.InputGraduateProgram)).setText(student.getGraduateProgram());
            ((TextInputEditText) view.findViewById(R.id.InputResearchTitle)).setText(student.getResearchTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((TextInputEditText) view.findViewById(R.id.InputDefenseDate)).setText(student.getDefenseDate().toString());
            }
            ((TextInputEditText) view.findViewById(R.id.InputStatus)).setText(student.getStatus());

        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            StudentListFragment studentListFragment = new StudentListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, studentListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {

            boolean isNew = (student == null);

            if (isNew) student = new GraduateStudent();

            student.setName(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputFullName)).getText()).toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                student.setDateOfEntry(LocalDate.parse(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputDataOfEntry)).getText()).toString()));
            }
            student.setRegistration(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputRegistration)).getText()).toString());
            student.setEmail(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputEmail)).getText()).toString());
            student.setPhoneNumber(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputPhone)).getText()).toString());
            student.setNameOfMentee(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputNameOfMentee)).getText()).toString());
            student.setGraduateProgram(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputGraduateProgram)).getText()).toString());
            student.setResearchTitle(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputResearchTitle)).getText()).toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                student.setDefenseDate(LocalDate.parse(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputDefenseDate)).getText()).toString()));
            }
            student.setStatus(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputStatus)).getText()).toString());

            GraduateStudentDAO graduateStudentDAO = new GraduateStudentDAO(getContext());
            if(isNew) graduateStudentDAO.addGraduateStudent(student);
            else graduateStudentDAO.updateGraduateStudent(student.getId(), student);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            StudentListFragment studentListFragment = new StudentListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, studentListFragment).commit();
        });
    }
}