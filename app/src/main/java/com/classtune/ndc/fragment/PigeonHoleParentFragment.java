package com.classtune.ndc.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    String tabThree = "", tabFour = "";


    public PigeonHoleParentFragment() {
        // Required empty public constructor
    }
//    https://stackoverflow.com/questions/37833495/add-iconstext-to-tablayout

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
            tabThree = "All Assignment";
            tabFour = "All CM Submission";
        }
        else {
            tabOne = "My Assignment";
            tabTwo = "All Assignment";
            tabThree = "My CM Submission";
            tabFour = "All CM Submission";
        }
        pigeonholeViewPagerAdapter = new PigeonholeViewPagerAdapter(getChildFragmentManager(), tabOne, tabTwo, tabThree, tabFour);
        viewPager.setAdapter(pigeonholeViewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View root = tabLayout.getChildAt(i);
            if (root instanceof LinearLayout) {
                ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(getResources().getColor(R.color.ndc_color));
                drawable.setSize(2, 1);
                ((LinearLayout) root).setDividerPadding(10);
                ((LinearLayout) root).setDividerDrawable(drawable);
            }
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            RelativeLayout relativeLayout = (RelativeLayout)
//                    LayoutInflater.from(getActivity()).inflate(R.layout.view_custom_tab, tabLayout, false);
//
//            TextView tabTextView = relativeLayout.findViewById(R.id.tab_title);
//            tabTextView.setText(tab.getText());
//            tab.setCustomView(relativeLayout);
//            tab.select();
        }

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
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
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                    //tabLayout.setBackground();
                }
               else if(tab.getPosition()== 1) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
                if(tab.getPosition()== 2) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
                else if(tab.getPosition()== 3) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
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
