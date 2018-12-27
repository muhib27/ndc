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
import com.classtune.ndc.activity.VideoPlayerActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.NoticeApi.Notice;
import com.classtune.ndc.apiresponse.NoticeApi.SingleNoticeResponseModel;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
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
                showPopupMenu(dotMenu);
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

    List<Attachment> attachmentList;
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
        attachmentList = notice.getAttachments();
        if (attachmentList != null && attachmentList.size() > 0) {

            for (int i = 0; i < notice.getAttachments().size(); i++) {
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
                        gotoNoticeAddFragment(id);
                        break;
                    case R.id.delete:
//                        Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                        callNoticeDeleteApi(id);
                        break;
                }
                return false;
            }
        });
    }

    private void gotoNoticeAddFragment(String id) {
        Bundle bundle=new Bundle();
        bundle.putString("id", id);

        NoticeAddFragment noticeAddFragment = new NoticeAddFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeAddFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeAddFragment, "noticeAddFragment").addToBackStack(null);
        transaction.commit();
    }
    public void callNoticeDeleteApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return ;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().noticeDelete(id, AppSharedPreference.getApiKey())

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
}
