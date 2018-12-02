package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskListResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.viewhelpers.UIHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructorDetailsFragment extends Fragment implements View.OnClickListener {
    LinearLayout submittedLayout, pendingLayout;
    UIHelper uiHelper;
    TextView title, assignDate, dueDate, description, cmNumber, submitNumber, pendingNumber;


    public InstructorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        uiHelper = new UIHelper(getActivity());
        initView(view);
        callTaskListApi(23);
    }

    private void initView(View view) {
        title = view.findViewById(R.id.titleText);
        assignDate = view.findViewById(R.id.assignDate);
        dueDate = view.findViewById(R.id.dueDate);
        description = view.findViewById(R.id.description);

        cmNumber = view.findViewById(R.id.cmNumber);
        submitNumber = view.findViewById(R.id.submitNumber);
        pendingNumber = view.findViewById(R.id.pendingNumber);


        submittedLayout = view.findViewById(R.id.submittedLayout);
        submittedLayout.setOnClickListener(this);
        pendingLayout = view.findViewById(R.id.pendingLayout);
        pendingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submittedLayout:
                gotoCMListFragment("submitted");
                break;
            case R.id.pendingLayout:
                gotoCMListFragment("pending");
                break;
        }
    }

    private void gotoCMListFragment(String cmType) {
        Bundle bundle = new Bundle();
        bundle.putString("cmType", cmType);
        CMListFragment cmListFragment = new CMListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmListFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmListFragment, "cmListFragment").addToBackStack(null);
        ;
        transaction.commit();
    }

    private void callTaskListApi(int id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
//        uiHelper.showLoadingDialog("Authenticating...");


        RetrofitApiClient.getApiInterface().getSinglePHDetails(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<PHTaskViewResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<PHTaskViewResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        PHTaskViewResponse phTaskViewResponse = value.body();
//                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (phTaskViewResponse.getCode() == 200) {
                            Log.v("PigeonholeFragment", value.message());

                            PHTaskViewData phTaskViewData = phTaskViewResponse.getPhTaskViewData();
                            setPageData(phTaskViewData);

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

    private void setPageData(PHTaskViewData phTaskViewData) {
        if (!phTaskViewData.getPhSingleTask().getTitle().isEmpty())
            title.setText(phTaskViewData.getPhSingleTask().getTitle());
        if (!phTaskViewData.getPhSingleTask().getDescription().isEmpty())
            description.setText(phTaskViewData.getPhSingleTask().getDescription());
        if (!phTaskViewData.getPhSingleTask().getCreatedAt().isEmpty())
            assignDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getCreatedAt()));
        if (!phTaskViewData.getPhSingleTask().getDueDate().isEmpty())
            dueDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getDueDate()));

        if (phTaskViewData.getPhSingleTask().getTotal() != null)
            cmNumber.setText(phTaskViewData.getPhSingleTask().getTotal());
        if (phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
            submitNumber.setText(phTaskViewData.getPhSingleTask().getSubmittedTotal());
        if (phTaskViewData.getPhSingleTask().getTotal() != null && phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
            pendingNumber.setText("" + (Integer.parseInt(phTaskViewData.getPhSingleTask().getTotal()) - Integer.parseInt(phTaskViewData.getPhSingleTask().getSubmittedTotal())));
    }


}
