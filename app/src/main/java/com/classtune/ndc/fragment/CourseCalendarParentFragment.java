package com.classtune.ndc.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.CCViewPagerAdapter;
import com.classtune.ndc.utils.AppConstant;
import com.classtune.ndc.viewhelpers.CustomTabButton;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseCalendarParentFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    CCViewPagerAdapter ccViewPagerAdapter;
    View view;


//    CustomTabButton generalBtn, announcementBtn, circulerBtn;


    public CourseCalendarParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_calendar_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        int back = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ccViewPagerAdapter = new CCViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(ccViewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition()== 0) {
//                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
////                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
//                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#727272"));
//                }
//                else
                    if(tab.getPosition()== 0) {
                    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#DFE1E2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    //tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#727272"));


                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
                        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
               else if(tab.getPosition()== 1) {
                    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffdf00"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    //tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#324055"));

                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
                        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
                else if(tab.getPosition()== 2) {
                   // tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#007894"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
                   // tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
                        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//
//    private void initView(View view) {
//
//        HashMap<String, Integer> btnGeneralTag = new HashMap<String, Integer>();
//
//        generalBtn = (CustomTabButton) view
//                .findViewById(R.id.btn_notice_general);
//        //generalBtn.setOnClickListener(this);
//
//        btnGeneralTag.put(AppConstant.NORMAL, R.drawable.eye_gray);
//        btnGeneralTag.put(AppConstant.SELECTED, R.drawable.eye_gray);
//
//        generalBtn.setTag(btnGeneralTag);
//
//        circulerBtn = (CustomTabButton) view
//                .findViewById(R.id.btn_notice_circular);
//       // circulerBtn.setOnClickListener(this);
//
//        HashMap<String, Integer> btnCircularTag = new HashMap<String, Integer>();
//        btnCircularTag.put(AppConstant.NORMAL, R.drawable.circular_gray);
//        btnCircularTag.put(AppConstant.SELECTED, R.drawable.circular_black);
//
//        circulerBtn.setTag(btnCircularTag);
//
//        announcementBtn = (CustomTabButton) view
//                .findViewById(R.id.btn_notice_announcement);
//       // announcementBtn.setOnClickListener(this);
//
//        HashMap<String, Integer> btnAnnouncementTag = new HashMap<String, Integer>();
//        btnAnnouncementTag
//                .put(AppConstant.NORMAL, R.drawable.annaouncment_gray);
//        btnAnnouncementTag.put(AppConstant.SELECTED,
//                R.drawable.annaouncment_black);
//
//        announcementBtn.setTag(btnAnnouncementTag);
//
//        generalBtn.setButtonSelected(true,
//                getResources().getColor(R.color.black), R.drawable.eye_black);
//        current = generalBtn;
//
//    }
}
