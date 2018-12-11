package com.classtune.ndc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.classtune.ndc.fragment.CMBoxFragment;
import com.classtune.ndc.fragment.CourseCalendarParentFragment;
import com.classtune.ndc.fragment.EventFragment;
import com.classtune.ndc.fragment.HomeFragment;
import com.classtune.ndc.fragment.NoticeFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;
import com.classtune.ndc.fragment.RoutineBlueFragment;
import com.classtune.ndc.fragment.RoutineWhiteFragment;
import com.classtune.ndc.fragment.RoutineYellowFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new HomeFragment();
        }
        else if (position == 1)
        {
            fragment = new PigeonholeFragment();
        }
        else if (position == 2)
        {
            fragment = new CMBoxFragment();
        }
        else if (position == 3)
        {
            fragment = new NoticeFragment();
        }
        else if (position == 4)
        {
            fragment = new CourseCalendarParentFragment();
        }
        else if (position == 5)
        {
            fragment = new EventFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Home";
        }
        else if (position == 1)
        {
            title = "Pigeonhole";
        }
        else if (position == 2)
        {
            title = "CM Box";
        }
        else if (position == 3)
        {
            title = "Notice";
        }
        else if (position == 4)
        {
            title = "Course Calendar";
        }
        else if (position == 5)
        {
            title = "Events";
        }
        return title;
    }
}
