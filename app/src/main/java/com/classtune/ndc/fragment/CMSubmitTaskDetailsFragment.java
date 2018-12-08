package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTask;
import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTaskData;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.AppUtility;
import com.classtune.ndc.utils.NetworkConnection;
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
public class CMSubmitTaskDetailsFragment extends Fragment implements View.OnClickListener {
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
    SubmittedTaskData submittedTaskData;


    public CMSubmitTaskDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cm_submit_task_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userPermission = AppSharedPreference.getUserPermission();
        submittedTaskData = new SubmittedTaskData();


        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getSerializable("submittedTaskData") != null)
                submittedTaskData = (SubmittedTaskData) b.getSerializable("submittedTaskData");
        }
        uiHelper = new UIHelper(getActivity());
        initView(view);
//        if (id != null && !id.isEmpty())
//            callTaskListApi(id);
    }

    private void initView(View v) {

        title = v.findViewById(R.id.titleText);
        assignDate = v.findViewById(R.id.assignDate);
        attachment_container = v.findViewById(R.id.attachment_container);

        if(submittedTaskData.getSubmittedTask().getContent()!=null && !submittedTaskData.getSubmittedTask().getContent().isEmpty())
            title.setText(submittedTaskData.getSubmittedTask().getContent());
        if(submittedTaskData.getSubmittedTask().getCreatedAt()!=null && !submittedTaskData.getSubmittedTask().getCreatedAt().isEmpty())
            assignDate.setText(AppUtility.getDateString(submittedTaskData.getSubmittedTask().getCreatedAt(),AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));

        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
        List<Attachment> attachmentList = submittedTaskData.getSubmittedTask().getAttachments();
        if (attachmentList != null && attachmentList.size() > 0) {

            for (int i = 0; i < submittedTaskData.getSubmittedTask().getAttachments().size(); i++) {
                // Add the text layout to the parent layout
                view = layoutInflater.inflate(R.layout.attachment_layout, attachment_container, false);

                // In order to get the view we have to use the new view with text_layout in it
                attachmentImage = view.findViewById(R.id.attachmentImage);
                attachmentImage.setTag("Row " + i);
                attachmentImage.setId(i + 1);
                list.add(attachmentImage);
                for (final ImageView imageView : list) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }



//    private void setPageData(PHTaskViewData phTaskViewData) {
//        if (!phTaskViewData.getPhSingleTask().getTitle().isEmpty())
//            title.setText(phTaskViewData.getPhSingleTask().getTitle());
//        if (!phTaskViewData.getPhSingleTask().getDescription().isEmpty())
//            description.setText(phTaskViewData.getPhSingleTask().getDescription());
//        if (!phTaskViewData.getPhSingleTask().getCreatedAt().isEmpty())
//            assignDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getCreatedAt()));
//        if (!phTaskViewData.getPhSingleTask().getDueDate().isEmpty())
//            dueDate.setText(uiHelper.dateTimeParse(phTaskViewData.getPhSingleTask().getDueDate()));
//
//        if (phTaskViewData.getPhSingleTask().getTotal() != null)
//            cmNumber.setText(phTaskViewData.getPhSingleTask().getTotal());
//        if (phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
//            submitNumber.setText(phTaskViewData.getPhSingleTask().getSubmittedTotal());
//        if (phTaskViewData.getPhSingleTask().getTotal() != null && phTaskViewData.getPhSingleTask().getSubmittedTotal() != null)
//            pendingNumber.setText("" + (Integer.parseInt(phTaskViewData.getPhSingleTask().getTotal()) - Integer.parseInt(phTaskViewData.getPhSingleTask().getSubmittedTotal())));
//
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View view;
//        List<Attachment> attachmentList = phTaskViewData.getPhSingleTask().getAttachments();
//        if (attachmentList != null && attachmentList.size() > 0) {
//
//            for (int i = 0; i < phTaskViewData.getPhSingleTask().getAttachments().size(); i++) {
//                // Add the text layout to the parent layout
//                view = layoutInflater.inflate(R.layout.attachment_layout, attachment_container, false);
//
//                // In order to get the view we have to use the new view with text_layout in it
//                attachmentImage = view.findViewById(R.id.attachmentImage);
//                attachmentImage.setTag("Row " + i);
//                attachmentImage.setId(i + 1);
//                list.add(attachmentImage);
//                for (final ImageView imageView : list) {
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(getActivity(), imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
////            textView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Toast.makeText(getApplicationContext(), textView.getTag().toString(), Toast.LENGTH_SHORT).show();
////                }
////            });
//
//                // Add the text view to the parent layout
//                attachment_container.addView(attachmentImage);
//            }
//        }
//    }


}
