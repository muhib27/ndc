package com.classtune.ndc.retrofit;


import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//import io.reactivex.Observable;


/**
 * Created by RR on 27-Dec-17.
 */

public interface ApiInterface {

    //@Headers({"clientAgent : ANDROID", "version : 1"})
    //@POST("api/user/register")
    //Call<ServerResponse> getUserValidity(@Body MyUser userLoginCredential);

    //@FormUrlEncoded
//    @POST("getquestion")
//    //Call<JsonElement> fees(@FieldMap HashMap<String, String> params);
//    Call<ServerResponse> getQusestion(@Body CallQuestion callQuestion);
    // Call<JSONObject> question(@FieldMap HashMap<String, String> params);

//    @FormUrlEncoded
//    @POST("api/sciencerocks/getquestion")
//    Call<JsonElement> getQusestion(@FieldMap Map<String, String> params);

    //    @POST("api/sciencerocks")
//    Observable<Response<JsonElement>> getTopics(@Body HashMap<String, String> params);
//    @FormUrlEncoded
//    @POST("api/sciencerocks/getscoreboard")
//    Call<JsonElement> getLeaderBoard(@FieldMap Map<String, String> params);
//
//    @GET("movie/top_rated")
//    Call<TopRatedMovies> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int pageIndex);

    //    @GET("movie/top_rated")
//    Observable<Response<TopRatedMovies> >getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int pageIndex);
//    @GET("posts?_embed")
//    Call<JsonElement> getTopics(@Query("categories") int post, @Query("per_page") int per_page, @Query("offset") int offest);
//    @GET("posts")
//    Call<JsonElement> getLatest(@Query("per_page") int per_page, @Query("offset") int offest);

//    @GET("posts")
//    Call<List<CategoryModel>> getTopicsList(@Query("categories") int post, @Query("per_page") int per_page);
//    @GET("wp-json/wp/v2/posts/{posts}?_embed")
//    Observable<JsonElement> getSinglePost(@Path("posts") int post);
//
//
//    @GET("wp-json/wp/v2/posts?_embed")
//    Observable<Response<List<CategoryModel>>> getTopics(@Query("categories") int post, @Query("per_page") int per_page, @Query("offset") int offest);
//    @GET("wp-json/wp/v2/posts?_embed")
//    Observable<Response<List<CategoryModel>>>     getLatest(@Query("per_page") int per_page, @Query("offset") int offest);
//
//    @GET("wp-json/wp/v2/posts?_embed")
//    Observable<Response<List<CategoryModel>>> getSearachTopics(@Query("search") String search, @Query("per_page") int per_page, @Query("offset") int offest);
}


