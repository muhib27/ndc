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
import com.classtune.ndc.adapter.DashboardClassScheduleAdapter;
import com.classtune.ndc.adapter.DashboardNoticeAdapter;
import com.classtune.ndc.model.ClassScheduleModel;
import com.classtune.ndc.utils.PaginationAdapterCallback;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassScheduleFragment extends Fragment implements PaginationAdapterCallback, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<ClassScheduleModel> strList = new ArrayList<>();
    DashboardClassScheduleAdapter dashboardClassScheduleAdapter;


    public ClassScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        initClassScheduleView(view);
    }

    private void initClassScheduleView(View view) {

        rv = (RecyclerView) view.findViewById(R.id.class_schedule_rv);
//        important = view.findViewById(R.id.impotant);
//        recent = view.findViewById(R.id.recent);
//        common = view.findViewById(R.id.common);
//        recent.setOnClickListener(this);

        strList = getStrList();
        dashboardClassScheduleAdapter = new DashboardClassScheduleAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(dashboardClassScheduleAdapter);
        dashboardClassScheduleAdapter.setData(getStrList());
        dashboardClassScheduleAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pigeonhole_fab:
                gotoInstructorTaskAssignFragment();
                break;
        }
    }

    private void gotoInstructorTaskAssignFragment() {
        InsTructorTaskAssignFragment insTructorTaskAssignFragment = new InsTructorTaskAssignFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, insTructorTaskAssignFragment, "insTructorTaskAssignFragment").addToBackStack(null);;
        transaction.commit();
    }

    private ArrayList<ClassScheduleModel> getStrList() {
        ArrayList<ClassScheduleModel> list = new ArrayList<>();

        ClassScheduleModel classScheduleModel =new ClassScheduleModel("Keynote speech on national security", "9:00 - 11:00", "09 Dec 2018", "Maj. Gen. Tarique Ahmed Siddique,rcds,psc");
        list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Follow up discussion", "12:00 - 14:00", "09 Dec 2018", "All SDSs");
        list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Keynote speech on BAF", "9:00 - 11:00", "10 Dec 2018", "Air Chief Marshal Masihuzzaman,Serniabat, BBP,OSP,ndu,pscGD(P) Chief of air staff");
                list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Ethnocentrism, Strategic culture & Leadership language", "12:00 - 14:00", "10 Dec 2018", "Lt. Gen. Md. Mahfujur Rahman PSO,AFD");
        list.add(classScheduleModel);


//        list.add("Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");

        return list;
    }

    @Override
    public void retryPageLoad() {

    }
}
