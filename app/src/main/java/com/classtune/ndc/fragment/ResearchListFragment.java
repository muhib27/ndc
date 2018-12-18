package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.classtune.ndc.adapter.CMListAdapter;
import com.classtune.ndc.adapter.ResearchListAdapter;
import com.classtune.ndc.apiresponse.NoticeApi.Notice;
import com.classtune.ndc.apiresponse.NoticeApi.NoticeResponseModel;
import com.classtune.ndc.apiresponse.research_api.Research;
import com.classtune.ndc.apiresponse.research_api.ResearchWingResponseModel;
import com.classtune.ndc.model.ResearcherModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
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
public class ResearchListFragment extends Fragment {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Research> strList = new ArrayList<>();
    ResearchListAdapter researchListAdapter;
    UIHelper uiHelper;


    public ResearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        uiHelper = new UIHelper(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String cmType = bundle.getString("cmType");
        }

        rv = (RecyclerView) view.findViewById(R.id.main_recycler);
       // strList = getStrList();
        researchListAdapter = new ResearchListAdapter(getContext(), strList);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(researchListAdapter);
        callResearchListApi();
    }


    private ArrayList<ResearcherModel> getStrList() {
        ArrayList<ResearcherModel> list = new ArrayList<>();
        ResearcherModel researcherModel = new ResearcherModel("Brig Gen Monirul Islam Akhand, ndc, psc","ndc1@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Col Salahuddin Khaled","ndc2@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Col Mohammad Shahriar Zaman, afwc, psc, G, Arty","ndc3@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col S M Merazul Islam, afwc, psc, Engr","ndc4@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col Syed Jamil Ahsan, afwc, psc, AC","ndc5@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("LT Col Md Kamrul Islam, BGBM, psc, Arty","ndc6@ndc.gov.bd");
        list.add(researcherModel);
        researcherModel = new ResearcherModel("Lt Col A S M Badiul Alam, afwc, psc, Arty","ndc7@ndc.gov.bd");
        list.add(researcherModel);

        return list;
    }

    private void callResearchListApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getResearchWingList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResearchWingResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResearchWingResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        ResearchWingResponseModel researchWingResponseModel = value.body();

                        if(researchWingResponseModel != null && researchWingResponseModel.getCode()!=null) {
                            if (researchWingResponseModel.getCode() == 200) {
                                Log.v("researchWingRespon", value.message());
                                List<Research> researchList = researchWingResponseModel.getResearchWingData().getResearch();
                                Collections.reverse(researchList);
                                researchListAdapter.clear();
                                researchListAdapter.addAllData(researchList);
                                Log.v("tt", researchList.toString());
                            } else if (researchWingResponseModel.getCode() == 500) {
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

//    private void gotoPigeonholeFragment() {
//        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);;
//        transaction.commit();
//    }
}
