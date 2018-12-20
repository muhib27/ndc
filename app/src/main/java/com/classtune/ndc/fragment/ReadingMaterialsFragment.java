package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.reading_package.RPResponseModel;
import com.classtune.ndc.apiresponse.reading_package.ReadingList;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadingMaterialsFragment extends Fragment {
    UIHelper uiHelper;
    WebView webView;
    String id ="";

    public ReadingMaterialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_materials, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
//            if(b.getString("type", "")!=null)
//                type = b.getString("type", "");

        }
        uiHelper = new UIHelper(getActivity());
        webView = view.findViewById(R.id.webView);

        callReadingApi(id);
    }


    private void callReadingApi( String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getReadingContent(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();
                       // RPResponseModel rpResponseModel = value.body();

//                        if(rpResponseModel != null && rpResponseModel.getCode()!=null) {
//                            if (rpResponseModel.getCode() == 200) {
////                                Log.v("noticeResponseModel", value.message());
//                                List<ReadingList> readingList = rpResponseModel.getReadingPackageData().getReadingList();
////                                Collections.reverse(readingList);
//                                readingPackageAdapter.addAllData(readingList);
//                                Log.v("tt", readingList.toString());
//                            } else if (rpResponseModel.getCode() == 500) {
//                                //Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else {
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
