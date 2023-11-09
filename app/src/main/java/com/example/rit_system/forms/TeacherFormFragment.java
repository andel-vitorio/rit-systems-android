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
import com.example.rit_system.TeacherListFragment;
import com.example.rit_system.bottom_sheets.TeacherInfoFragment;
import com.example.rit_system.dao.TeacherDAO;
import com.example.rit_system.entities.Teacher;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFormFragment extends Fragment {

    public static final String TAG = "TeacherForm";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "teacher";
    private static final String ARG_PARAM2 = "recycler";

    // TODO: Rename and change types of parameters
    private Teacher teacher;
    private RecyclerView recyclerView;

    public TeacherFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherFormFragment newInstance(Teacher teacher) {
        TeacherFormFragment fragment = new TeacherFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, teacher);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teacher = (Teacher) getArguments().getSerializable(ARG_PARAM1);
            recyclerView = (RecyclerView) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_form, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            TeacherListFragment teacherListFragment = new TeacherListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, teacherListFragment).commit();
        });

        if (teacher != null) {
            ((TextInputEditText) view.findViewById(R.id.InputFullName)).setText(teacher.getName());
            ((TextInputEditText) view.findViewById(R.id.InputBirthDay)).setText(teacher.getBirthDay().toString());
            ((TextInputEditText) view.findViewById(R.id.InputIdentificationNumber)).setText(teacher.getIndentificatorNumber());
            ((TextInputEditText) view.findViewById(R.id.InputEmail)).setText(teacher.getEmail());
            ((TextInputEditText) view.findViewById(R.id.InputPhoneNumber)).setText(teacher.getPhone());
            ((TextInputEditText) view.findViewById(R.id.InputTrainingArea)).setText(teacher.getTrainingArea());
            ((TextInputEditText) view.findViewById(R.id.InputYearOfExperience)).setText( String.valueOf(teacher.getYearsOfExperience()));
        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            TeacherListFragment teacherListFragment = new TeacherListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, teacherListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {

            boolean isNew = (teacher == null);

            if (isNew) teacher = new Teacher();

            TextInputEditText fullNameEditText = view.findViewById(R.id.InputFullName);
            TextInputEditText birthDayEditText = view.findViewById(R.id.InputBirthDay);
            TextInputEditText identificationNumberEditText = view.findViewById(R.id.InputIdentificationNumber);
            TextInputEditText emailEditText = view.findViewById(R.id.InputEmail);
            TextInputEditText phoneNumberEditText = view.findViewById(R.id.InputPhoneNumber);
            TextInputEditText trainingAreaEditText = view.findViewById(R.id.InputTrainingArea);
            TextInputEditText yearOfExperienceEditText = view.findViewById(R.id.InputYearOfExperience);

            String fullName = Objects.requireNonNull(fullNameEditText.getText()).toString();
            String birthDayString = Objects.requireNonNull(birthDayEditText.getText()).toString();
            String identificationNumber = Objects.requireNonNull(identificationNumberEditText.getText()).toString();
            String email = Objects.requireNonNull(emailEditText.getText()).toString();
            String phoneNumber = Objects.requireNonNull(phoneNumberEditText.getText()).toString();
            String trainingArea = Objects.requireNonNull(trainingAreaEditText.getText()).toString();

            int yearsOfExperience = Integer.parseInt(Objects.requireNonNull(yearOfExperienceEditText.getText()).toString());

            LocalDate birthDay = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                birthDay = LocalDate.parse(birthDayString);
            }

            teacher.setName(fullName);
            teacher.setBirthDay(birthDay);
            teacher.setIndentificatorNumber(identificationNumber);
            teacher.setEmail(email);
            teacher.setPhone(phoneNumber);
            teacher.setTrainingArea(trainingArea);
            teacher.setYearsOfExperience(yearsOfExperience);

            System.out.println(teacher + " " + teacher.getId());

            TeacherDAO teacherDAO = new TeacherDAO(getContext());
            if(isNew) teacherDAO.add(teacher);
            else teacherDAO.update(teacher);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            TeacherListFragment teacherListFragment = new TeacherListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, teacherListFragment).commit();
        });
    }
}