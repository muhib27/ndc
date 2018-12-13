package com.classtune.ndc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.activity.VideoPlayerActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.NoticeApi.Notice;
import com.classtune.ndc.apiresponse.NoticeApi.SingleNoticeResponseModel;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.viewhelpers.UIHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeDetailsFragment extends Fragment {
    LinearLayout submittedLayout, pendingLayout;
    UIHelper uiHelper;
    TextView title, assignDate, dueDate, description, cmNumber, submitNumber, pendingNumber;
    LinearLayout attachment_container, statusViewLayout;
    List<ImageView> list = new ArrayList<ImageView>();
    ImageView attachmentImage;
    ImageButton dotMenu;
    Button submitBtn;
    UserPermission userPermission;
    String id = "";

    public NoticeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ndc_logo);
        }

        userPermission = AppSharedPreference.getUserPermission();


        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
        }
        uiHelper = new UIHelper(getActivity());
        initView(view);
        if (id != null && !id.isEmpty())
            callDetailsApi(id);
    }

    private void initView(View view) {
        dotMenu = view.findViewById(R.id.dot_menu);
        dotMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(dotMenu);
            }
        });

        title = view.findViewById(R.id.titleText);
        assignDate = view.findViewById(R.id.assignDate);
        dueDate = view.findViewById(R.id.dueDate);
        description = view.findViewById(R.id.description);


        attachment_container = view.findViewById(R.id.attachment_container);

        if (userPermission.isTasksEdit() && userPermission.isTasksDelete())
            dotMenu.setVisibility(View.VISIBLE);
        else
            dotMenu.setVisibility(View.INVISIBLE);


    }

    private void callDetailsApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getSingleNoticeDetails(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<SingleNoticeResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<SingleNoticeResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        SingleNoticeResponseModel singleNoticeResponseModel = value.body();
//                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (singleNoticeResponseModel!=null && singleNoticeResponseModel.getCode()!= null) {
                            if(singleNoticeResponseModel.getCode()==200){
                                Notice notice = singleNoticeResponseModel.getSingleNoticeData().getNotice();
                                setPageData(notice);
                            }
                            Log.v("PigeonholeFragment", value.message());


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

    private void setPageData(Notice notice) {
        if (!notice.getTitle().isEmpty())
            title.setText(notice.getTitle());
        if (!notice.getDescription().isEmpty())
            description.setText(notice.getDescription());
        if (!notice.getCreatedAt().isEmpty())
            assignDate.setText(uiHelper.dateTimeParse(notice.getCreatedAt()));
//        if (phTaskViewData.getPhSingleTask().getDueDate()!=null && !phTaskViewData.getPhSingleTask().getDueDate().isEmpty())
//            dueDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getDueDate()));


        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
        List<Attachment> attachmentList = notice.getAttachments();
        if (attachmentList != null && attachmentList.size() > 0) {

            for (int i = 0; i < notice.getAttachments().size(); i++) {
                // Add the text layout to the parent layout
                view = layoutInflater.inflate(R.layout.attachment_layout, attachment_container, false);

                // In order to get the view we have to use the new view with text_layout in it
                attachmentImage = view.findViewById(R.id.attachmentImage);
                attachmentImage.setTag(attachmentList.get(i).getName());
                attachmentImage.setId(i + 1);
                list.add(attachmentImage);
                for (final ImageView imageView : list) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
                            if(imageView.getTag().toString().contains(".mp4")) {
                                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                                intent.putExtra("url", imageView.getTag().toString());
                                startActivity(intent);
                            }
                        }
                    });
                }
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(), textView.getTag().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });

                // Add the text view to the parent layout
                attachment_container.addView(attachmentImage);
            }
        }
    }
}
