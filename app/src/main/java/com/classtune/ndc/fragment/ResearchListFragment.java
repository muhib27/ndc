package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.CMListAdapter;
import com.classtune.ndc.adapter.ResearchListAdapter;
import com.classtune.ndc.model.ResearcherModel;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchListFragment extends Fragment {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<ResearcherModel> strList = new ArrayList<>();
    ResearchListAdapter researchListAdapter;


    public ResearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String cmType = bundle.getString("cmType");
        }

        rv = (RecyclerView) view.findViewById(R.id.main_recycler);
        strList = getStrList();
        researchListAdapter = new ResearchListAdapter(getContext(), strList);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(researchListAdapter);
    }


    private ArrayList<ResearcherModel> getStrList() {
        ArrayList<ResearcherModel> list = new ArrayList<>();
        ResearcherModel researcherModel = new ResearcherModel("Brig Gen Monirul Islam Akhand, ndc, psc","ndc1@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Col Salahuddin Khaled","ndc2@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Col Mohammad Shahriar Zaman, afwc, psc, G, Arty","ndc3@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col S M Merazul Islam, afwc, psc, Engr","ndc4@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col Syed Jamil Ahsan, afwc, psc, AC","ndc5@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("LT Col Md Kamrul Islam, BGBM, psc, Arty","ndc6@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col A S M Badiul Alam, afwc, psc, Arty","ndc7@ndc.gov.bd");
        list.add(researcherModel);

        return list;
    }

//    private void gotoPigeonholeFragment() {
//        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);;
//        transaction.commit();
//    }
}
