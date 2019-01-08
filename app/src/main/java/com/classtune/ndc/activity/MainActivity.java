package com.classtune.ndc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.ndc.R;
import com.classtune.ndc.adapter.MainViewPagerAdapter;
import com.classtune.ndc.apiresponse.menu_api.User;
import com.classtune.ndc.apiresponse.menu_api.UserMenu;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTaskData;
import com.classtune.ndc.fragment.CMBoxDetailsFragment;
import com.classtune.ndc.fragment.CMBoxFragment;
import com.classtune.ndc.fragment.CMSubmitTaskDetailsFragment;
import com.classtune.ndc.fragment.CourseCalendarParentFragment;
import com.classtune.ndc.fragment.DashBoardFragment;
import com.classtune.ndc.fragment.EventsFragment;
import com.classtune.ndc.fragment.EventsFragment_old;
import com.classtune.ndc.fragment.HomeFragment;
import com.classtune.ndc.fragment.InstructorDetailsFragment;
import com.classtune.ndc.fragment.NoticeDetailsFragment;
import com.classtune.ndc.fragment.NoticeFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;
import com.classtune.ndc.fragment.ProfileFragment;
import com.classtune.ndc.fragment.ReadingPackageFragment;
import com.classtune.ndc.fragment.ResearchListFragment;
import com.classtune.ndc.fragment.ResearchTopicListFragment;
import com.classtune.ndc.fragment.RoutineWhiteFragment;
import com.classtune.ndc.fragment.SubmissionTrayFragment;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.DrawerLocker;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.URLHelper;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    NavigationView navigationView;
    public static ActionBarDrawerToggle toggle;
    public static DrawerLayout drawer;
    Toolbar toolbar;
    UIHelper uiHelper;
    CircleImageView userImage;
    TextView userName, userEmail;
    User user;
    TabLayout tabLayout;
    ViewPager viewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    UserPermission userPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPermission = AppSharedPreference.getUserPermission();


        AppSharedPreference.setUsingFirstTime(false);
        showToolbar();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(mainViewPagerAdapter);


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("pigeonhole"));
        if (userPermission.isUserTasksSubmitTask())
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.submission_tray_tab).setTag("submission_tray"));
        else
            hideItem();
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cm_box).setTag("cm_box"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.reading_package).setTag("reading_package"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.notice_tab_).setTag("notice"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cc_tab_).setTag("course_calendar"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.social_event).setTag("events"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.research_tab_).setTag("research_icon"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile_tab_).setTag("profile"));
//        tabLayout.addTab(tabLayout.newTab().setTag("tab"));


//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
//        }
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                if (tab.getTag().equals("pigeonhole")) {
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    gotoPigeonholeFragment();
                } else if (userPermission.isUserTasksSubmitTask() && tab.getTag().equals("submission_tray")) {
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    gotoSubmissionTrayFragment();
                } else if (tab.getTag().equals("cm_box")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoCMBoxFragment();
                } else if (tab.getTag().equals("reading_package")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoReadingPackageFragment();
                } else if (tab.getTag().equals("notice")) {
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoNoticeFragment("");
                } else if (tab.getTag().equals("course_calendar")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoClassScheduleFragment();
                } else if (tab.getTag().equals("events")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoEventsFragment();
                } else if (tab.getTag().equals("research_icon")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    UserMenu userMenu = AppSharedPreference.getUserMenu();
                    if (userMenu.isResearch())
                        gotoResearchTopicFragment();
                    else
                        gotoResearchListFragment();
                } else if (tab.getTag().equals("profile")) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                    // Toast.makeText(MainActivity.this, tab.getTag().toString() + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    gotoProfileFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Toast.makeText(getApplicationContext(), tab.getPosition(), Toast.LENGTH_SHORT).show();
            }
        });

//        tabLayout.removeTabAt(7);

//        tabLayout.setupWithViewPager(viewPager);


        uiHelper = new UIHelper(MainActivity.this);
//
        user = AppSharedPreference.getUserBasicInfo();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);

//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userImage = hView.findViewById(R.id.userImage);
        userName = hView.findViewById(R.id.userName);
        userEmail = hView.findViewById(R.id.userEmail);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        loadImage();

        Bundle extras = getIntent().getExtras();

        String orderId = "";
