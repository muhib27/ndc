package com.classtune.ndc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.menu_api.MenuApiResponse;
import com.classtune.ndc.apiresponse.menu_api.User;
import com.classtune.ndc.fragment.ClassScheduleFragment;
import com.classtune.ndc.fragment.DashBoardFragment;
import com.classtune.ndc.fragment.EventsFragment;
import com.classtune.ndc.fragment.NoticeFragment;
import com.classtune.ndc.fragment.PigeonholeFragment;
import com.classtune.ndc.fragment.ProfileFragment;
import com.classtune.ndc.fragment.ResearchListFragment;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.DrawerLocker;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.URLHelper;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;
import com.vincent.filepicker.activity.ImagePickActivity;

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
        uiHelper = new UIHelper(MainActivity.this);
//
        user = AppSharedPreference.getUserBasicInfo();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);

//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userImage = hView.findViewById(R.id.userImage);
        userName = hView.findViewById(R.id.userName);
        userEmail = hView.findViewById(R.id.userEmail);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        loadImage();

        gotoDashboardFragment();


        // load();
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
        if (id == R.id.nav_pigeonhole) {

            gotoPigeonholeFragment();

        } else if (id == R.id.nav_notice) {
            gotoNoticeFragment();

        } else if (id == R.id.nav_manage) {
            gotoClassScheduleFragment();

        } else if (id == R.id.nav_events) {
            gotoEventsFragment();

        } else if (id == R.id.nav_research) {
            gotoResearchListFragment();

        } else if (id == R.id.nav_profile) {
            gotoProfileFragment();
        } else if (id == R.id.nav_logout) {
            callLogOutApi();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userLogout() {
        User user = new User("", "", "", "");
        AppSharedPreference.setUserBasicInfo(user);
        AppSharedPreference.setUserNameAndPassword("", "", "");
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
//        uiHelper.showLoadingDialog("Authenticating...");



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

                        if(value.code()==200)
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
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, dashBoardFragment, "dashBoardFragment");
        transaction.commit();
    }

    private void gotoNoticeFragment() {
        setUpBackStackCountToZero();
        NoticeFragment noticeFragment = new NoticeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, noticeFragment, "noticeFragment").addToBackStack(null);
        ;
        transaction.commit();
    }

    private void gotoClassScheduleFragment() {
        setUpBackStackCountToZero();
        ClassScheduleFragment classScheduleFragment = new ClassScheduleFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, classScheduleFragment, "classScheduleFragment").addToBackStack(null);
        ;
        transaction.commit();
    }

    private void gotoPigeonholeFragment() {
        setUpBackStackCountToZero();
        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);
        ;
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
        setUpBackStackCountToZero();
        EventsFragment eventsFragment = new EventsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, eventsFragment, "eventsFragment").addToBackStack(null);
        ;
        transaction.commit();
    }

    private void gotoResearchListFragment() {
        setUpBackStackCountToZero();
        ResearchListFragment researchListFragment = new ResearchListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, researchListFragment, "researchListFragment").addToBackStack(null);
        ;
        transaction.commit();
    }

    private void gotoProfileFragment() {
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

//    private void hideItem()
//    {
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_settings).setVisible(false);
//    }
}
