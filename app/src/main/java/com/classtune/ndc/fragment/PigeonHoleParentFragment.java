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
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.CCViewPagerAdapter;
import com.classtune.ndc.adapter.PigeonholeViewPagerAdapter;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.utils.AppSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class PigeonHoleParentFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PigeonholeViewPagerAdapter pigeonholeViewPagerAdapter;
    View view;

    String tabOne = "", tabTwo = "";


    public PigeonHoleParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pigeon_hole_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        UserPermission userPermission = AppSharedPreference.getUserPermission();

        int back = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        if(userPermission.isUserTasksSubmitTask()){
            tabOne = "Assignment";
            tabTwo = "My Submission";
        }
        else {
            tabOne = "My Assignment";
            tabTwo = "My CM Submission";
        }
        pigeonholeViewPagerAdapter = new PigeonholeViewPagerAdapter(getChildFragmentManager(), tabOne, tabTwo);
        viewPager.setAdapter(pigeonholeViewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        InstructorDetailsFragment instructorDetailsFragment = (InstructorDetailsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("instructorDetailsFragment");
//        if(instructorDetailsFragment !=null && instructorDetailsFragment.isVisible())
//            Toast.makeText(getActivity(), "" + instructorDetailsFragment , Toast.LENGTH_SHORT).show();

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
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#DFE1E2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#727272"));
                }
               else if(tab.getPosition()== 1) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffdf00"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#324055"));
                }
//                else if(tab.getPosition()== 2) {
//                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#007894"));
////                    tabLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
//                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
