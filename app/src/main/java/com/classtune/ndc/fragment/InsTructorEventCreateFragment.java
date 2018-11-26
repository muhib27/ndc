package com.classtune.ndc.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.viewhelpers.UIHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsTructorEventCreateFragment extends Fragment implements View.OnClickListener {
    LinearLayout layoutDueDate;
    Button eventDate, eventTime;
    UIHelper uiHelper;





    public InsTructorEventCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ins_tructor_event_create, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        uiHelper = new UIHelper(getActivity());

        initView(view);

    }

    private void initView(View view) {
        eventDate = view.findViewById(R.id.eventDate);
        eventTime = view.findViewById(R.id.eventTime);

        eventDate.setOnClickListener(this);
//        eventTime.setOnClickListener(this);

        eventTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eventTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Event Time");
                mTimePicker.show();

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eventDate:
//                String date = "Event Date";
//                if(!uiHelper.eventDateDialog().isEmpty() || uiHelper.eventDateDialog()!=null)
//                    date = uiHelper.eventDateDialog();
//                eventDate.setText(date);
                showDatepicker();
                break;
//            case R.id.eventTime:
//                eventTime.setText(uiHelper.tiemPicker());
//                break;
        }
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
            eventDate.setText(dateFormatApp);
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
