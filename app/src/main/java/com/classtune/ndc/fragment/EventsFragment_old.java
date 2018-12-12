package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.classtune.ndc.adapter.DashboardEventsAdapter;
import com.classtune.ndc.adapter.DashboardNoticeAdapter;
import com.classtune.ndc.model.EventsModel;
import com.classtune.ndc.model.NoticeModel;
import com.classtune.ndc.utils.PaginationAdapterCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment_old extends Fragment implements PaginationAdapterCallback, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<EventsModel> strList = new ArrayList<>();
    DashboardEventsAdapter dashboardEventsAdapter;
    FloatingActionButton event_fab;


    public EventsFragment_old() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
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
        initNoticeView(view);
    }

    private void initNoticeView(View view) {
        event_fab = view.findViewById(R.id.event_fab);
        event_fab.setOnClickListener(this);

        rv = (RecyclerView) view.findViewById(R.id.notice_recycleview);
//        important = view.findViewById(R.id.impotant);
//        recent = view.findViewById(R.id.recent);
//        common = view.findViewById(R.id.common);
//        recent.setOnClickListener(this);

        strList = getStrList();
        dashboardEventsAdapter = new DashboardEventsAdapter(getContext(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(dashboardEventsAdapter);
        dashboardEventsAdapter.setData(getStrList());
        dashboardEventsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_fab:
                gotoInstructorEventCreateFragment();
                break;
        }
    }

    private void gotoInstructorEventCreateFragment() {
        InsTructorEventCreateFragment insTructorEventCreateFragment = new InsTructorEventCreateFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, insTructorEventCreateFragment, "insTructorEventCreateFragment").addToBackStack(null);;
        transaction.commit();
    }

    private ArrayList<EventsModel> getStrList() {
        ArrayList<EventsModel> list = new ArrayList<>();
        EventsModel eventsModel = new EventsModel("The National Mourning Day", "NDC observed The National Mourning Day On 15 August 2018 with due Reverence and Solemnity, marking the 43rd Anniversary of the assassination of Father of the Nation Bangabandhu Sheikh Mujibur Rahman.");
        list.add(eventsModel);
        eventsModel = new EventsModel("VISIT by Secretary General of IMCTC to NDC", "Lieutenant General Abdul Elah bin Othman Al Salah, Secretary General of Islamic Coalition for Terrorism (IMCTC)  visited NDC on 14 November 2018 for delivering an opportunity lecture on ``IMCTC and its contribution to the peace and Security in the Middle East'' to ND Course 2018. Commandant, Faculty, Staff Officers and Course members of ND Course-2018 attended the Session.");
        list.add(eventsModel);
        eventsModel = new EventsModel("LECTURE TO ND COURSE", "Chief of Army Staff, General Aziz Ahmed, BGBM, PBGM, BGBMS, psc, G, delivered a keynote speech on Bangladesh Army to ND course 2018 and AFWC 2018 on 27 November 2018 at NDC. Commandant, Faculty, Staff Officers and all course members of 2018 attended the session.");
        list.add(eventsModel);
        eventsModel = new EventsModel("JOINT SESSION AT NDC", "Commandant, NDC, Lieutenant General Sheikh Mamun Khaled, SUP, rcds, psc, PhD delivered a lecture on Essential Leadership Qualities in the VUCA World");
        list.add(eventsModel);
        return list;

    }

    @Override
    public void retryPageLoad() {

    }
}
