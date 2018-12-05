package com.classtune.ndc.retrofit;

/**
 * Created by RR on 08-Jan-18.
 */

import com.classtune.ndc.utils.URLHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitWithTime {

    public static <S> S createService(Class<S> serviceClass) {
        String BASE_URL = "https://api.champs21.com/";
        Retrofit retrofit = null;
//        if(ip!=null && !ip.isEmpty()){
//            BASE_URL = "http://"+ ip + "/ApprovalSystem/";
//        }
//        if(ip.equals("internet")){
//            BASE_URL = "http://"+ "182.160.102.101:8080" + "/ApprovalSystem/";
//        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(URLHelper.BASE_URL + URLHelper.SUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}
