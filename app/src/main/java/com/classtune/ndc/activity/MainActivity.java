package com.classtune.ndc.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.classtune.ndc.R;
import com.classtune.ndc.fragment.DashBoardFragment;
import com.classtune.ndc.fragment.NoticeFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.DrawerLocker;
import com.classtune.ndc.utils.NetworkConnection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    NavigationView navigationView;
    public static ActionBarDrawerToggle toggle;
    public static DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSharedPreference.setUsingFirstTime(false);
        showToolbar();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        gotoDashboardFragment();


        // load();
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


//    private void showToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //mActionBar = getSupportActionBar();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        //drawer.setDrawerListener(toggle);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        final View.OnClickListener originalToolbarListener = toggle.getToolbarNavigationClickListener();
//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    toggle.setDrawerIndicatorEnabled(false);
//                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //getFragmentManager().popBackStack();
//                            NoticeFragment noticeFragment = (NoticeFragment) getSupportFragmentManager().findFragmentByTag("noticeFragment");
//                            if (noticeFragment != null && noticeFragment.isVisible()) {
////                                reportFragment.getNextItem("prev");
//                            }
//                        }
//                    });
//                } else {
//                    //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                    toggle.setDrawerIndicatorEnabled(true);
//                    toggle.setToolbarNavigationClickListener(originalToolbarListener);
////                    toggle.syncState();
//                }
//            }
//        });
//    }

    private void showToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //mActionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final View.OnClickListener originalToolbarListener = toggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStack = getSupportFragmentManager().getBackStackEntryCount();
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toggle.setDrawerIndicatorEnabled(false);
                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();

                        }
                    });
                } else {
                    setDrawerEnabled(true);
                    getSupportActionBar().setTitle("");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.setDrawerIndicatorEnabled(true);
//                    toggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
                    toggle.setToolbarNavigationClickListener(originalToolbarListener);
                    toggle.syncState();
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //getSupportFragmentManager().popBackStack();
            DashBoardFragment dashBoardFragment = (DashBoardFragment) getSupportFragmentManager().findFragmentByTag("dashBoardFragment");
            NoticeFragment noticeFragment = (NoticeFragment) getSupportFragmentManager().findFragmentByTag("noticeFragment");
//            SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("settingsFragment");
            if (noticeFragment != null && noticeFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //gotoDashboardFragment();
            }
//            else if((settingsFragment != null && settingsFragment.isVisible())){
//                Log.v("fdddddddddd", "dffffffffffff");
//            }
//            else if ((orderDetailsFragment != null && orderDetailsFragment.isVisible())   || (searchResultFragment != null && searchResultFragment.isVisible())) {
//                getSupportFragmentManager().popBackStack();
//                goesToHomeFragment("");
//            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
//            SELECTED_PAGE = "Today";
            super.onBackPressed();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    //getSupportFragmentManager().popBackStack();
                    DashBoardFragment dashBoardFragment = (DashBoardFragment) getSupportFragmentManager().findFragmentByTag("dashBoardFragment");
                    NoticeFragment noticeFragment = (NoticeFragment) getSupportFragmentManager().findFragmentByTag("noticeFragment");
//            SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("settingsFragment");
                    if (noticeFragment != null && noticeFragment.isVisible()) {
                        getSupportFragmentManager().popBackStack();
                        toggle.setDrawerIndicatorEnabled(true);
                        //gotoDashboardFragment();
                    }
//            else if((settingsFragment != null && settingsFragment.isVisible())){
//                Log.v("fdddddddddd", "dffffffffffff");
//            }
//            else if ((orderDetailsFragment != null && orderDetailsFragment.isVisible())   || (searchResultFragment != null && searchResultFragment.isVisible())) {
//                getSupportFragmentManager().popBackStack();
//                goesToHomeFragment("");
//            }
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
//            SELECTED_PAGE = "Today";
            super.onBackPressed();
                }
                return true;
//            case R.id.settins:
//                gotoSettingsFragment();
//                return true;
//
        }


        return super.onOptionsItemSelected(item);
    }


    private void load() {
        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_pigeonhole) {
            gotoPigeonholeFragment();

        } else if (id == R.id.nav_notice) {
            gotoNoticeFragment();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void gotoDashboardFragment() {
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, dashBoardFragment, "dashBoardFragment");
        transaction.commit();
    }

    private void gotoNoticeFragment() {
        NoticeFragment noticeFragment = new NoticeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, noticeFragment, "noticeFragment").addToBackStack(null);;
        transaction.commit();
    }
    private void gotoPigeonholeFragment() {
        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);;
        transaction.commit();
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }
}
