package com.classtune.ndc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.classtune.ndc.fragment.AllAssignmentFragment;
import com.classtune.ndc.fragment.AllCMSubmissionFragment;
import com.classtune.ndc.fragment.CMBoxFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;

public class PigeonholeViewPagerAdapter extends  FragmentStatePagerAdapter {
    String tabOne = "";
    String tabTwo = "";
    String tabThree = "";
    String tabFour = "";

    public PigeonholeViewPagerAdapter(FragmentManager fm, String tabOne, String tabTwo, String tabThree, String tabFour) {
        super(fm);
        this.tabOne = tabOne;
        this.tabTwo = tabTwo;
        this.tabThree = tabThree;
        this.tabFour = tabFour;
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
            fragment = new AllAssignmentFragment();

        }
        else if (position == 2)
        {
            fragment = new CMBoxFragment();
        }
        else if (position == 3)
        {
            fragment = new AllCMSubmissionFragment();
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
        else if (position == 2)
        {
            title = tabThree;
        }
        else if (position == 3)
        {
            title = tabFour;
        }
        return title;
    }
}
