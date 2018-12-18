package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.classtune.ndc.adapter.ResearchListAdapter;
import com.classtune.ndc.adapter.ResearchTopicListAdapter;
import com.classtune.ndc.apiresponse.research_api.Research;
import com.classtune.ndc.apiresponse.research_api.ResearchTopicResponseModel;
import com.classtune.ndc.apiresponse.research_api.ResearchWingResponseModel;
import com.classtune.ndc.apiresponse.research_api.UserResearchTopic;
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
public class ResearchTopicListFragment extends Fragment {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<UserResearchTopic> strList = new ArrayList<>();
    ResearchTopicListAdapter researchTopicListAdapter;
    UIHelper uiHelper;
    String id = "";


    public ResearchTopicListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_list_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
        }
        uiHelper = new UIHelper(getActivity());
        //initView(view);
        if (id != null && !id.isEmpty())
            callTopicListApi(id);
        else {
            callTopicListApi();
        }
//
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            String cmType = bundle.getString("cmType");
//        }

        rv = (RecyclerView) view.findViewById(R.id.main_recycler);
        //strList = getStrList();
        researchTopicListAdapter = new ResearchTopicListAdapter(getContext(), strList);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(researchTopicListAdapter);
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

    private void callTopicListApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getResearchTopicList(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResearchTopicResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResearchTopicResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        ResearchTopicResponseModel researchTopicResponseModel = value.body();

                        if(researchTopicResponseModel != null && researchTopicResponseModel.getCode()!=null) {
                            if (researchTopicResponseModel.getCode() == 200) {
                                Log.v("researchTopicRespons", value.message());
                                List<UserResearchTopic> userResearchTopics = researchTopicResponseModel.getResearchTopicData().getUserResearchTopics();
                                Collections.reverse(userResearchTopics);
                                researchTopicListAdapter.clear();
                                researchTopicListAdapter.addAllData(userResearchTopics);
                                Log.v("tt", userResearchTopics.toString());
                            } else if (researchTopicResponseModel.getCode() == 500) {
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

    private void callTopicListApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getCMResearch(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResearchTopicResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResearchTopicResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        ResearchTopicResponseModel researchTopicResponseModel = value.body();

                        if(researchTopicResponseModel != null && researchTopicResponseModel.getCode()!=null) {
                            if (researchTopicResponseModel.getCode() == 200) {
                                Log.v("researchTopicRespons", value.message());
                                List<UserResearchTopic> userResearchTopics = researchTopicResponseModel.getResearchTopicData().getUserResearchTopics();
                                Collections.reverse(userResearchTopics);
                                researchTopicListAdapter.clear();
                                researchTopicListAdapter.addAllData(userResearchTopics);
                                Log.v("tt", userResearchTopics.toString());
                            } else if (researchTopicResponseModel.getCode() == 500) {
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
