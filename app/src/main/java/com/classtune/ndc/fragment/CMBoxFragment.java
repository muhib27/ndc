package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.CMBoxAdapter;
import com.classtune.ndc.apiresponse.CMBox.CMBoxSubmittedTask;
import com.classtune.ndc.apiresponse.CMBox.CMBoxSubmittedTaskResponse;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.PaginationAdapterCallback;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;
import com.classtune.ndc.viewhelpers.UIHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CMBoxFragment extends Fragment implements PaginationAdapterCallback, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> strList = new ArrayList<>();
    CMBoxAdapter cmBoxAdapter;
    FloatingActionButton pigeonholeFab;
    UIHelper uiHelper;


    public CMBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cm_box, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        uiHelper = new UIHelper(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        rv = (RecyclerView) view.findViewById(R.id.main_recycler);
//        pigeonholeFab = (FloatingActionButton) view.findViewById(R.id.pigeonhole_fab);
//        pigeonholeFab.setOnClickListener(this);

//        UserPermission userPermission = AppSharedPreference.getUserPermission();
//        if(userPermission.isTasksAdd())
//            pigeonholeFab.setVisibility(View.VISIBLE);
//        else
//            pigeonholeFab.setVisibility(View.INVISIBLE);

        strList = getStrList();


        cmBoxAdapter = new CMBoxAdapter(getContext());


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
//                linearLayoutManager.getOrientation());
//        rv.addItemDecoration(dividerItemDecoration);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(cmBoxAdapter);
        //cmBoxAdapter.addAllData(strList);

        callCMBoxListApi();


    }


    private void callCMBoxListApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getMyCMBoxList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CMBoxSubmittedTaskResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CMBoxSubmittedTaskResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        CMBoxSubmittedTaskResponse cmBoxSubmittedTaskResponse = value.body();

                        if(cmBoxSubmittedTaskResponse != null && cmBoxSubmittedTaskResponse.getCode()!=null) {
                            if (cmBoxSubmittedTaskResponse.getCode() == 200) {
                                Log.v("cmBoxSubmittedTaskRes", value.message());
                                List<CMBoxSubmittedTask> cmBoxSubmittedTasks = cmBoxSubmittedTaskResponse.getCmBoxData().getCmBoxSubmittedTasks();
                                //Collections.reverse(cmBoxSubmittedTasks);
                                if(cmBoxSubmittedTasks!=null)
                                cmBoxAdapter.addAllData(cmBoxSubmittedTasks);
                              //  Log.v("tt", cmBoxSubmittedTasks.toString());
                            } else if (cmBoxSubmittedTaskResponse.getCode() == 500) {
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                        }

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


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.pigeonhole_fab:
//                gotoInstructorTaskAssignFragment();
//                break;
        }
    }

    private void gotoInstructorTaskAssignFragment() {
        InsTructorTaskAssignFragment insTructorTaskAssignFragment = new InsTructorTaskAssignFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, insTructorTaskAssignFragment, "insTructorTaskAssignFragment").addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void retryPageLoad() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList<String> getStrList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");
        list.add("Lorem ipsum dolor sit amet");

        return list;
    }
}
