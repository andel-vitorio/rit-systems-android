package com.example.rit_system.forms;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rit_system.HomeFragment;
import com.example.rit_system.R;
import com.example.rit_system.SubjectListFragment;
import com.example.rit_system.bottom_sheets.SubjectInfoFragment;
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.entities.Subject;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFormFragment extends Fragment {

    public static final String TAG = "SubjectForm";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "subject";
    private static final String ARG_PARAM2 = "recycler";

    // TODO: Rename and change types of parameters
    private Subject subject;
    private RecyclerView recyclerView;

    public SubjectFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectFormFragment newInstance(Subject subject) {
        SubjectFormFragment fragment = new SubjectFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, subject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subject = (Subject) getArguments().getSerializable(ARG_PARAM1);
            recyclerView = (RecyclerView) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_form, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            SubjectListFragment subjectListFragment = new SubjectListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectListFragment).commit();
        });

        if (subject != null) {
            ((TextInputEditText) view.findViewById(R.id.InputSubjectCode)).setText(subject.getCode());
            ((TextInputEditText) view.findViewById(R.id.InputSubjectName)).setText(subject.getName());
            ((TextInputEditText) view.findViewById(R.id.InputSubjectDescription)).setText(subject.getDescription());
            ((TextInputEditText) view.findViewById(R.id.InputStartHourSubject)).setText(subject.getStartTime().toString());
            ((TextInputEditText) view.findViewById(R.id.InputEndHourSubject)).setText(subject.getEndTime().toString());
            ((TextInputEditText) view.findViewById(R.id.InputClassroomSubject)).setText(subject.getClassroom());
            ((TextInputEditText) view.findViewById(R.id.InputTeacherNameSubject)).setText(subject.getTeacherName());
            ((TextInputEditText) view.findViewById(R.id.InputPrerequisites)).setText(subject.getRequirements());
            ((TextInputEditText) view.findViewById(R.id.InputCourseLoadSubject)).setText(String.valueOf(subject.getCourseLoad()));
            ((TextInputEditText) view.findViewById(R.id.InputCreditsSubject)).setText(String.valueOf(subject.getCredits()));
            ((TextInputEditText) view.findViewById(R.id.InputNumberVancanciesSubject)).setText(String.valueOf(subject.getNumberOfVacancies()));
        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            SubjectListFragment subjectListFragment = new SubjectListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {
            String code = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputSubjectCode)).getText());
            String name = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputSubjectName)).getText());
            String description = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputSubjectDescription)).getText());
            LocalTime startHour = null, endHour = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String startHourText = ((TextInputEditText) view.findViewById(R.id.InputStartHourSubject)).getText().toString();
                String endHourText = ((TextInputEditText) view.findViewById(R.id.InputEndHourSubject)).getText().toString();

                try {
                    // Adicione os segundos e nanos segundos zerados
                    startHour = LocalTime.parse(startHourText);
                    endHour = LocalTime.parse(endHourText);
                } catch (DateTimeParseException e) {
                    // Trate exceções de formato inválido, se necessário
                    Log.e("ConversionError", "Formato de hora inválido");
                }
            }

            String classroom = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputClassroomSubject)).getText());
            String teacherName = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputTeacherNameSubject)).getText());
            String requirements = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputPrerequisites)).getText());
            int courseLoad = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputCourseLoadSubject)).getText()));
            int credits = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputCreditsSubject)).getText()));
            int numberOfVancancies = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputNumberVancanciesSubject)).getText()));
            
            boolean isNew = (subject == null);
            
            if (isNew) subject = new Subject();
            
            subject.setName(name);
            subject.setCode(code);
            subject.setDescription(description);
            subject.setStartTime(startHour);
            subject.setEndTime(endHour);
            subject.setClassroom(classroom);
            subject.setTeacherName(teacherName);
            subject.setRequirements(requirements);
            subject.setCourseLoad(courseLoad);
            subject.setCredits(credits);
            subject.setNumberOfVacancies(numberOfVancancies);

            SubjectDAO subjectDAO = new SubjectDAO(getContext());
            if(isNew) subjectDAO.add(subject);
            else subjectDAO.update(subject);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            SubjectListFragment subjectListFragment = new SubjectListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectListFragment).commit();
        });
    }
}