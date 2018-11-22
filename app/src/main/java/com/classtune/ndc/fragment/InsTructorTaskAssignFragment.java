package com.classtune.ndc.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsTructorTaskAssignFragment extends Fragment {
    LinearLayout layoutAssDate;

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

        layoutAssDate = (LinearLayout)view.findViewById(R.id.layoutAssDate);
        layoutAssDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatepicker();
            }
        });
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
}
