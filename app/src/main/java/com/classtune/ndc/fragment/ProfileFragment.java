package com.classtune.ndc.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.menu_api.User;
import com.classtune.ndc.apiresponse.reading_package.RMResponseModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    ImageButton edit;
    EditText courseNo, marritalStatus, bdContact, countryContact, rank, bloodGp, dob, nationality, doc;
    FloatingActionButton save;
    LinearLayout rankLayout;
    CircleImageView profileImage;
    User user;
    UIHelper uiHelper;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frofile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        user = AppSharedPreference.getUserBasicInfo();
        uiHelper = new UIHelper(getActivity());
        initView(view);
        //callProfileApi();

        loadImage();
    }

    private void initView(View view) {
        //edit = view.findViewById(R.id.edit);
        rankLayout = view.findViewById(R.id.rankLayout);
        rank = view.findViewById(R.id.rank);
        courseNo = view.findViewById(R.id.courseNo);
        marritalStatus = view.findViewById(R.id.marritalStatus);
        bloodGp = view.findViewById(R.id.bloodGp);
        dob = view.findViewById(R.id.dob);
        doc = view.findViewById(R.id.doc);
        bdContact = view.findViewById(R.id.bdContact);
        countryContact = view.findViewById(R.id.countryContact);
        nationality = view.findViewById(R.id.nationality);
        save = view.findViewById(R.id.save_fab);
        profileImage = view.findViewById(R.id.profile_image);


        save.setOnClickListener(this);

        //edit.setOnClickListener(this);

        disableEditOption();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.edit:
//                enableEditOption();
//                break;
            case R.id.save_fab:
                disableEditOption();
                break;
        }
    }

    private void enableEditOption() {

        save.setVisibility(View.VISIBLE);
        edit.setVisibility(View.GONE);
        rankLayout.setVisibility(View.VISIBLE);

        rank.setEnabled(true);
        rank.requestFocus();
        courseNo.setEnabled(true);
        marritalStatus.setEnabled(true);
        bdContact.setEnabled(true);
        countryContact.setEnabled(true);

    }
    private void disableEditOption() {

        rankLayout.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
//        edit.setVisibility(View.VISIBLE);

        courseNo.setEnabled(false);
        marritalStatus.setEnabled(false);
        bdContact.setEnabled(false);
        countryContact.setEnabled(false);
        rank.setEnabled(false);
    }


    private void loadImage() {
        Glide
                .with(this)
                .load(URLHelper.BASE_URL + user.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .fitCenter())
                .into(profileImage);

    }

    private void callProfileApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().userProfile(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();
//                        RMResponseModel rmResponseModel = value.body();
//
//                        if (rmResponseModel != null && rmResponseModel.getCode() != null) {
//                            if (rmResponseModel.getCode() == 200) {
////                                Log.v("noticeResponseModel", value.message());
////                                List<ReadingList> readingList = rmResponseModel.getReadingPackageData().getReadingList();
////                                Collections.reverse(readingList);
////                                readingPackageAdapter.addAllData(readingList);
////                                Log.v("tt", readingList.toString());
//                                //populateData(rmResponseModel);
//
//                            } else if (rmResponseModel.getCode() == 500) {
//                                //Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
//                        }

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

}