//        gotoPigeonholeFragment();
        if (extras != null && !extras.isEmpty()) {

            String type = "";
            String id = "";
            if (extras.getString("target_type") != null)
                type = extras.getString("target_type");
            if (extras.getString("target_id") != null)
                id = extras.getString("target_id");

            if (type.equals("3")) {
                gotoNoticeDetailsFragment(id);
            } else if (type.equals("1")) {
                gotoInstructorDetailsFragment(id);
            } else if (type.equals("2")) {
                gotoCMSubmitTaskDetailsFragment(id);
            } else if (type.equals("4")) {
                gotoReadingPackageFragmentNotify(id);
            }
        } else
            gotoPigeonholeFragment();

        // load();
    }

    private void gotoNoticeDetailsFragment(String id) {
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        tab.select();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        NoticeDetailsFragment noticeDetailsFragment = new NoticeDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeDetailsFragment, "noticeDetailsFragment");
        transaction.commit();
    }

    private void gotoReadingPackageFragmentNotify(String id) {
        Bundle bundle = new Bundle();
        if (id != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(4);
            tab.select();

            bundle.putString("id", id);
        }

        setUpBackStackCountToZero();
        ReadingPackageFragment readingPackageFragment = new ReadingPackageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        readingPackageFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, readingPackageFragment, "readingPackageFragment");
        transaction.commit();
    }

    private void gotoInstructorDetailsFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        InstructorDetailsFragment instructorDetailsFragment = new InstructorDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        instructorDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, instructorDetailsFragment, "instructorDetailsFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoCMSubmitTaskDetailsFragment(String id) {
        Bundle bundle = new Bundle();
        if (id != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();

            bundle.putString("id", id);
        }
        CMBoxDetailsFragment cmBoxDetailsFragment = new CMBoxDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmBoxDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmBoxDetailsFragment, "cmBoxDetailsFragment").addToBackStack(null);
        transaction.commit();
    }

    private void loadImage() {
        Glide
                .with(this)
                .load(URLHelper.BASE_URL + user.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .fitCenter())
                .into(userImage);

//        return Glide
//                .with(getApplicationContext())
//                .load(user.getImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
//                .centerCrop()
//                .crossFade();
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
        getSupportActionBar().setTitle("");


        ImageView iv = findViewById(R.id.logoXmarks);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "fds", Toast.LENGTH_LONG).show();
//                DashBoardFragment dashBoardFragment = (DashBoardFragment) getSupportFragmentManager().findFragmentByTag("dashBoardFragment");
                PigeonholeFragment pigeonholeFragment = (PigeonholeFragment) getSupportFragmentManager().findFragmentByTag("pigeonholeFragment");
                if (pigeonholeFragment != null && pigeonholeFragment.isVisible())
                    return;
                else {
                    //gotoDashboardFragment();
                    gotoPigeonholeFragment();
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                }
            }
        });
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
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
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
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
                InstructorDetailsFragment instructorDetailsFragment = (InstructorDetailsFragment) getSupportFragmentManager().findFragmentByTag("instructorDetailsFragment");
                CMSubmitTaskDetailsFragment cmSubmitTaskDetailsFragment = (CMSubmitTaskDetailsFragment) getSupportFragmentManager().findFragmentByTag("cmSubmitTaskDetailsFragment");
                if ((instructorDetailsFragment != null && instructorDetailsFragment.isVisible()) || cmSubmitTaskDetailsFragment != null && cmSubmitTaskDetailsFragment.isVisible()) {
                    getSupportFragmentManager().popBackStack();
                    gotoPigeonholeFragment();
                    return;
                }


            }
            getSupportFragmentManager().popBackStack();
//            DashBoardFragment dashBoardFragment = (DashBoardFragment) getSupportFragmentManager().findFragmentByTag("dashBoardFragment");
//            NoticeFragment noticeFragment = (NoticeFragment) getSupportFragmentManager().findFragmentByTag("noticeFragment");
//            if (noticeFragment != null && noticeFragment.isVisible()) {
//                getSupportFragmentManager().popBackStack();
//                toggle.setDrawerIndicatorEnabled(true);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//            }

//            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
//            SELECTED_PAGE = "Today";
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
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

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else
//        if (id == R.id.nav_home) {
//
//            gotoDashboardFragment();
//
//        } else
        if (id == R.id.nav_pigeonhole) {

            //gotoPigeonholeFragment();
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();

        } else if (id == R.id.nav_submission_tray) {

            //gotoPigeonholeFragment();
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();

        } else if (id == R.id.nav_cm_box) {
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 2;
            else
                index = 1;

            //gotoCMBoxFragment();
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        }
        else if (id == R.id.nav_reading_package) {
            //gotoReadingPackageFragment();
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 3;
            else
                index = 2;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        }
        else if (id == R.id.nav_notice) {
            //gotoNoticeFragment("");
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 4;
            else
                index = 3;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        } else if (id == R.id.nav_course_calendar) {
            //gotoClassScheduleFragment();
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 5;
            else
                index = 4;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        } else if (id == R.id.nav_events) {
            //gotoEventsFragment();
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 6;
            else
                index = 5;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        } else if (id == R.id.nav_research) {
            //gotoResearchListFragment();
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 7;
            else
                index = 6;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();

        } else if (id == R.id.nav_profile) {
            //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
            //gotoProfileFragment();
            int index;
            if (userPermission.isUserTasksSubmitTask())
                index = 8;
            else
                index = 7;
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            tab.select();
        } else if (id == R.id.nav_logout) {
            callLogOutApi();
        }
