package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructorDetailsFragment extends Fragment implements View.OnClickListener {
    LinearLayout submittedLayout, pendingLayout;


    public InstructorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView(view);
    }

    private void initView(View view) {
        submittedLayout = view.findViewById(R.id.submittedLayout);
        submittedLayout.setOnClickListener(this);
        pendingLayout = view.findViewById(R.id.pendingLayout);
        pendingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submittedLayout:
                gotoCMListFragment("submitted");
                break;
            case R.id.pendingLayout:
                gotoCMListFragment("pending");
                break;
        }
    }

    private void gotoCMListFragment(String cmType) {
        Bundle bundle = new Bundle();
        bundle.putString("cmType", cmType);
        CMListFragment cmListFragment = new CMListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmListFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmListFragment, "cmListFragment").addToBackStack(null);;
        transaction.commit();
    }
}
