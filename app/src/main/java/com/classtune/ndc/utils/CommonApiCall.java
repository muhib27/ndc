package com.classtune.ndc.utils;

import android.content.Context;
import android.content.Intent;

import com.classtune.ndc.activity.LoginActivity;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.menu_api.MenuApiResponse;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Fahim on 12/7/2018.
 */

public class CommonApiCall {
    private Context context;
    MainActivity activity;
    UIHelper uiHelper;

    public CommonApiCall() {
    }

    public CommonApiCall(Context context) {
        this.context = context;
        activity = (MainActivity) context;
        uiHelper = new UIHelper(activity);
    }

    public Context getContext() {
        return context;
    }
    boolean status = false;
    public boolean callPigeonholeDeleteApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return false;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().pigeonholeDelete(id, AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();


                        if(value.code() ==200) {

                            status = true;
                        }
                        else
                            status = false;

                    }


                    @Override
                    public void onError(Throwable e) {
                        status = false;
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        uiHelper.dismissLoadingDialog();
                    }
                });

        return status;
    }


//    public void commit(final RetrofitResponseListener retrofitResponseListener) {
//
//        requestHandler.insertFields(tableName, values).enqueue(new Callback<DatabaseModel>() {
//            @Override
//            public void onResponse(Call<DatabaseModel> call,
//                                   Response<DatabaseModel> response) {
//                if (response.isSuccessful())
//                    retrofitResponseListener.onSuccess();
//                else
//                    retrofitResponseListener.onFailure();
//
//            }
//
//            @Override
//            public void onFailure(Call<DatabaseModel> call, Throwable t) {
//                retrofitResponseListener.onFailure();
//            } 
//        });
//    }
//private void setListener() {
//    commit(new RetrofitResponseListener() {
//        @Override
//        public void onSuccess() {
//
//        }
//
//        @Override
//        public void onFailure() {
//
//        }
//    });
//}
}
