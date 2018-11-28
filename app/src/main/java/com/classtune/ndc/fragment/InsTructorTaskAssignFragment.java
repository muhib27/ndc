package com.classtune.ndc.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.UserTaskAssignAdapter;
import com.classtune.ndc.callbacks.IAttachFile;
import com.classtune.ndc.model.CMModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
//, IAttachFile
public class InsTructorTaskAssignFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks,EasyPermissions.RationaleCallbacks{
    LinearLayout layoutDueDate, layoutAssignTo, commonTypeLayout;
    Button attachFileBtn, assignBtn;
    TextView dueDate, assignTo;
    RadioGroup typeGroup, instituteGroup;
    RadioButton  ndc, afwc, capston;
    Button common, custom;
    ListView listView;
    UserTaskAssignAdapter userTaskAssignAdapter;
    private List<CMModel> cmModelList;


    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_CAMERA_PERM = 125;
    private static final int RC_STORAGE_CAMERA_PERM = 124;
    private ArrayList<String> listFiles;
    public static InsTructorTaskAssignFragment instance;

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
        listFiles = new ArrayList<>();

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
        layoutAssignTo = view.findViewById(R.id.layoutAssignTo);
        layoutAssignTo.setOnClickListener(this);
        assignTo = view.findViewById(R.id.assign_to);
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
                //showDialogAttachment();
                readStorageStateTask();
                break;
            case R.id.layoutAssignTo:
                showDialogAssignTo();
                break;
            case R.id.assignBtn:
                break;
            case R.id.common:
                enableCommonSelection();
                break;
            case R.id.custom:
                enableCustomSelection();
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

    private void showDialogAssignTo() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View assignToDialogView = factory.inflate(R.layout.dialog_instructor_assign_to, null);

//        typeGroup = assignToDialogView.findViewById(R.id.typeGroup);
        instituteGroup = assignToDialogView.findViewById(R.id.instituteGroup);

        common = assignToDialogView.findViewById(R.id.common);
        custom = assignToDialogView.findViewById(R.id.custom);

        common.setOnClickListener(this);
        custom.setOnClickListener(this);

        ndc = assignToDialogView.findViewById(R.id.ndc);
        afwc = assignToDialogView.findViewById(R.id.afwc);
        capston = assignToDialogView.findViewById(R.id.capston);

        listView = assignToDialogView.findViewById(R.id.list);

        commonTypeLayout = assignToDialogView.findViewById(R.id.commonTypeLayout);
        enableCommonSelection();

//        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if (checkedId == R.id.common) {
//                    common.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_background_assign));
//                    enableCommonSelection();
//
//                    //some code
//                } else if(checkedId == R.id.custom) {
//                    //some code
//                    custom.setChecked(true);
//                    enableCustomSelection();
//
//                }
//
//            }
//        });


        batchDialog = new AlertDialog.Builder(getActivity()).create();
        batchDialog.setView(assignToDialogView);
        assignToDialogView.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                batchDialog.dismiss();
            }
        });

        batchDialog.show();

    }

    private void enableCommonSelection() {
        common.setTextColor(common.getContext().getResources().getColor(R.color.white));
        common.setBackgroundColor(common.getContext().getResources().getColor(R.color.ndc_color));
        custom.setBackgroundColor(custom.getContext().getResources().getColor(R.color.ash));
        custom.setTextColor(custom.getContext().getResources().getColor(R.color.ndc_color));
        commonTypeLayout.setVisibility(View.VISIBLE);

        instituteGroup.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }
