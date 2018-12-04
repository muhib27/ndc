//package com.classtune.ndc.fragment;
//
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.classtune.ndc.R;
//import com.classtune.ndc.activity.MainActivity;
//import com.classtune.ndc.adapter.DashboardClassScheduleAdapter;
//import com.classtune.ndc.adapter.DashboardEventsAdapter;
//import com.classtune.ndc.adapter.DashboardNoticeAdapter;
//import com.classtune.ndc.adapter.DashboardPigeonholeAdapter;
//import com.classtune.ndc.adapter.DashboardReadingPackageAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import lecho.lib.hellocharts.model.PieChartData;
//import lecho.lib.hellocharts.model.SliceValue;
//import lecho.lib.hellocharts.view.PieChartView;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class DashBoardFragmentOld extends Fragment implements View.OnClickListener {
//    RecyclerView rv, pigeonhole_recycleview, reading_package_rv, class_schedule_rv, events_rv;
//    LinearLayoutManager linearLayoutManager;
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    ArrayList<String> strList = new ArrayList<>();
//    DashboardNoticeAdapter dashboardNoticeAdapter;
//    DashboardPigeonholeAdapter dashboardPigeonholeAdapter;
//    DashboardReadingPackageAdapter dashboardReadingPackageAdapter;
//    DashboardClassScheduleAdapter dashboardClassScheduleAdapter;
//    DashboardEventsAdapter dashboardEventsAdapter;
//    TextView recent, important, common;
//    PieChartView pieChartView;
//    PieChartData pieChartData;
//
//    public DashBoardFragmentOld() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dash_board, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            MainActivity.toggle.setDrawerIndicatorEnabled(true);
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
//        initView(view);
//
//
//
//
//
//    }
//
//    private void initView(View view) {
//        initNoticeView(view);
//        initPigeonholeView(view);
//        initReadingPackageView(view);
//        initClassScheduleView(view);
//        initEventsView(view);
//        initResearchView(view);
//
//    }
//
//    private void initResearchView(View view) {
//        pieChartView = view.findViewById(R.id.chart);
//
//        List pieData = new ArrayList<>();
//        pieData.add(new SliceValue(75, ContextCompat.getColor(getActivity(), R.color.selected_color)));
//        pieData.add(new SliceValue(25, ContextCompat.getColor(getActivity(), R.color.not_selected_color)));
//
//
//        PieChartData pieChartData = new PieChartData(pieData);
//        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
//        pieChartData.setHasCenterCircle(true).setCenterText1("").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
//        pieChartView.setPieChartData(pieChartData);
//
//        pieChartView.setOnClickListener(this);
//    }
//
//    private void initNoticeView(View view) {
//
//        rv = (RecyclerView) view.findViewById(R.id.notice_recycleview);
//        important = view.findViewById(R.id.impotant);
//        recent = view.findViewById(R.id.recent);
//        common = view.findViewById(R.id.common);
//        recent.setOnClickListener(this);
//
//        strList = getStrList();
//        dashboardNoticeAdapter = new DashboardNoticeAdapter(getContext(), 0);
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        rv.setLayoutManager(linearLayoutManager);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        rv.setAdapter(dashboardNoticeAdapter);
//        dashboardNoticeAdapter.setData(getStrList());
//        dashboardNoticeAdapter.notifyDataSetChanged();
//    }
//    private void initPigeonholeView(View view) {
//
//        pigeonhole_recycleview = (RecyclerView) view.findViewById(R.id.pigeonhole_recycleview);
////        important = view.findViewById(R.id.impotant);
////        recent = view.findViewById(R.id.recent);
////        common = view.findViewById(R.id.common);
////        recent.setOnClickListener(this);
//
//        strList = getStrList();
//        dashboardPigeonholeAdapter = new DashboardPigeonholeAdapter(getContext());
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        pigeonhole_recycleview.setLayoutManager(linearLayoutManager);
//        pigeonhole_recycleview.setItemAnimator(new DefaultItemAnimator());
//        pigeonhole_recycleview.setAdapter(dashboardPigeonholeAdapter);
//        dashboardPigeonholeAdapter.setData(getRecent());
//        dashboardPigeonholeAdapter.notifyDataSetChanged();
//    }
//    private void initReadingPackageView(View view) {
//
//        reading_package_rv = (RecyclerView) view.findViewById(R.id.reading_package_rv);
////        important = view.findViewById(R.id.impotant);
////        recent = view.findViewById(R.id.recent);
////        common = view.findViewById(R.id.common);
////        recent.setOnClickListener(this);
//
//
//        dashboardReadingPackageAdapter = new DashboardReadingPackageAdapter(getContext());
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        reading_package_rv.setLayoutManager(linearLayoutManager);
//        reading_package_rv.setItemAnimator(new DefaultItemAnimator());
//        reading_package_rv.setAdapter(dashboardReadingPackageAdapter);
//        dashboardReadingPackageAdapter.setData(getRecent());
//        dashboardReadingPackageAdapter.notifyDataSetChanged();
//    }
//    private void initClassScheduleView(View view) {
//
//        class_schedule_rv = (RecyclerView) view.findViewById(R.id.class_schedule_rv);
//
//        dashboardClassScheduleAdapter = new DashboardClassScheduleAdapter(getContext(),0);
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        class_schedule_rv.setLayoutManager(linearLayoutManager);
//        class_schedule_rv.setItemAnimator(new DefaultItemAnimator());
//        class_schedule_rv.setAdapter(dashboardClassScheduleAdapter);
//        dashboardClassScheduleAdapter.setData(getRecent());
//        dashboardClassScheduleAdapter.notifyDataSetChanged();
//    }
//    private void initEventsView(View view) {
//
//        events_rv = (RecyclerView) view.findViewById(R.id.events_rv);
//
//        dashboardEventsAdapter = new DashboardEventsAdapter(getContext(),0);
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        events_rv.setLayoutManager(linearLayoutManager);
//        events_rv.setItemAnimator(new DefaultItemAnimator());
//        events_rv.setAdapter(dashboardEventsAdapter);
//        dashboardEventsAdapter.setData(getRecent());
//        dashboardEventsAdapter.notifyDataSetChanged();
//    }
//
//    private ArrayList<String> getStrList() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//        list.add("Lorem ipsum dolor sit amet");
//
//        return list;
//    }
//    private ArrayList<String> getRecent() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("getRecent");
//        list.add("getRecent");
//        list.add("getRecent");
//        list.add("getRecent");
//
//
//        return list;
//    }
//    private ArrayList<String> getCommon() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("getCommon");
//        list.add("getCommon");
//        list.add("getCommon");
//        list.add("getCommon");
//
//
//        return list;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.recent:
////                dashboardAdapter.setData(getRecent());
////                dashboardAdapter.notifyDataSetChanged();
//                break;
//            case R.id.impotant:
//                break;
//            case R.id.common:
//                break;
//            case R.id.chart:
//                gotoResearchListFragment();
//                break;
//        }
//    }
//
//    private void gotoResearchListFragment() {
//        ResearchListFragment researchListFragment = new ResearchListFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, researchListFragment, "researchListFragment").addToBackStack(null);;
//        transaction.commit();
//    }
//}
