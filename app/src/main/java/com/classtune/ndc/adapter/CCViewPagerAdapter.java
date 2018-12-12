package com.classtune.ndc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.classtune.ndc.fragment.EventsFragment;
import com.classtune.ndc.fragment.RoutineBlueFragment;
import com.classtune.ndc.fragment.RoutineWhiteFragment;
import com.classtune.ndc.fragment.RoutineYellowFragment;

public class CCViewPagerAdapter extends FragmentStatePagerAdapter {

    public CCViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new EventsFragment();
        }
        else if (position == 1)
        {
            fragment = new RoutineWhiteFragment();
        }
        else if (position == 2)
        {
            fragment = new RoutineYellowFragment();
        }
        else if (position == 3)
        {
            fragment = new RoutineBlueFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Event";
        }
        else if (position == 1)
        {
            title = "White";
        }
        else if (position == 2)
        {
            title = "Yellow";
        }
        else if (position == 3)
        {
            title = "Blue";
        }
        return title;
    }
}
