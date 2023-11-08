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
import com.example.rit_system.PapersListFragment;
import com.example.rit_system.R;
import com.example.rit_system.bottom_sheets.PaperInfoFragment;
import com.example.rit_system.entities.Paper;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaperFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaperFormFragment extends Fragment {

    public static final String TAG = "PaperForm";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "paper";
    private static final String ARG_PARAM2 = "recycler";

    // TODO: Rename and change types of parameters
    private Paper paper;
    private RecyclerView recyclerView;

    public PaperFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaperFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaperFormFragment newInstance(Paper paper) {
        PaperFormFragment fragment = new PaperFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, paper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paper = (Paper) getArguments().getSerializable(ARG_PARAM1);
            recyclerView = (RecyclerView) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paper_form, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MaterialToolbar) view.findViewById(R.id.topAppBar)).setNavigationOnClickListener(v -> {
            PapersListFragment paperListFragment = new PapersListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperListFragment).commit();
        });

        if (paper != null) {
  //          ((TextInputEditText) view.findViewById(R.id.InputPaperCode)).setText(paper.getCode());

        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            PapersListFragment paperListFragment = new PapersListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {
            /*
            String code = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputPaperCode)).getText());
            String name = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputPaperName)).getText());
            String description = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputPaperDescription)).getText());
            LocalTime startHour = null, endHour = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String startHourText = ((TextInputEditText) view.findViewById(R.id.InputStartHourPaper)).getText().toString();
                String endHourText = ((TextInputEditText) view.findViewById(R.id.InputEndHourPaper)).getText().toString();

                try {
                    // Adicione os segundos e nanos segundos zerados
                    startHour = LocalTime.parse(startHourText);
                    endHour = LocalTime.parse(endHourText);
                } catch (DateTimeParseException e) {
                    // Trate exceções de formato inválido, se necessário
                    Log.e("ConversionError", "Formato de hora inválido");
                }
            }

            String classroom = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputClassroomPaper)).getText());
            String teacherName = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputTeacherNamePaper)).getText());
            String requirements = String.valueOf(((TextInputEditText) view.findViewById(R.id.InputPrerequisites)).getText());
            int courseLoad = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputCourseLoadPaper)).getText()));
            int credits = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputCreditsPaper)).getText()));
            int numberOfVancancies = Integer.valueOf(String.valueOf(((TextInputEditText) view.findViewById(R.id.InputNumberVancanciesPaper)).getText()));

            boolean isNew = (paper == null);

            if (isNew) paper = new Paper();

            paper.setName(name);
            paper.setCode(code);
            paper.setDescription(description);
            paper.setStartTime(startHour);
            paper.setEndTime(endHour);
            paper.setClassroom(classroom);
            paper.setTeacherName(teacherName);
            paper.setRequirements(requirements);
            paper.setCourseLoad(courseLoad);
            paper.setCredits(credits);
            paper.setNumberOfVacancies(numberOfVancancies);

            PaperDAO paperDAO = new PaperDAO(getContext());
            if(isNew) paperDAO.add(paper);
            else paperDAO.update(paper);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            PaperListFragment paperListFragment = new PaperListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperListFragment).commit();

             */
        });
    }
}