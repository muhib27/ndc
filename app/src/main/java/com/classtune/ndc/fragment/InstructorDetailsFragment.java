package com.classtune.ndc.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.activity.PlayerActivity;
import com.classtune.ndc.activity.VideoPlayerActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTask;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskListResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskSubmitResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTaskData;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
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
public class InstructorDetailsFragment extends Fragment implements View.OnClickListener {
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
        userPermission = AppSharedPreference.getUserPermission();


        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
        }
        uiHelper = new UIHelper(getActivity());
        initView(view);
        if (id != null && !id.isEmpty())
            callTaskListApi(id);
    }

    private void initView(View view) {
        dotMenu = view.findViewById(R.id.dot_menu);
        dotMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(dotMenu);
            }
        });
        submitBtn = view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);

        title = view.findViewById(R.id.titleText);
        assignDate = view.findViewById(R.id.assignDate);
        dueDate = view.findViewById(R.id.dueDate);
        description = view.findViewById(R.id.description);

        cmNumber = view.findViewById(R.id.cmNumber);
        submitNumber = view.findViewById(R.id.submitNumber);
        pendingNumber = view.findViewById(R.id.pendingNumber);


        submittedLayout = view.findViewById(R.id.submittedLayout);
        statusViewLayout = view.findViewById(R.id.statusViewLayout);
        submittedLayout.setOnClickListener(this);
        pendingLayout = view.findViewById(R.id.pendingLayout);
        pendingLayout.setOnClickListener(this);

        attachment_container = view.findViewById(R.id.attachment_container);

        if (userPermission.isTasksEdit() && userPermission.isTasksDelete())
            dotMenu.setVisibility(View.VISIBLE);
        else
            dotMenu.setVisibility(View.INVISIBLE);


        if (userPermission.isUserTasksSubmitTask()) {
            submitBtn.setVisibility(View.VISIBLE);
            statusViewLayout.setVisibility(View.GONE);
        } else {
            submitBtn.setVisibility(View.GONE);
            statusViewLayout.setVisibility(View.VISIBLE);
        }

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
            case R.id.submit:
               callForSubmitStatus(id);
                break;
        }
    }

    private void callForSubmitStatus(final String id) {

            if (!NetworkConnection.getInstance().isNetworkAvailable()) {
                //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
                return;
            }
            uiHelper.showLoadingDialog("Please wait...");


            RetrofitApiClient.getApiInterface().getPHTaskViewSubmitTask(AppSharedPreference.getApiKey(), id)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<PHTaskSubmitResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<PHTaskSubmitResponse> value) {
                            uiHelper.dismissLoadingDialog();
                            PHTaskSubmitResponse phTaskSubmitResponse = value.body();

                            if (phTaskSubmitResponse!=null && phTaskSubmitResponse.getCode() == 200) {
                                Log.v("PigeonholeFragment", value.message());
                                if(phTaskSubmitResponse.getSubmittedTaskData().getSubmittedTask()==null)
                                {
                                    gotoCMTaskSubmitFragment(id);
                                }
                                else {
                                    gotoCMSubmitTaskDetailsFragment(phTaskSubmitResponse.getSubmittedTaskData());
                                }

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

    private void gotoCMListFragment(String cmType) {
        Bundle bundle = new Bundle();
        bundle.putString("cmType", cmType);
        CMListFragment cmListFragment = new CMListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmListFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmListFragment, "cmListFragment").addToBackStack(null);
        transaction.commit();
    }
    private void gotoCMTaskSubmitFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        CMTaskSubmitFragment cmTaskSubmitFragment = new CMTaskSubmitFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmTaskSubmitFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmTaskSubmitFragment, "cmTaskSubmitFragment").addToBackStack(null);
        transaction.commit();
    }
    private void gotoCMSubmitTaskDetailsFragment(SubmittedTaskData submittedTaskData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("submittedTaskData", submittedTaskData);
        CMSubmitTaskDetailsFragment cmSubmitTaskDetailsFragment = new CMSubmitTaskDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cmSubmitTaskDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, cmSubmitTaskDetailsFragment, "cmSubmitTaskDetailsFragment").addToBackStack(null);
        transaction.commit();
    }

    private void callTaskListApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


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
    List<Attachment> attachmentList;
    private void setPageData(PHTaskViewData phTaskViewData) {
        if (!phTaskViewData.getPhSingleTask().getTitle().isEmpty())
            title.setText(phTaskViewData.getPhSingleTask().getTitle());
        if (!phTaskViewData.getPhSingleTask().getDescription().isEmpty())
            description.setText(phTaskViewData.getPhSingleTask().getDescription());
        if (!phTaskViewData.getPhSingleTask().getCreatedAt().isEmpty())
            assignDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getCreatedAt()));
        if (phTaskViewData.getPhSingleTask().getDueDate()!=null && !phTaskViewData.getPhSingleTask().getDueDate().isEmpty())
            dueDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getDueDate()));

        if (phTaskViewData.getPhSingleTask().getTotal() != null)
            cmNumber.setText(phTaskViewData.getPhSingleTask().getTotal());
        if (phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
            submitNumber.setText(phTaskViewData.getPhSingleTask().getSubmittedTotal());
        if (phTaskViewData.getPhSingleTask().getTotal() != null && phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
            pendingNumber.setText("" + (Integer.parseInt(phTaskViewData.getPhSingleTask().getTotal()) - Integer.parseInt(phTaskViewData.getPhSingleTask().getSubmittedTotal())));


        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
         attachmentList = phTaskViewData.getPhSingleTask().getAttachments();
        if (attachmentList != null && attachmentList.size() > 0) {

            for (int i = 0; i < phTaskViewData.getPhSingleTask().getAttachments().size(); i++) {
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
                            else if(attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName().contains("png") || attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName().contains("jpg"))
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



    PopupMenu popupMenu;

    private void showPopupMenu(View view) {

        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popupMenuStyle);
        popupMenu = new PopupMenu(wrapper, view);
        popupMenu.inflate(R.menu.pigeonhole_cell_menu);


        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {

            popupMenu.show();
            return;
        }


        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit:
//                         Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                        gotoInstructorTaskAssignFragment(id);
                        break;
                    case R.id.delete:
                        callPigeonholeDeleteApi(id);
//                        Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
//                        CommonApiCall commonApiCall = new CommonApiCall(getActivity());
//                        boolean b = commonApiCall.callPigeonholeDeleteApi(id);
//                        if(b){
//                            int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
//                            getActivity().getSupportFragmentManager().popBackStack();
//                            int count1 = getActivity().getSupportFragmentManager().getBackStackEntryCount();
//                            Log.v("tag", count1+"");
//                        }
                        break;
                }
                return false;
            }
        });
    }

    public void callPigeonholeDeleteApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();

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
                            if(getActivity()!=null)
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                        else{
                            Toast.makeText(getActivity(), "Something went wrong. Please try later", Toast.LENGTH_SHORT).show();
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

        return;
    }



    private void gotoInstructorTaskAssignFragment(String id) {
        Bundle bundle=new Bundle();
        bundle.putString("id", id);

        InsTructorTaskAssignFragment insTructorTaskAssignFragment = new InsTructorTaskAssignFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        insTructorTaskAssignFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, insTructorTaskAssignFragment, "insTructorTaskAssignFragment").addToBackStack(null);
        transaction.commit();
    }
}
