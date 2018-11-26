package com.classtune.ndc.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsTructorTaskAssignFragment extends Fragment implements View.OnClickListener {
    LinearLayout layoutDueDate;
    Button attachFileBtn, assignBtn;
    TextView dueDate;

    public InsTructorTaskAssignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ins_tructor_task_assign, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView(view);

        layoutDueDate = (LinearLayout)view.findViewById(R.id.layoutDueDate);
        layoutDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatepicker();
            }
        });
    }

    private void initView(View view) {
        dueDate = view.findViewById(R.id.due_date);
        attachFileBtn = view.findViewById(R.id.attachFile);
        assignBtn = view.findViewById(R.id.assignBtn);

        attachFileBtn.setOnClickListener(this);
        assignBtn.setOnClickListener(this);

    }

    private void showDatepicker() {
        DatePickerFragment picker = new DatePickerFragment();
        picker.setCallbacks(datePickerCallback);
        picker.show(getFragmentManager(), "datePicker");
    }
    DatePickerFragment.DatePickerOnSetDateListener datePickerCallback = new DatePickerFragment.DatePickerOnSetDateListener() {

        @Override
        public void onDateSelected(int month, String monthName, int day,
                                   int year, String dateFormatServer, String dateFormatApp,
                                   Date date) {
            // TODO Auto-generated method stub
            dueDate.setText(dateFormatApp);
//            choosenDateTextView.setText(dateFormatApp);
//            dateFormatServerString = dateFormatServer;
        }

		/*
		 * @Override public void onDateSelected(String monthName, int day, int
		 * year) { // TODO Auto-generated method stub Date date; try { date =
		 * new SimpleDateFormat("MMMM").parse(monthName); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(date); String dateString = day +
		 * "-" + cal.get(Calendar.MONTH) + "-" + year;
		 * choosenDateTextView.setText(dateString); } catch (ParseException e) {
		 * // TODO Auto-generated catch block Log.e("ERROR", e.toString()); } }
		 */
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attachFile:
                showDialogAttachment();
                break;
            case R.id.assignBtn:
                break;
        }
    }






//    List<Subject> seletedItems;
    AlertDialog batchDialog;
//    DialogBatchAndSubjectAdapter batchAndSubjectAdapter;
    SearchView searchView;

    private void showDialogAttachment() {
//        seletedItems = new ArrayList();


        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View batchDialogView = factory.inflate(R.layout.dialog_instructor_attachment, null);
//        ListView listView = (ListView) batchDialogView.findViewById(R.id.listView);
//        searchView = batchDialogView.findViewById(R.id.search);
//        batchAndSubjectAdapter = new DialogBatchAndSubjectAdapter(getActivity(), seletedItems, switchStatus, TeacherHomeWorkAddFragment.this);
//        batchAndSubjectAdapter.notifyDataSetChanged();
//        listView.setAdapter(batchAndSubjectAdapter);
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchView.setIconified(false);
//            }
//        });


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                seletedItems = getSearchItems(subjectList, newText);
//                batchAndSubjectAdapter.setData(seletedItems);
//                batchAndSubjectAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });

//        TextView text = (TextView) batchDialogView.findViewById(R.id.text_dialog);
//        text.setText(st);
        batchDialog = new AlertDialog.Builder(getActivity()).create();
        batchDialog.setView(batchDialogView);
        batchDialogView.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                batchDialog.dismiss();
            }
        });
//        batchDialogView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int t = batchAndSubjectAdapter.dTempList.size();
//                listSubjectId.clear();
//                listSubjectName.clear();
//                for(int i=0; i<batchAndSubjectAdapter.dTempList.size();i++){
//                    listSubjectId.add(batchAndSubjectAdapter.dTempList.get(i).getId());
//                    listSubjectName.add(batchAndSubjectAdapter.dTempList.get(i).getName());
//                }
//                subjectNameTextView.setText(getNameWithComma());
//                subjectId = getIdWithComma();
//                batchDialog.dismiss();
//            }
//        });

        batchDialog.show();

    }
}
