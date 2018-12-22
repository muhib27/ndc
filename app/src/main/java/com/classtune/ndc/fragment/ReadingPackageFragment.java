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
import com.classtune.ndc.adapter.DashboardNoticeAdapter;
import com.classtune.ndc.adapter.ReadingPackageAdapter;
import com.classtune.ndc.apiresponse.NoticeApi.Notice;
import com.classtune.ndc.apiresponse.NoticeApi.NoticeResponseModel;
import com.classtune.ndc.apiresponse.reading_package.RPResponseModel;
import com.classtune.ndc.apiresponse.reading_package.ReadingList;
import com.classtune.ndc.apiresponse.reading_package.ReadingPackageData;
import com.classtune.ndc.model.NoticeModel;
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
public class ReadingPackageFragment extends Fragment implements PaginationAdapterCallback, View.OnClickListener{
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<NoticeModel> strList = new ArrayList<>();
    ReadingPackageAdapter readingPackageAdapter;
    FloatingActionButton floatingActionButton;
    UIHelper uiHelper;
    String id = "";
    String type = "";


    public ReadingPackageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_package, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }


        uiHelper = new UIHelper(getActivity());
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        mSwipeRefreshLayout.setRefreshing(false);
//        mSwipeRefreshLayout.post(new Runnable() {
//
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
            if(b.getString("type", "")!=null)
                type = b.getString("type", "");

        }
        initNoticeView(view);
        if((id!=null && !id.isEmpty() )&& (type!=null && !type.isEmpty())){
            //gotoNoticeDetailsFragment(id);
            callApi(type, id);
        }
        else {


        }
    }

    private void gotoNoticeDetailsFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        NoticeDetailsFragment noticeDetailsFragment = new NoticeDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeDetailsFragment, "noticeDetailsFragment").addToBackStack(null);
        transaction.commit();
    }

    private void initNoticeView(View view) {
//        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.notice_fab);
//        floatingActionButton.setOnClickListener(this);

        rv = (RecyclerView) view.findViewById(R.id.notice_recycleview);
//        important = view.findViewById(R.id.impotant);
//        recent = view.findViewById(R.id.recent);
//        common = view.findViewById(R.id.common);
//        recent.setOnClickListener(this);

        strList = getStrList();
        readingPackageAdapter = new ReadingPackageAdapter(getContext(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(readingPackageAdapter);
        //dashboardNoticeAdapter.setData(getStrList());
        readingPackageAdapter.notifyDataSetChanged();
    }

//
//    @Override
//    public void onRefresh() {
//        mSwipeRefreshLayout.setRefreshing(false);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.notice_fab:
//                gotoInstructorNoticeCreateFragment();
//                break;
        }
    }
    private void callApi( String parentId, String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getReadingList(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RPResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<RPResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        RPResponseModel rpResponseModel = value.body();

                        if(rpResponseModel != null && rpResponseModel.getCode()!=null) {
                            if (rpResponseModel.getCode() == 200) {
//                                Log.v("noticeResponseModel", value.message());
                                List<ReadingList> readingList = rpResponseModel.getReadingPackageData().getReadingList();
//                                Collections.reverse(readingList);
                                readingPackageAdapter.addAllData(readingList);
                                Log.v("tt", readingList.toString());
                            } else if (rpResponseModel.getCode() == 500) {
                                //Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
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



    private void gotoInstructorNoticeCreateFragment() {
        NoticeAddFragment noticeAddFragment = new NoticeAddFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, noticeAddFragment, "noticeAddFragment").addToBackStack(null);;
        transaction.commit();
    }

    private ArrayList<NoticeModel> getStrList() {
        ArrayList<NoticeModel> list = new ArrayList<>();
        NoticeModel noticeModel = new NoticeModel("JOINT SESSION AT NDC", "Commandant, NDC, Lieutenant General Sheikh Mamun Khaled, SUP, rcds, psc, PhD delivered a lecture on Essential Leadership Qualities in the VUCA World");
        list.add(noticeModel);
        noticeModel = new NoticeModel("VISIT by Secretary General of IMCTC to NDC", "Lieutenant General Abdul Elah bin Othman Al Salah, Secretary General of Islamic Coalition for Terrorism (IMCTC)  visited NDC on 14 November 2018 for delivering an opportunity lecture on ``IMCTC and its contribution to the peace and Security in the Middle East'' to ND Course 2018. Commandant, Faculty, Staff Officers and Course members of ND Course-2018 attended the Session.");
        list.add(noticeModel);
        noticeModel = new NoticeModel("LECTURE TO ND COURSE", "Chief of Army Staff, General Aziz Ahmed, BGBM, PBGM, BGBMS, psc, G, delivered a keynote speech on Bangladesh Army to ND course 2018 and AFWC 2018 on 27 November 2018 at NDC. Commandant, Faculty, Staff Officers and all course members of 2018 attended the session.");
        list.add(noticeModel);
        noticeModel = new NoticeModel("The National Mourning Day", "NDC observed The National Mourning Day On 15 August 2018 with due Reverence and Solemnity, marking the 43rd Anniversary of the assassination of Father of the Nation Bangabandhu Sheikh Mujibur Rahman.");
        list.add(noticeModel);
        return list;

    }

    @Override
    public void retryPageLoad() {


    }
}
