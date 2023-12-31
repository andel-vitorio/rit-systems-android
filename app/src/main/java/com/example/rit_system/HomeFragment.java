package com.example.rit_system;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rit_system.adapters.MenuRecyclerViewAdapter;
import com.example.rit_system.adapters.SubjectRecyclerViewAdapter;
import com.example.rit_system.dao.CoordinationActivityDAO;
import com.example.rit_system.dao.GraduateStudentDAO;
import com.example.rit_system.dao.PaperDAO;
import com.example.rit_system.dao.SubjectDAO;
import com.example.rit_system.dao.UndergraduateStudentDAO;
import com.example.rit_system.databinding.FragmentHomeBinding;
import com.example.rit_system.entities.CoordinationActivity;
import com.example.rit_system.entities.GraduateStudent;
import com.example.rit_system.entities.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuRecyclerViewAdapter.MenuDataset[] dataset = new MenuRecyclerViewAdapter.MenuDataset[4];

        dataset[0] = new MenuRecyclerViewAdapter.MenuDataset("Disciplinas", R.drawable.subject_icon);
        dataset[1] = new MenuRecyclerViewAdapter.MenuDataset("Estudantes", R.drawable.student_icon);
        dataset[2] = new MenuRecyclerViewAdapter.MenuDataset("Atividades da Coordenação", R.drawable.activity_icon);
        dataset[3] = new MenuRecyclerViewAdapter.MenuDataset("Artigos Publicados", R.drawable.paper_icon);

        RecyclerView recyclerView = view.findViewById(R.id.menuRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MenuRecyclerViewAdapter(dataset, position -> {
            if (position == 0) {
                SubjectListFragment subjectListFragment = new SubjectListFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, subjectListFragment).commit();
            } else if (position == 1) {
                StudentListFragment studentListFragment = new StudentListFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, studentListFragment).commit();
            } else if (position == 2) {
                CoordinationActivityListFragment coordinationActivityListFragment = new CoordinationActivityListFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, coordinationActivityListFragment).commit();
            } else if (position == 3) {
                PapersListFragment papersListFragment = new PapersListFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.ActivityMain, papersListFragment).commit();
            }
        }));

        SubjectDAO subjectDAO = new SubjectDAO(getContext());
        PaperDAO paperDAO = new PaperDAO(getContext());
        GraduateStudentDAO graduateStudentDAO = new GraduateStudentDAO(getContext());
        UndergraduateStudentDAO undergraduateStudentDAO = new UndergraduateStudentDAO(getContext());
        CoordinationActivityDAO coordinationActivityDAO = new CoordinationActivityDAO(getContext());


        //((TextView) view.findViewById(R.id.teacher_amount_tv)).setText(subjectDAO.getList().size());
        binding.subjectsAmountTv.setText(String.valueOf(subjectDAO.getList().size()));
        binding.studentAmountTv.setText(String.valueOf(
                graduateStudentDAO.getGraduateStudents().size() + undergraduateStudentDAO.getUndergraduateStudents().size()
        ));
        binding.coordActivityAmountTv.setText(String.valueOf(coordinationActivityDAO.getCoordinationActivities().size()));
        binding.papersAmountTv.setText(String.valueOf(paperDAO.getPapers().size()));

    }

}