package com.classtune.ndc.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.CMListAdapter;
import com.classtune.ndc.adapter.DashboardAdapter;
import com.classtune.ndc.adapter.DashboardClassScheduleAdapter;
import com.classtune.ndc.adapter.DashboardEventsAdapter;
import com.classtune.ndc.adapter.DashboardNoticeAdapter;
import com.classtune.ndc.adapter.DashboardPigeonholeAdapter;
import com.classtune.ndc.adapter.DashboardReadingPackageAdapter;
import com.classtune.ndc.model.DemoClass;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment implements View.OnClickListener {
    RecyclerView rv, pigeonhole_recycleview, reading_package_rv, class_schedule_rv, events_rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<DemoClass> strList = new ArrayList<>();

    DashboardAdapter dashboardAdapter;
    PieChartView pieChartView;
    PieChartData pieChartData;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        initView(view);





    }

    private void initView(View view) {
        initAdapterView(view);
//        initPigeonholeView(view);
//        initReadingPackageView(view);
//        initClassScheduleView(view);
//        initEventsView(view);
//        initResearchView(view);

    }



    private void initAdapterView(View view) {

        rv = (RecyclerView) view.findViewById(R.id.notice_recycleview);

        strList = getStrList();
        dashboardAdapter = new DashboardAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(dashboardAdapter);
        dashboardAdapter.setData(getStrList());
        dashboardAdapter.notifyDataSetChanged();
    }





    private ArrayList<DemoClass> getStrList() {
//        ArrayList<DemoClass> list = new ArrayList<>();
//       DemoClass demoClass = new DemoClass("PigeonHole", "The etymology of Bangladesh (Country of Bengal) can be traced to the early 20th century");
//       list.add(demoClass);
//        demoClass = new DemoClass("Research", "The term gained official status during the Sultanate of Bengal in the 14th century.");
//        list.add(demoClass);
//        demoClass = new DemoClass("Class Schedule", "After the 1757 Battle of Plassey, Bengal was the first region of the Indian subcontinent.");
//        list.add(demoClass);
//        demoClass = new DemoClass("PigeonHole", "The etymology of Bangladesh (Country of Bengal) can be traced to the early 20th century");
//        list.add(demoClass);
//        demoClass = new DemoClass("Research", "The term gained official status during the Sultanate of Bengal in the 14th century.");
//        list.add(demoClass);
//        demoClass = new DemoClass("Class Schedule", "After the 1757 Battle of Plassey, Bengal was the first region of the Indian subcontinent.");
//        list.add(demoClass);
//        demoClass = new DemoClass("PigeonHole", "The etymology of Bangladesh (Country of Bengal) can be traced to the early 20th century");
//        list.add(demoClass);
//        demoClass = new DemoClass("Research", "The term gained official status during the Sultanate of Bengal in the 14th century.");
//        list.add(demoClass);
//        demoClass = new DemoClass("Class Schedule", "After the 1757 Battle of Plassey, Bengal was the first region of the Indian subcontinent.");
//        list.add(demoClass);
//        return list;

        ArrayList<DemoClass> list = new ArrayList<>();
        DemoClass demoClass = new DemoClass("Pigeon Hole", "The etymology of Bangladesh", "The etymology of Bangladesh (Country of Bengal) can be traced to the early 20th century");
        list.add(demoClass);
        demoClass = new DemoClass("Research", "Developing National Policy", "Developing National Policy Guidelines on United Nations Peace Operations");
        list.add(demoClass);
        demoClass = new DemoClass("Class Schedule", "National Security", "Keynote speech on national security");
        list.add(demoClass);
        demoClass = new DemoClass("Pigeon Hole", "The Syrian Crisis", "The Syrian Crisis: Role of Major Powers");
        list.add(demoClass);
        demoClass = new DemoClass("Research", "Social Compliance", "Social Compliance in Readymade Garments (RMG) Sector: Towards Ensuring Equitable Justice for Garment Workers and Boosting Bangladesh’s Export Economy");
        list.add(demoClass);
        demoClass = new DemoClass("Class Schedule", "BAF", "Keynote speech on BAF");
        list.add(demoClass);
        demoClass = new DemoClass("Pigeon Hole", "National Security Strategy", "Need For National Security Strategy: Relevant Concept and Paradigm and Context of Bangladesh");
        list.add(demoClass);
        demoClass = new DemoClass("Research", "The Sultanate of Bengal", "The term gained official status during the Sultanate of Bengal in the 14th century.");
        list.add(demoClass);
        demoClass = new DemoClass("Class Schedule", "Ethnocentrism", "Ethnocentrism, Strategic culture & Leadership language");
        list.add(demoClass);
        return list;
    }
    private ArrayList<String> getRecent() {
        ArrayList<String> list = new ArrayList<>();
        list.add("getRecent");
        list.add("getRecent");
        list.add("getRecent");
        list.add("getRecent");


        return list;
    }
    private ArrayList<String> getCommon() {
        ArrayList<String> list = new ArrayList<>();
        list.add("getCommon");
        list.add("getCommon");
        list.add("getCommon");
        list.add("getCommon");


        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    private void gotoResearchListFragment() {
        ResearchListFragment researchListFragment = new ResearchListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, researchListFragment, "researchListFragment").addToBackStack(null);;
        transaction.commit();
    }
}
