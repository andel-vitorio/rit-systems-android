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
import com.example.rit_system.dao.PaperDAO;
import com.example.rit_system.entities.Paper;
import com.example.rit_system.forms.PaperFormFragment;
import com.example.rit_system.models.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaperInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaperInfoFragment extends BottomSheetDialogFragment {

    public static final String TAG = "PaperInfo";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "paper";
    private static final String ARG_PARAM2 = "position";

    // TODO: Rename and change types of parameters
    private Paper paper;
    private int position;

    public PaperInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paper Parameter 1.
     * @param position Parameter 2.
     * @return A new instance of fragment PaperInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaperInfoFragment newInstance(Paper paper, int position) {
        PaperInfoFragment fragment = new PaperInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, paper);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paper = (Paper) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paper_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (paper != null) {
            Log.d("Paper", paper.toString());
            ((TextView) view.findViewById(R.id.PaperTitle)).setText(paper.getTitle());
            ((TextView) view.findViewById(R.id.PublicationDate)).setText(paper.getPublicationDate().toString());
            ((TextView) view.findViewById(R.id.KeyWords)).setText(paper.getKeywords());
            ((TextView) view.findViewById(R.id.Abstract)).setText(paper.getDescription());
            ((TextView) view.findViewById(R.id.Topics)).setText(paper.getCategory());
            ((TextView) view.findViewById(R.id.Link)).setText(paper.getUrl());
        }

        view.findViewById(R.id.EditButton).setOnClickListener(v -> {
            PaperFormFragment paperFormFragment = PaperFormFragment.newInstance(paper);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ActivityMain, paperFormFragment).commit();
        });

        view.findViewById(R.id.DeleteButton).setOnClickListener(v -> {
            PaperDAO paperDAO = new PaperDAO(getContext());
            paperDAO.deletePaperById(paper.getId());
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setItemIndex(position);
            dismiss();
        });
    }
}