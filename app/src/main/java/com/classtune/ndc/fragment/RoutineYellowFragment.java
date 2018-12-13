package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.adapter.DashboardClassScheduleAdapter;
import com.classtune.ndc.adapter.RoutineAdapter;
import com.classtune.ndc.apiresponse.course_calendar_api.Routine;
import com.classtune.ndc.apiresponse.course_calendar_api.RoutineResponseModel;
import com.classtune.ndc.model.ClassScheduleModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

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
public class RoutineYellowFragment extends Fragment {


    public RoutineYellowFragment() {
        // Required empty public constructor
    }

    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<ClassScheduleModel> strList = new ArrayList<>();
    RoutineAdapter routineAdapter;
    UIHelper uiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine_white, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRoutineView(view);
        callRoutineApi();
    }

    private void initRoutineView(View view) {
        uiHelper = new UIHelper(getActivity());

        rv = (RecyclerView) view.findViewById(R.id.class_schedule_rv);

        strList = getStrList();
        routineAdapter = new RoutineAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(routineAdapter);
//        routineAdapter.setData(getStrList());
        routineAdapter.notifyDataSetChanged();
    }

    private ArrayList<ClassScheduleModel> getStrList() {
        ArrayList<ClassScheduleModel> list = new ArrayList<>();

        ClassScheduleModel classScheduleModel =new ClassScheduleModel("Keynote speech on national security", "9:00 - 11:00", "09 Dec 2018", "Maj. Gen. Tarique Ahmed Siddique,rcds,psc");
        list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Follow up discussion", "12:00 - 14:00", "09 Dec 2018", "All SDSs");
        list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Keynote speech on BAF", "9:00 - 11:00", "10 Dec 2018", "Air Chief Marshal Masihuzzaman,Serniabat, BBP,OSP,ndu,pscGD(P) Chief of air staff");
        list.add(classScheduleModel);
        classScheduleModel =new ClassScheduleModel("Ethnocentrism, Strategic culture & Leadership language", "12:00 - 14:00", "10 Dec 2018", "Lt. Gen. Md. Mahfujur Rahman PSO,AFD");
        list.add(classScheduleModel);


        return list;
    }


    private void callRoutineApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getYellowRoutine(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RoutineResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<RoutineResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        RoutineResponseModel routineResponseModel = value.body();

                        if(routineResponseModel != null && routineResponseModel.getCode()!=null) {
                            if (routineResponseModel.getCode() == 200) {
                                Log.v("routineResponseModel", value.message());
                                List<Routine> routine = routineResponseModel.getRoutineData().getRoutine();
                                Collections.reverse(routine);
                                routineAdapter.addAllData(routine);
                                //Log.v("tt", cmBoxSubmittedTasks.toString());
                            } else if (routineResponseModel.getCode() == 500) {
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onError(Throwable e) {

                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }
}
