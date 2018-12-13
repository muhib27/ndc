package com.classtune.ndc.retrofit;


import com.classtune.ndc.apiresponse.CMBox.CMBoxSubmittedTaskResponse;
import com.classtune.ndc.apiresponse.LoginApiModel;
import com.classtune.ndc.apiresponse.NoticeApi.NoticeResponseModel;
import com.classtune.ndc.apiresponse.NoticeApi.SingleNoticeResponseModel;
import com.classtune.ndc.apiresponse.course_calendar_api.EventsResponseModel;
import com.classtune.ndc.apiresponse.course_calendar_api.RoutineResponseModel;
import com.classtune.ndc.apiresponse.menu_api.MenuApiResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskListResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskSubmitResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeGetCourseApiResponse;
import com.classtune.ndc.fragment.CMTaskSubmitFragment;
import com.classtune.ndc.fragment.InsTructorTaskAssignFragment;
import com.classtune.ndc.utils.URLHelper;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static com.classtune.ndc.fragment.CMTaskSubmitFragment.phTaskSubmitId;


//import io.reactivex.Observable;


/**
 * Created by RR on 27-Dec-17.
 */

public interface ApiInterface {
    String phTaskSubmitId = CMTaskSubmitFragment.phTaskSubmitId;

    @FormUrlEncoded
    @POST(URLHelper.URL_LOGIN)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<LoginApiModel>> userLogin(@Field("email") String userId, @Field("password") String password, @Field("fcm_id") String gcm_id);

    @FormUrlEncoded
    @POST(URLHelper.ADD_FCM)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<JsonElement>> addFcm(@Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.GET_MENU)
    Observable<Response<MenuApiResponse>> getMenu(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_LOGOUT)
    Observable<Response<JsonElement>> getLogout(@Field("api_key") String api_key, @Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.GET_PIGEONHOLE_TASK_LIST)
    Observable<Response<PHTaskListResponse>> getPigeonholeTaskList(@Field("api_key") String api_key);


    @POST(URLHelper.GET_PIGEONHOLE_TASK_ADD)
      //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> getTaskAssign(@Body MultipartBody file );

    @POST(".")
        //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> getTaskEdit(@Body MultipartBody file);

    @FormUrlEncoded
    @POST(URLHelper.GET_PIGEONHOLE_TASK_DELETE)
    Observable<Response<JsonElement>> pigeonholeDelete(@Field("id") String id, @Field("api_key") String api_key);

      //Observable<Response<JsonElement>> getTaskAssign(@Part("api_key") RequestBody api_key,@Part List<MultipartBody.Part> file, @Part("users[]") List<String> selectedList, @Part("course[]") List<String> courseList, @Part("title") RequestBody title, @Part("description") RequestBody description, @Part("due_date") RequestBody due_date);

    @FormUrlEncoded
    @POST(URLHelper.GET_PIGEONHOLE_GET_COURSES)
    Observable<Response<PigeonholeGetCourseApiResponse>> getPigeonholeCourses(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_PIGEONHOLE_DETAILS + "/{id}")
    Observable<Response<PHTaskViewResponse>> getSinglePHDetails(@Field("api_key") String api_key, @Path("id") String id);

    @FormUrlEncoded
    @POST(URLHelper.GET_PIGEONHOLE_TASK_VIEW_SUBITTED_TASK + "/{id}")
    Observable<Response<PHTaskSubmitResponse>> getPHTaskViewSubmitTask(@Field("api_key") String api_key, @Path("id") String id);

    @POST(".")
        //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> getPHTaskSubmitTask(@Body MultipartBody file);

    @FormUrlEncoded
    @POST(URLHelper.GET_CM_BOX_SUBMITTED_LIST)
    Observable<Response<CMBoxSubmittedTaskResponse>> getCMBoxList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_CM_BOX_DETAILS + "/{id}")
    Observable<Response<CMBoxSubmittedTaskResponse>> getCMBoxDetails(@Field("api_key") String api_key, @Path("id") String id);


    @FormUrlEncoded
    @POST(URLHelper.GET_PROGRAM_WHITE_ROUTINE_LIST)
    Observable<Response<RoutineResponseModel>> getWhiteRoutine(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_PROGRAM_YELLOW_ROUTINE_LIST)
    Observable<Response<RoutineResponseModel>> getYellowRoutine(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_PROGRAM_BLUE_ROUTINE_LIST)
    Observable<Response<RoutineResponseModel>> getBlueRoutine(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.GET_EVENTS_LIST)
    Observable<Response<EventsResponseModel>> getEvents(@Field("api_key") String api_key, @Field("start") String start, @Field("end") String end);

    @FormUrlEncoded
    @POST(URLHelper.GET_NOTICE_LIST)
    Observable<Response<NoticeResponseModel>> getNoticeList(@Field("api_key") String api_key);


    @FormUrlEncoded
    @POST(URLHelper.GET_NOTICE_DETAILS + "/{id}")
    Observable<Response<SingleNoticeResponseModel>>  getSingleNoticeDetails(@Field("api_key") String api_key, @Path("id") String id);

























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