//    private void disableCommonSelection() {
//
//        instituteGroup.setVisibility(View.GONE);
//        listView.setVisibility(View.GONE);
//
//    }
//

    private void enableCustomSelection() {
        cmModelList = new ArrayList<>();
        getCMData();
        custom.setTextColor(custom.getContext().getResources().getColor(R.color.white));
        custom.setBackgroundColor(custom.getContext().getResources().getColor(R.color.ndc_color));
        common.setBackgroundColor(common.getContext().getResources().getColor(R.color.ash));
        common.setTextColor(common.getContext().getResources().getColor(R.color.ndc_color));
        commonTypeLayout.setVisibility(View.GONE);

        instituteGroup.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);


        userTaskAssignAdapter = new UserTaskAssignAdapter(getActivity(), cmModelList);
        userTaskAssignAdapter.notifyDataSetChanged();
        listView.setAdapter(userTaskAssignAdapter);
    }

    private void getCMData() {
        CMModel cmModel = new CMModel("11", "nnn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
        cmModel = new CMModel("22", "mmn", "0");
        cmModelList.add(cmModel);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        showChooser();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
//    private void disableCustomSelection() {
//        commonTypeLayout.setVisibility(View.GONE);
//
//        instituteGroup.setVisibility(View.VISIBLE);
//        listView.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    getActivity(),
                    getString(R.string.returned_from_app_settings_to_activity_storage,
                            hasStorageAndCameraPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    @AfterPermissionGranted(RC_STORAGE_CAMERA_PERM)
    public void readStorageStateTask() {
        if (hasStorageAndCameraPermission()) {
            // Have permission, do the thing!
            // Toast.makeText(this, "TODO: Phone State things", Toast.LENGTH_LONG).show();
            //validateFieldAndCallLogIn();
            showChooser();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage_camera),
                    RC_STORAGE_CAMERA_PERM,
                    STORAGE_AND_CAMERA);
        }
    }

    private boolean hasStorageAndCameraPermission() {
        return EasyPermissions.hasPermissions(getActivity(), STORAGE_AND_CAMERA);
    }

    private void showChooser() {
        instance = this;
       // showFileDialog();
    }
//    private void showFileDialog() {
//        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//        alertDialog.setTitle(getString(R.string.app_name));
//        alertDialog.setMessage(getString(R.string.file_chooser_message));
//        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.file_chooser_type_photo),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
////                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
////                                == PackageManager.PERMISSION_DENIED)
//
//                        listFiles.clear();
//                        FilePickerBuilder.getInstance().setMaxCount(1)
//                                .setSelectedFiles(listFiles)
//                                .setActivityTheme(R.style.LibAppTheme)
//                                //.setActivityTheme(R.style.CustomAppCompatTheme)
//                                .pickPhoto(getActivity());
//                    }
//                });
//
//        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.file_chooser_type_doc),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
////						if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////							//isStoragePermissionGranted();
////							checkPermission();
////						}
//
//                        String[] zips = {".zip", ".rar"};
//                        listFiles.clear();
//                        FilePickerBuilder.getInstance().setMaxCount(1)
//                                .setSelectedFiles(listFiles)
//                                //.setActivityTheme(R.style.CustomAppCompatTheme)
//                                .setActivityTheme(R.style.LibAppTheme)
//                                .addFileSupport("ZIP", zips)
//                                .pickFile(getActivity());
//                    }
//                });
//
//
//        alertDialog.show();
//    }

//    @Override
//    public void onAttachCallBack(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case FilePickerConst.REQUEST_CODE_PHOTO:
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    listFiles.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
//                    if (listFiles.size() > 0) {
//                        String fileNamePath = listFiles.get(0);
//                        String fileName = fileNamePath.substring(fileNamePath.lastIndexOf("/") + 1);
//
////                        selectedFilePath = fileNamePath;
////
////
////                        mimeType = ApplicationSingleton.getInstance().getMimeType(selectedFilePath);
////                        File myFile = new File(selectedFilePath);
////                        fileSize = String.valueOf(myFile.length());
////
////                        Log.e("MIME_TYPE", "is: " + ApplicationSingleton.getInstance().getMimeType(selectedFilePath));
////                        Log.e("FILE_SIZE", "is: " + fileSize);
////
////                        long fileSizeInKB = myFile.length() / 1024;
////                        long fileSizeInMB = fileSizeInKB / 1024;
////
////                        if (fileSizeInMB <= 5) {
////                            choosenFileTextView.setText(fileName);
////                        } else {
////                            selectedFilePath = "";
////                            mimeType = "";
////                            fileSize = "";
////                            Toast.makeText(getActivity(), R.string.java_teacherhomeworkaddfragment_file_size_message, Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                }
//                break;
//            case FilePickerConst.REQUEST_CODE_DOC:
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    listFiles.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
//                    if (listFiles.size() > 0) {
//                        String fileNamePath = listFiles.get(0);
//                        String fileName = fileNamePath.substring(fileNamePath.lastIndexOf("/") + 1);
//
////                        selectedFilePath = fileNamePath;
////
////
////                        mimeType = ApplicationSingleton.getInstance().getMimeType(selectedFilePath);
////                        File myFile = new File(selectedFilePath);
////                        fileSize = String.valueOf(myFile.length());
////
////                        Log.e("MIME_TYPE", "is: " + ApplicationSingleton.getInstance().getMimeType(selectedFilePath));
////                        Log.e("FILE_SIZE", "is: " + fileSize);
////
////                        long fileSizeInKB = myFile.length() / 1024;
////                        long fileSizeInMB = fileSizeInKB / 1024;
////
////                        if (fileSizeInMB <= 5) {
////                            choosenFileTextView.setText(fileName);
////                        } else {
////                            selectedFilePath = "";
////                            mimeType = "";
////                            fileSize = "";
////                            Toast.makeText(getActivity(), R.string.java_teacherhomeworkaddfragment_file_size_message, Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                }
//                break;
//
//        }
//
//        instance = null;
//    }
}
