package com.classtune.ndc.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.viewhelpers.CustomTabButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PHTabFragment extends Fragment implements View.OnClickListener {
    private View view;
    private CustomTabButton currentButton;

    private CustomTabButton btnOne;
    private CustomTabButton btnTwo;
    private CustomTabButton btnThree;
    private CustomTabButton btnFour;

    private List<CustomTabButton> listBtn;
    private String noticeType = "";
    UserPermission userPermission;

    private LinearLayout layoutFilter;
    private boolean isFilterClicked = false;
    private LinearLayout layoutMidPanel;
    private ImageView imgFilter;

    public PHTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBtn = new ArrayList<CustomTabButton>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phtab, container, false);
        userPermission = AppSharedPreference.getUserPermission();
        initView(view);

        layoutFilter.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isFilterClicked = !isFilterClicked;

                if (isFilterClicked) {
                    layoutFilter.setBackgroundColor(Color.parseColor("#b1b8ba"));
                    imgFilter.setImageResource(R.drawable.filter_tap);

                    layoutMidPanel.setVisibility(View.VISIBLE);

                    //layoutMidPanel.animate().translationY(layoutMidPanel.getHeight());
                } else {
                    layoutFilter.setBackgroundColor(Color.WHITE);
                    imgFilter.setImageResource(R.drawable.filter_normal);

                    layoutMidPanel.setVisibility(View.GONE);

//
//                    if (type == 1) {
//                        processFetchHomework(URLHelper.URL_HOMEWORK);
//                    } else {
//                        processFetchHomework(URLHelper.URL_PROJECT);
                    }


                }


                //initApiCallSubject();

        });


        return view;
    }

    private void initView(View view) {
        layoutFilter = (LinearLayout)view.findViewById(R.id.layoutFilter);
        layoutMidPanel = (LinearLayout)view.findViewById(R.id.layoutMidPanel);
        imgFilter = (ImageView)view.findViewById(R.id.imgFilter);

        layoutFilter.setAlpha(1f);

        btnOne = (CustomTabButton) view.findViewById(R.id.btnOne);
        btnTwo = (CustomTabButton) view.findViewById(R.id.btnTwo);
        btnThree = (CustomTabButton) view.findViewById(R.id.btnThree);
        btnFour = (CustomTabButton) view.findViewById(R.id.btnFour);

        if (userPermission.isUserTasksSubmitTask()) {
            btnOne.setTitleText("Assignments");
            btnThree.setTitleText("My Submission");
            btnTwo.setVisibility(View.GONE);
            btnFour.setVisibility(View.GONE);
//            btnThree.setTitleText("All Assignment");
//            btnFour.setTitleText("All CM Submission");
        } else {
            btnOne.setTitleText("My Assignment");
            btnTwo.setTitleText("All Assignment");
            btnThree.setTitleText("My CM Submission");
            btnFour.setTitleText("All CM Submission");
        }

        listBtn.add(btnOne);
        listBtn.add(btnTwo);
        listBtn.add(btnThree);
        listBtn.add(btnFour);


        currentButton = btnOne;
        noticeType = "1";
        btnOne.setButtonSelected(true, getResources().getColor(R.color.black), R.drawable.eye_gray);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);

//        listViewNotice = (PullToRefreshListView)view.findViewById(R.id.listViewNotice);
        //setUpList();

//        listViewNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//                Notice data = (Notice) adapter.getItem(position-1);
//                Log.e("NOTICE_ID", "id: " + data.getNoticeId());
//                Intent intent = new Intent(getActivity(), SingleNoticeActivity.class);
//                intent.putExtra(AppConstant.ID_SINGLE_NOTICE, data.getNoticeId());
//                startActivityForResult(intent, 65);
//            }
//        });
        if (userPermission.isUserTasksSubmitTask())
            gotoAllAssignmentFragment();
        else
            gotoMyAssignmentFragment();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOne:
                currentButton = btnOne;
                btnOne.setButtonSelected(true, getResources().getColor(R.color.black), R.drawable.eye_gray);
                noticeType = "1";
                updateButtonUi();
                if (userPermission.isUserTasksSubmitTask())
                    gotoAllAssignmentFragment();
                else
                    gotoMyAssignmentFragment();


//                initializePageing();
//                initApiCall(pageNumber, noticeType);

                break;

            case R.id.btnTwo:
                currentButton = btnTwo;
                btnTwo.setButtonSelected(true, getResources().getColor(R.color.black), R.drawable.eye_gray);
                noticeType = "2";
                updateButtonUi();
                gotoAllAssignmentFragment();

//                initializePageing();
//                initApiCall(pageNumber, noticeType);

                break;

            case R.id.btnThree:
                currentButton = btnThree;
                btnThree.setButtonSelected(true, getResources().getColor(R.color.black), R.drawable.eye_gray);
                noticeType = "3";
                updateButtonUi();
                gotoCMSubmissionFragment();

//                initializePageing();
//                initApiCall(pageNumber, noticeType);

                break;
            case R.id.btnFour:
                currentButton = btnFour;
                btnFour.setButtonSelected(true, getResources().getColor(R.color.black), R.drawable.eye_gray);
                noticeType = "4";
                updateButtonUi();
                gotoAllCMSubmissionFragment();

//                initializePageing();
//                initApiCall(pageNumber, noticeType);

                break;

            default:
                break;
        }
    }

    private void updateButtonUi() {
        for (CustomTabButton btn : listBtn) {
            if (!btn.equals(currentButton)) {
                if (btn.equals(btnOne)) {
                    btn.setButtonSelected(false, getResources().getColor(R.color.black), R.drawable.eye_gray);
                }
                if (btn.equals(btnTwo)) {
                    btn.setButtonSelected(false, getResources().getColor(R.color.black), R.drawable.circular_gray);
                }
                if (btn.equals(btnThree)) {
                    btn.setButtonSelected(false, getResources().getColor(R.color.black), R.drawable.annaouncment_gray);
                }
                if (btn.equals(btnFour)) {
                    btn.setButtonSelected(false, getResources().getColor(R.color.black), R.drawable.annaouncment_gray);
                }
            }

        }
    }

    private void gotoMyAssignmentFragment() {
        MyAssignmentFragment myAssignmentFragment = new MyAssignmentFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.ph_container, myAssignmentFragment, "myAssignmentFragment");
        transaction.commit();
    }

    private void gotoAllAssignmentFragment() {
        PigeonholeFragment pigeonholeFragment = new PigeonholeFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (userPermission.isUserTasksSubmitTask())
        transaction.replace(R.id.ph_container, pigeonholeFragment, "pigeonholeFragment");
//        else
//            transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);
        transaction.commit();
    }

    private void gotoCMSubmissionFragment() {
        CMBoxFragment cmBoxFragment = new CMBoxFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (userPermission.isUserTasksSubmitTask())
        transaction.replace(R.id.ph_container, cmBoxFragment, "cmBoxFragment");
//        else
//            transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);
        transaction.commit();
    }
    private void gotoAllCMSubmissionFragment() {
        AllCMSubmissionFragment allCMSubmissionFragment = new AllCMSubmissionFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (userPermission.isUserTasksSubmitTask())
        transaction.replace(R.id.ph_container, allCMSubmissionFragment, "allCMSubmissionFragment");
//        else
//            transaction.replace(R.id.main_acitivity_container, pigeonholeFragment, "pigeonholeFragment").addToBackStack(null);
        transaction.commit();
    }

}
