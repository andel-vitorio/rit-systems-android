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
import com.example.rit_system.dao.PaperDAO;
import com.example.rit_system.entities.Paper;
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
            ((TextInputEditText) view.findViewById(R.id.InputPaperTitle)).setText(paper.getTitle());
            ((TextInputEditText) view.findViewById(R.id.InputKeyWord)).setText(paper.getKeywords());
            ((TextInputEditText) view.findViewById(R.id.InputPublicationDate)).setText(paper.getPublicationDate().toString());
            ((TextInputEditText) view.findViewById(R.id.InputAuthors)).setText(paper.getAuthors());
            ((TextInputEditText) view.findViewById(R.id.InputAbstract)).setText(paper.getDescription());
            ((TextInputEditText) view.findViewById(R.id.InputTopics)).setText(paper.getCategory());
            ((TextInputEditText) view.findViewById(R.id.InputLink)).setText(paper.getUrl());

        }

        view.findViewById(R.id.CancelButton).setOnClickListener(v -> {
            PapersListFragment paperListFragment = new PapersListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperListFragment).commit();
        });

        view.findViewById(R.id.AddButton).setOnClickListener(v -> {

            boolean isNew = (paper == null);

            if (isNew) paper = new Paper();

            paper.setTitle(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputPaperTitle)).getText()).toString());
            paper.setKeywords(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputKeyWord)).getText()).toString());

            String publicationDateString = Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputPublicationDate)).getText()).toString();
            LocalDate publicationDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                publicationDate = LocalDate.parse(publicationDateString);
            }
            paper.setPublicationDate(publicationDate);

            paper.setAuthors(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputAuthors)).getText()).toString());
            paper.setDescription(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputAbstract)).getText()).toString());
            paper.setCategory(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputTopics)).getText()).toString());
            paper.setUrl(Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.InputLink)).getText()).toString());

            PaperDAO paperDAO = new PaperDAO(getContext());
            if(isNew) paperDAO.addPaper(paper);
            else paperDAO.updatePaper(paper.getId(), paper);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(0);

            PapersListFragment paperListFragment = new PapersListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperListFragment).commit();

        });
    }
}