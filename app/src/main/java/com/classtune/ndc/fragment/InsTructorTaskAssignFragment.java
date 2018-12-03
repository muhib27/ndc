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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.adapter.AttachmentAdapter;
import com.classtune.ndc.adapter.TaskAssignAdapter;
import com.classtune.ndc.adapter.UserTaskAssignAdapter;
import com.classtune.ndc.apiresponse.Course;
import com.classtune.ndc.apiresponse.pigeonhole_api.GetCourseData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeGetCourseApiResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeTaskAdd;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.callbacks.IAttachFile;
import com.classtune.ndc.model.AttachmentModel;
import com.classtune.ndc.model.CMModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.VerticalSpaceItemDecoration;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.activity.VideoPickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;
import com.vincent.filepicker.filter.entity.VideoFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
//, IAttachFile
public class InsTructorTaskAssignFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    LinearLayout layoutDueDate, layoutAssignTo, commonTypeLayout;
    Button attachFileBtn, assignBtn;
    TextView dueDate, assignTo;
    EditText title, description;
    RadioGroup typeGroup, instituteGroup;
    RadioButton ndc, afwc, capston;
    Button common, custom;
    RecyclerView listView;
    TaskAssignAdapter userTaskAssignAdapter;
    private List<CMModel> cmModelList;
    List<Student> ndcStList;
    List<Student> afwcStList;
    List<Student> capstonStList;

    public List<String> courseList;
    public static List<String> selectedList;

    UIHelper uiHelper;

    List<AttachmentModel> attachmentModelList;
    PigeonholeTaskAdd pigeonholeTaskAdd;


    AttachmentAdapter attachmentAdapter, attachmentAdapterMain;

    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_CAMERA_PERM = 125;
    private static final int RC_STORAGE_CAMERA_PERM = 124;
    private ArrayList<String> listFiles;
    public static InsTructorTaskAssignFragment instance;
    ListView attachmentListMain;

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

        ndcStList = new ArrayList<>();
        afwcStList = new ArrayList<>();
        capstonStList = new ArrayList<>();
        uiHelper = new UIHelper(getActivity());
        initView(view);
        listFiles = new ArrayList<>();
        attachmentModelList = new ArrayList<>();
        attachmentListMain = view.findViewById(R.id.attachmentListMain);

        attachmentAdapter = new AttachmentAdapter(getActivity(), attachmentModelList);
        attachmentAdapter.notifyDataSetChanged();
        attachmentListMain.setAdapter(attachmentAdapter);

        layoutDueDate = (LinearLayout) view.findViewById(R.id.layoutDueDate);
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
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
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
        switch (v.getId()) {
            case R.id.attachFile:
                //showDialogAttachment();
                readStorageStateTask();
                break;
            case R.id.layoutAssignTo:
                callPigeonholeGetCourseApi();

                break;
            case R.id.assignBtn:
                initTaskAssignApi();
                break;
            case R.id.common:
                enableCommonSelection();
                break;
            case R.id.custom:
                enableCustomSelection();
                break;
            case R.id.btnBrowse:
                browseFile(SELECTED_TYPE);
                break;
        }
    }

    private void initTaskAssignApi() {

        pigeonholeTaskAdd = new PigeonholeTaskAdd();
        if (!title.getText().toString().trim().isEmpty() )
            pigeonholeTaskAdd.setTitle(title.getText().toString());
        if (!description.getText().toString().trim().isEmpty() )
            pigeonholeTaskAdd.setDescription(description.getText().toString());
        if (!dueDate.getText().toString().trim().isEmpty() )
            pigeonholeTaskAdd.setTitle(dueDate.getText().toString());

        if(ndcCount>0)
            courseList.add("ndc");
        if(afwcCount>0)
            courseList.add("afwc");
        if(capstonCount>0)
            courseList.add("capston");

        Toast.makeText(getActivity(), ""+courseList.size() , Toast.LENGTH_LONG).show();

     //   callTaskAddApi();

    }

    private void browseFile(String selected) {

        if (selected.equals("video")) {
            Intent intent1 = new Intent(getActivity(), VideoPickActivity.class);
            intent1.putExtra(IS_NEED_CAMERA, true);
            intent1.putExtra(Constant.MAX_NUMBER, 9);
            intent1.putExtra(IS_NEED_FOLDER_LIST, true);
            startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_VIDEO);
        } else if (selected.equals("audio")) {

            Intent intent2 = new Intent(getActivity(), AudioPickActivity.class);
            intent2.putExtra(IS_NEED_CAMERA, true);
            intent2.putExtra(Constant.MAX_NUMBER, 9);
            intent2.putExtra(IS_NEED_FOLDER_LIST, true);
            startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_AUDIO);
        } else if (selected.equals("image")) {
            Intent intent3 = new Intent(getActivity(), ImagePickActivity.class);
            intent3.putExtra(IS_NEED_RECORDER, true);
            intent3.putExtra(Constant.MAX_NUMBER, 9);
            intent3.putExtra(IS_NEED_FOLDER_LIST, true);
            startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_IMAGE);
        } else if (selected.equals("file")) {

            Intent intent4 = new Intent(getActivity(), NormalFilePickActivity.class);
            intent4.putExtra(Constant.MAX_NUMBER, 9);
            intent4.putExtra(IS_NEED_FOLDER_LIST, true);
            intent4.putExtra(NormalFilePickActivity.SUFFIX,
                    new String[]{"xlsx", "zip", "xls", "doc", "dOcX", "ppt", ".pptx", "pdf"});
            startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
        }

    }


    //    List<Subject> seletedItems;
    AlertDialog fileAttachDialog, batchDialog;
    //    DialogBatchAndSubjectAdapter batchAndSubjectAdapter;
    SearchView searchView;
    Button browseBtn;
    RadioGroup attachmentType;
    RadioButton rbVideo, rbAudio, rbImage, rbFile;
    private String SELECTED_TYPE = "video";
    TextView mTvResult;
    ListView attachmentList;
    AttachmentModel attachmentModel;

    private void showDialogAttachment() {
//        seletedItems = new ArrayList();

//        cmModelList = new ArrayList<>();
////        getCMData();
        attachmentModelList = new ArrayList<>();
        builder = new StringBuilder();
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View fileAttachDialogView = factory.inflate(R.layout.dialog_instructor_attachment, null);
        browseBtn = fileAttachDialogView.findViewById(R.id.btnBrowse);
        browseBtn.setOnClickListener(this);

        attachmentList = fileAttachDialogView.findViewById(R.id.attachmentList);
        attachmentAdapter = new AttachmentAdapter(getActivity(), attachmentModelList);
        attachmentAdapter.notifyDataSetChanged();
        attachmentList.setAdapter(attachmentAdapter);

        attachmentType = fileAttachDialogView.findViewById(R.id.attachmentType);
//        mTvResult = fileAttachDialogView.findViewById(R.id.tv_result);

        rbVideo = fileAttachDialogView.findViewById(R.id.video);
        rbAudio = fileAttachDialogView.findViewById(R.id.audio);
        rbImage = fileAttachDialogView.findViewById(R.id.image);
        rbFile = fileAttachDialogView.findViewById(R.id.file);


        attachmentModel = new AttachmentModel();


        attachmentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.video) {

                    SELECTED_TYPE = "video";
                    //some code
                } else if (checkedId == R.id.audio) {
                    SELECTED_TYPE = "audio";

                } else if (checkedId == R.id.image) {

                    SELECTED_TYPE = "image";
                } else if (checkedId == R.id.file) {

                    SELECTED_TYPE = "file";
                }

            }
        });
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
        fileAttachDialog = new AlertDialog.Builder(getActivity()).create();
        fileAttachDialog.setView(fileAttachDialogView);
        fileAttachDialogView.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                fileAttachDialog.dismiss();
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

        fileAttachDialog.show();

    }

    public static int ndcCount, afwcCount, capstonCount;
    private void showDialogAssignTo() {

        ndcCount = 0;
        afwcCount = 0;
        capstonCount = 0;
        selectedList = new ArrayList<>();
        courseList = new ArrayList<>();
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

        instituteGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.ndc) {

                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc");
                    userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);

                    userTaskAssignAdapter.clear();
                    userTaskAssignAdapter.addAllData(ndcStList);
                    //some code
                } else if(checkedId == R.id.afwc) {
                    //some code
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "afwc");
                    userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    userTaskAssignAdapter.clear();
                    userTaskAssignAdapter.addAllData(afwcStList);

                }
                else if(checkedId == R.id.capston) {
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "capston");
                    userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    userTaskAssignAdapter.clear();
                    userTaskAssignAdapter.addAllData(capstonStList);

                }

            }
        });


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
        batchDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 800);

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

    LinearLayoutManager linearLayoutManager;
    private void enableCustomSelection() {
        cmModelList = new ArrayList<>();
        getCMData();
        custom.setTextColor(custom.getContext().getResources().getColor(R.color.white));
        custom.setBackgroundColor(custom.getContext().getResources().getColor(R.color.ndc_color));
        common.setBackgroundColor(common.getContext().getResources().getColor(R.color.ash));
        common.setTextColor(common.getContext().getResources().getColor(R.color.ndc_color));
        commonTypeLayout.setVisibility(View.GONE);
        ndc.setChecked(true);

        instituteGroup.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);


        userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc");
