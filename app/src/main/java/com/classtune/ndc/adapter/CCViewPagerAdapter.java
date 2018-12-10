package com.classtune.ndc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.classtune.ndc.fragment.EventFragment;
import com.classtune.ndc.fragment.RoutineBlueFragment;
import com.classtune.ndc.fragment.RoutineWhiteFragment;
import com.classtune.ndc.fragment.RoutineYellowFragment;

public class CCViewPagerAdapter extends FragmentPagerAdapter {

    public CCViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new EventFragment();
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
            title = "Tab-1";
        }
        else if (position == 1)
        {
            title = "Tab-2";
        }
        else if (position == 2)
        {
            title = "Tab-3";
        }
        else if (position == 3)
        {
            title = "Tab-4";
        }
        return title;
    }
}
