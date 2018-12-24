package com.classtune.ndc.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.PlayerActivity;
import com.classtune.ndc.activity.VideoPlayerActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.reading_package.RMAttachment;
import com.classtune.ndc.apiresponse.reading_package.RMResponseModel;
import com.classtune.ndc.apiresponse.reading_package.RPResponseModel;
import com.classtune.ndc.apiresponse.reading_package.ReadingList;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.CommonApiCall;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.URLHelper;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

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
public class ReadingMaterialsFragment extends Fragment {
    UIHelper uiHelper;
    WebView webView;
    TextView title;
    String id = "";
    LinearLayout attachment_container;
    ImageView attachmentImage;
    List<ImageView> list = new ArrayList<ImageView>();

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
        attachment_container = view.findViewById(R.id.attachment_container);
        title = view.findViewById(R.id.title);

        callReadingApi(id);
    }


    private void callReadingApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getReadingContent(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RMResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<RMResponseModel> value) {
                        uiHelper.dismissLoadingDialog();
                        RMResponseModel rmResponseModel = value.body();

                        if (rmResponseModel != null && rmResponseModel.getCode() != null) {
                            if (rmResponseModel.getCode() == 200) {
//                                Log.v("noticeResponseModel", value.message());
//                                List<ReadingList> readingList = rmResponseModel.getReadingPackageData().getReadingList();
//                                Collections.reverse(readingList);
//                                readingPackageAdapter.addAllData(readingList);
//                                Log.v("tt", readingList.toString());
                                populateData(rmResponseModel);

                            } else if (rmResponseModel.getCode() == 500) {
                                //Toast.makeText(getActivity(), "500", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
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

    List<RMAttachment> attachmentList;
    private void populateData(RMResponseModel rmResponseModel){

        try {
            if (rmResponseModel.getData().getReadingContent().getContentDetails().getTitle() != null)
                title.setText(rmResponseModel.getData().getReadingContent().getContentDetails().getTitle());
            if (rmResponseModel.getData().getReadingContent().getContentDetails().getContent() != null) {
                String contentStr = String.valueOf(Html
                        .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#222222; \">"
                                + rmResponseModel.getData().getReadingContent().getContentDetails().getContent()
                                + "</body>]]>"));
                webView.loadData("<style>figure{height: auto;width: 100% !important; padding:0px !important;margin:0px !important;} img{height: auto;width: 100% !important;} </style>" + contentStr, "text/html", "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
        if(rmResponseModel.getData().getReadingContent().getAttachments()!=null) {
           attachmentList = rmResponseModel.getData().getReadingContent().getAttachments();
            if (attachmentList != null && attachmentList.size() > 0) {

                for (int i = 0; i < rmResponseModel.getData().getReadingContent().getAttachments().size(); i++) {
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
                    attachmentImage.setId(i);
                    list.add(attachmentImage);
                    for (final ImageView imageView : list) {
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
                                if (attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileExt().equals("mp4")) {
                                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                                    intent.putExtra("url", imageView.getTag().toString());
                                    startActivity(intent);
                                }
                                else if(attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileExt().equals("png"))
                                {
                                    CommonApiCall commonApiCall = new CommonApiCall(getActivity());
                                    commonApiCall.showImage(attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFileName());
                                }
                                else {

                                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                            .parse(URLHelper.BASE_URL+ attachmentList.get(Integer.parseInt(imageView.getTag().toString())).getFilePath() )));
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
}