//        userTaskAssignAdapter.notifyDataSetChanged();
//        listView.setAdapter(userTaskAssignAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
//                linearLayoutManager.getOrientation());
//        rv.addItemDecoration(dividerItemDecoration);
        listView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        listView.setLayoutManager(linearLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(userTaskAssignAdapter);

        userTaskAssignAdapter.addAllData(ndcStList);
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

    StringBuilder builder;

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
        } else if (requestCode == Constant.REQUEST_CODE_PICK_VIDEO) {

            if (resultCode == RESULT_OK) {
                ArrayList<VideoFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
//                StringBuilder builder = new StringBuilder();
                for (VideoFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");
                    attachmentModelList.add(new AttachmentModel(f.getName(), path));
                }
                attachmentAdapter.notifyDataSetChanged();
                //mTvResult.setText(builder.toString());
//                attachmentAdapter = new AttachmentAdapter(getActivity(), attachmentModelList);
//                //attachmentAdapter.setData(attachmentModelList);
//                attachmentAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_AUDIO) {

            if (resultCode == RESULT_OK) {
                ArrayList<AudioFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);
//                StringBuilder builder = new StringBuilder();
                for (AudioFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");

                    attachmentModelList.add(new AttachmentModel(f.getName(), path));
                }
                attachmentAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
//                StringBuilder builder = new StringBuilder();
                for (ImageFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");
                    attachmentModelList.add(new AttachmentModel(f.getName(), path));
                }
                attachmentAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

            if (resultCode == RESULT_OK) {
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
//                StringBuilder builder = new StringBuilder();
                for (NormalFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");
                    attachmentModelList.add(new AttachmentModel(f.getName(), path));
                }
                attachmentAdapter.notifyDataSetChanged();
            }
        }
    }

    @AfterPermissionGranted(RC_STORAGE_CAMERA_PERM)
    public void readStorageStateTask() {
        if (hasStorageAndCameraPermission()) {
            // Have permission, do the thing!
            // Toast.makeText(this, "TODO: Phone State things", Toast.LENGTH_LONG).show();
            //validateFieldAndCallLogIn();
            //showChooser();
            showDialogAttachment();
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


    private void callTaskAddApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
//        uiHelper.showLoadingDialog("Authenticating...");


        RetrofitApiClient.getApiInterface().getTaskAssign(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();
//                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (value.code() == 200) {
                            Log.v("PigeonholeFragment", value.message());
                            //  AppSharedPreference.setUserBasicInfo(menuApiResponse.getMenuData().getUser());

//                            User user1 = AppSharedPreference.getUserBasicInfo();

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


    private void callPigeonholeGetCourseApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
//        uiHelper.showLoadingDialog("Authenticating...");


        RetrofitApiClient.getApiInterface().getPigeonholeCourses(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<PigeonholeGetCourseApiResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<PigeonholeGetCourseApiResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        PigeonholeGetCourseApiResponse pigeonholeGetCourseApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (pigeonholeGetCourseApiResponse.getCode() == 200) {
                            Log.v("PigeonholeFragment", value.message());
                            parseCourseData(pigeonholeGetCourseApiResponse.getCourseData().getCourses());
                            showDialogAssignTo();
                            //  AppSharedPreference.setUserBasicInfo(menuApiResponse.getMenuData().getUser());

//                            User user1 = AppSharedPreference.getUserBasicInfo();

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

    private void parseCourseData(List<Course> courses) {
        for(int i=0; i<courses.size(); i++){
            if(courses.get(i).getName().equalsIgnoreCase("ndc")) {

                ndcStList = courses.get(i).getStudents();
//                for(int k=0; k<20; k++)
//                ndcStList.add(courses.get(i).getStudents().get(0));
            }
            else if(courses.get(i).getName().equalsIgnoreCase("afwc"))
                afwcStList = courses.get(i).getStudents();
            else if(courses.get(i).getName().equalsIgnoreCase("capston"))
                capstonStList = courses.get(i).getStudents();
        }
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
