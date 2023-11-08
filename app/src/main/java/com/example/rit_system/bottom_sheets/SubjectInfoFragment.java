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
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.entities.Subject;
import com.example.rit_system.forms.SubjectFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectInfoFragment extends BottomSheetDialogFragment {

    public static final String TAG = "SubjectInfo";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "subject";
    private static final String ARG_PARAM2 = "position";

    // TODO: Rename and change types of parameters
    private Subject subject;
    private int position;

    public SubjectInfoFragment() {
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
    public static SubjectInfoFragment newInstance(Subject subject, int position) {
        SubjectInfoFragment fragment = new SubjectInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, subject);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subject = (Subject) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (subject != null) {
            Log.d("Subject", subject.toString());

            ((TextView) (view.findViewById(R.id.SubjectCode))).setText(subject.getCode());
            ((TextView) (view.findViewById(R.id.SubjectName))).setText(subject.getName());
            ((TextView) (view.findViewById(R.id.StartHour))).setText(subject.getStartTime().toString());
            ((TextView) (view.findViewById(R.id.EndHour))).setText(subject.getEndTime().toString());
            ((TextView) (view.findViewById(R.id.Classroom))).setText(subject.getClassroom());
            ((TextView) (view.findViewById(R.id.TeacherName))).setText(subject.getTeacherName());
            ((TextView) (view.findViewById(R.id.CourseLoad))).setText(String.valueOf(subject.getCourseLoad()));
            ((TextView) view.findViewById(R.id.PreRequisites)).setText(subject.getRequirements());
            ((TextView) view.findViewById(R.id.Credits)).setText(String.valueOf(subject.getCredits()));
            ((TextView) view.findViewById(R.id.NumberOfVacancies)).setText( String.valueOf(subject.getNumberOfVacancies()));
        }

        view.findViewById(R.id.EditButton).setOnClickListener(v -> {
            SubjectFormFragment subjectFormFragment = SubjectFormFragment.newInstance(subject);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectFormFragment).commit();
        });

        view.findViewById(R.id.DeleteButton).setOnClickListener(v -> {
            SubjectDAO subjectDAO = new SubjectDAO(getContext());
            subjectDAO.delete(String.valueOf(subject.getId()));
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(position);
            dismiss();
        });
    }
}