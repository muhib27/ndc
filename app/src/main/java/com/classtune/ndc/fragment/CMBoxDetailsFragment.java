package com.classtune.ndc.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.activity.PlayerActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.CMBox.CMBoxSubmittedTask;
import com.classtune.ndc.apiresponse.CMBox.CMBoxSubmittedTaskResponse;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskSubmitResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTaskData;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.AppUtility;
import com.classtune.ndc.utils.CommonApiCall;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.URLHelper;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import java.lang.reflect.Field;
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
public class CMBoxDetailsFragment extends Fragment implements View.OnClickListener {
    LinearLayout submittedLayout, pendingLayout;
    UIHelper uiHelper;
    TextView title, submittedBy, dueDate, submittedDate, assignBy, assignDate, description;
    LinearLayout attachment_container, statusViewLayout;
    List<ImageView> list = new ArrayList<ImageView>();
    ImageView attachmentImage;
    ImageButton dotMenu;
    Button submitBtn;
    UserPermission userPermission;
    String id = "";


    public CMBoxDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cm_box_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            callTaskViewApi(id);
    }

    private void initView(View view) {

        description= view.findViewById(R.id.descriptionText);
        title = view.findViewById(R.id.titleText);
        submittedBy = view.findViewById(R.id.submitteBy);
        assignBy = view.findViewById(R.id.assignedBy);
        submittedDate = view.findViewById(R.id.submittedDate);
        dueDate = view.findViewById(R.id.dueDate);


        attachment_container = view.findViewById(R.id.attachment_container);

//        if (userPermission.isTasksEdit() && userPermission.isTasksDelete())
//            dotMenu.setVisibility(View.VISIBLE);
//        else
//            dotMenu.setVisibility(View.INVISIBLE);
//
//
//        if (userPermission.isUserTasksSubmitTask()) {
//            submitBtn.setVisibility(View.VISIBLE);
//            statusViewLayout.setVisibility(View.GONE);
//        } else {
//            submitBtn.setVisibility(View.GONE);
//            statusViewLayout.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.submittedLayout:
//                gotoCMListFragment("submitted");
//                break;
//            case R.id.pendingLayout:
//                gotoCMListFragment("pending");
//                break;
        }
    }


    private void callTaskViewApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getCMBoxDetails(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CMBoxSubmittedTaskResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CMBoxSubmittedTaskResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        CMBoxSubmittedTaskResponse cmBoxSubmittedTaskResponse = value.body();
//                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (cmBoxSubmittedTaskResponse.getCode() == 200) {
                            Log.v("PigeonholeFragment", value.message());

                            CMBoxSubmittedTask cmBoxSubmittedTask = cmBoxSubmittedTaskResponse.getCmBoxData().getSubmittedTask();
                            setPageData(cmBoxSubmittedTask);

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
    List<Attachment> attachmentList;

    private void setPageData(CMBoxSubmittedTask cmBoxSubmittedTask) {
        if (!cmBoxSubmittedTask.getTitle().isEmpty())
            title.setText(cmBoxSubmittedTask.getTitle());
        if (!cmBoxSubmittedTask.getDescription().isEmpty())
            description.setText(cmBoxSubmittedTask.getDescription());

        if (cmBoxSubmittedTask.getUserName()!=null)
            submittedBy.setText(": "+cmBoxSubmittedTask.getUserName());
        if (cmBoxSubmittedTask.getAssignedBy()!=null)
            assignBy.setText(": "+cmBoxSubmittedTask.getAssignedBy());
        if (cmBoxSubmittedTask.getSubmitDate()!=null)
            submittedDate.setText(": " + AppUtility.getDateString(cmBoxSubmittedTask.getSubmitDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
        if (cmBoxSubmittedTask.getDueDate()!=null)
            dueDate.setText(": " + AppUtility.getDateString(cmBoxSubmittedTask.getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));


        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
        attachmentList = cmBoxSubmittedTask.getAttachments();
        if (attachmentList != null && attachmentList.size() > 0) {

            for (int i = 0; i < cmBoxSubmittedTask.getAttachments().size(); i++) {
                // Add the text layout to the parent layout
                view = layoutInflater.inflate(R.layout.attachment_layout, attachment_container, false);

                // In order to get the view we have to use the new view with text_layout in it
                attachmentImage = view.findViewById(R.id.attachmentImage);
                if(attachmentList.get(i).getFileType().contains("mp4"))
                {
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.attachment_mp));
                }
                else if(attachmentList.get(i).getFileType().contains("3gp")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.three_gp_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("doc")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.doc_attachment));
                } else if(attachmentList.get(i).getFileType().contains("docx")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.docs_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("gif")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.gif_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("mp3")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.mp_three_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("pdf")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.pdf_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("psd")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.psd_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("rar")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.rar_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("txt")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.txt_attachment));
                } else if(attachmentList.get(i).getFileType().contains("xls")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.xls_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("zip")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.zip_attachment));
                }
                else if(attachmentList.get(i).getFileType().contains("png") || attachmentList.get(i).getFileType().contains("jpg")){
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.photo_attachment));
                }
                else {
                    attachmentImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image_attachment));
                }
                attachmentImage.setTag(i);
                attachmentImage.setId(i + 1);
                list.add(attachmentImage);
                for (final ImageView imageView : list) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
                            if (attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName().contains("mp4")) {
                                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                                intent.putExtra("url", attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName());
                                startActivity(intent);
                            }
                            else if(attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName().contains("png"))
                            {
                                CommonApiCall commonApiCall = new CommonApiCall(getActivity());
                                commonApiCall.showImage(attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName());
                            }
                            else {

                                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                        .parse(URLHelper.BASE_URL+ attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName() )));
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
