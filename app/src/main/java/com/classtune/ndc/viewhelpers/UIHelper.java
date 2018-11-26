package com.classtune.ndc.viewhelpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.classtune.ndc.R;

import java.util.Calendar;

public class UIHelper {

	ProgressDialog loadingDialog;
    Activity activity;
    UIHelper uiHelper;

    public UIHelper(Activity activity) {
        this.activity = activity;
    }



    public void showMessage(String message)
	{
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
    
    public void showLoadingDialog(final String message) {
    	
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    loadingDialog = ProgressDialog.show(activity, "", message, true, false);
                }
            });
        }
    }

    public void updateLoadingDialog(final String message) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    loadingDialog.setMessage(message);
                }
            });
        }
    }

    public boolean isDialogActive() {
        if (loadingDialog != null) {
            return loadingDialog.isShowing();
        } else {
            return false;
        }
    }

    public void dismissLoadingDialog() {
        if (activity != null && loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(activity).setMessage(errorMessage).setTitle(R.string.java_uihelper_error).setPositiveButton(R.string.java_uihelper_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public void showSuccessDialog(String errorMessage, String title) {
        new AlertDialog.Builder(activity).setMessage(errorMessage).setTitle(title).setPositiveButton(R.string.java_uihelper_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).create().show();
    }
    
    public void showErrorDialogOnGuiThread(final String errorMessage) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(activity).setMessage(errorMessage).setTitle(R.string.java_uihelper_error).setPositiveButton(R.string.java_uihelper_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            dismissLoadingDialog();
                        }
                    }).create().show();
                }
            });
        }
    }
    
    public void showInternetConnectivityDialog(String conenctionMessage, String title) {
    	
    	final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
    	dialog.setMessage(conenctionMessage);
    	dialog.setTitle(title);
    	dialog.setPositiveButton(R.string.java_uihelper_settings, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				dialog.dismiss();
			}
		});
    	
    	dialog.setNegativeButton(R.string.java_uihelper_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
    	
    	dialog.create();
    	dialog.show();
    	
        
    }


    String date = "";
    String time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;
    public String eventDateDialog() {
        // Get Current Date

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        //tiemPicker();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                datePickerDialog.dismiss();
                return;
            }
        });
        return date;
    }

    public String tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

//                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
//                        eventTime.setText(hourOfDay + ":" + minute);
                        time = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        return time;
    }
    
}
