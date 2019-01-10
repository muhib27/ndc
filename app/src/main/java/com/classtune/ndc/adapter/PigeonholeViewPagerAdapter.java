package com.classtune.ndc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.classtune.ndc.fragment.CMBoxFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;
import com.classtune.ndc.fragment.RoutineBlueFragment;
import com.classtune.ndc.fragment.RoutineWhiteFragment;
import com.classtune.ndc.fragment.RoutineYellowFragment;

public class PigeonholeViewPagerAdapter extends  FragmentStatePagerAdapter {
    String tabOne = "";
    String tabTwo = "";

    public PigeonholeViewPagerAdapter(FragmentManager fm, String tabOne, String tabTwo) {
        super(fm);
        this.tabOne = tabOne;
        this.tabTwo = tabTwo;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
//        if (position == 0)
//        {
//            fragment = new EventsFragment();
//        }
//        else
        if (position == 0)
        {
            fragment = new PigeonholeFragment();
        }
        else if (position == 1)
        {
            fragment = new CMBoxFragment();
        }
//        else if (position == 2)
//        {
//            fragment = new RoutineBlueFragment();
//        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
//        if (position == 0)
//        {
//            title = "Event";
//        }
//        else
        if (position == 0)
        {
            title = tabOne;
        }
        else if (position == 1)
        {
            title = tabTwo;
        }
//        else if (position == 2)
//        {
//            title = "Blue";
//        }
        return title;
    }
}