//        else if (id == R.id.nav_reading_package) {
//            return false;
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void userLogout() {
        User user = new User("", "", "", "");
        AppSharedPreference.setUserBasicInfo(user);
        if (AppSharedPreference.getRememberMe()) {
            AppSharedPreference.setUserNameAndPassword(AppSharedPreference.getUserName(), AppSharedPreference.getUserPassword(), "", true);

        } else {
            AppSharedPreference.setUserNameAndPassword("", "", "", false);
        }
        AppSharedPreference.setUsingFirstTime(true);
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void callLogOutApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getLogout(AppSharedPreference.getApiKey(), AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();

                        if (value.code() == 200)
                            userLogout();

                    }


                    @Override
                    public void onError(Throwable e) {

//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });
    }


    private void gotoDashboardFragment() {
        setUpBackStackCountToZero();
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, homeFragment, "homeFragment");
        transaction.commit();
//        DashBoardFragment dashBoardFragment = new DashBoardFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, dashBoardFragment, "dashBoardFragment");
//        transaction.commit();
    }

    private void gotoNoticeFragment(String id) {
//        TabLayout.Tab tab = tabLayout.getTabAt(3);
//        tab.select();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putString("id", id);
        }

        setUpBackStackCountToZero();
        NoticeFragment noticeFragment = new NoticeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeFragment, "noticeFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoReadingPackageFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(5);
//        tab.select();
        Bundle bundle = new Bundle();
        bundle.putString("type", "0");
        bundle.putString("id", "0");
//        if(id!=null) {
//            TabLayout.Tab tab = tabLayout.getTabAt(3);
//            tab.select();
//
//            bundle.putString("id", id);
//        }

        setUpBackStackCountToZero();
        ReadingPackageFragment readingPackageFragment = new ReadingPackageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        readingPackageFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, readingPackageFragment, "readingPackageFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoClassScheduleFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(2);
//        tab.select();
        setUpBackStackCountToZero();

        RoutineWhiteFragment routineWhiteFragment = new RoutineWhiteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, routineWhiteFragment, "routineWhiteFragment").addToBackStack(null);
        transaction.commit();
//        CourseCalendarParentFragment courseCalendarParentFragment = new CourseCalendarParentFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, courseCalendarParentFragment, "courseCalendarParentFragment").addToBackStack(null);
//        transaction.commit();
    }

    private void gotoPigeonholeFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(0);
//        tab.select();
        setUpBackStackCountToZero();
        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (userPermission.isUserTasksSubmitTask())
        transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment");
//        else
//            transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoSubmissionTrayFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(0);
//        tab.select();
        setUpBackStackCountToZero();
        SubmissionTrayFragment submissionTrayFragment = new SubmissionTrayFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, submissionTrayFragment, "submissionTrayFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoCMBoxFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(1);
//        tab.select();
        setUpBackStackCountToZero();
        CMBoxFragment cmBoxFragment = new CMBoxFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, cmBoxFragment, "cmBoxFragment").addToBackStack(null);
        transaction.commit();
    }

    private void setUpBackStackCountToZero() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            while (count > 0) {
                getSupportFragmentManager().popBackStack();
                count--;
            }
        }
    }

    private void gotoEventsFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(4);
//        tab.select();
        setUpBackStackCountToZero();
        EventsFragment eventsFragment = new EventsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, eventsFragment, "eventsFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoResearchListFragment() {
//        TabLayout.Tab tab = tabLayout.getTabAt(6);
//        tab.select();
        setUpBackStackCountToZero();
        ResearchListFragment researchListFragment = new ResearchListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, researchListFragment, "researchListFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoResearchTopicFragment() {
        setUpBackStackCountToZero();
        ResearchTopicListFragment researchTopicListFragment = new ResearchTopicListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, researchTopicListFragment, "researchTopicListFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoProfileFragment() {
//        View view=tabLayout.getTabAt(7).getCustomView();
//        tabLayout.addTab(tabLayout.newTab().setCustomView(view));
//        TabLayout.Tab tab = tabLayout.getTabAt(7);
//        tab.select();
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#74af27"));
        setUpBackStackCountToZero();
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, profileFragment, "profileFragment").addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_submission_tray).setVisible(false);
    }
}
