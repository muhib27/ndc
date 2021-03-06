package com.classtune.ndc.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.classtune.ndc.adapter.AttachmentAdapterMain;
import com.classtune.ndc.adapter.TaskAssignAdapter;
import com.classtune.ndc.adapter.TaskAssignAttachmentConfirmAdapter;
import com.classtune.ndc.adapter.TaskAssignConfirmAdapter;
import com.classtune.ndc.apiresponse.Attachment;
import com.classtune.ndc.apiresponse.Course;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHSingleTask;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewData;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTaskViewResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeGetCourseApiResponse;
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeTaskAdd;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.model.AttachmentModel;
import com.classtune.ndc.model.CMModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.AppUtility;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.SmallVerticalSpaceItemDecoration;
import com.classtune.ndc.utils.URLHelper;
import com.classtune.ndc.utils.UserCourses;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
public class InsTructorTaskAssignFragment_old extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    LinearLayout layoutDueDate, layoutAssignTo, commonTypeLayout;
    Button attachFileBtn, assignBtn;
    TextView dueDate, assignTo;
    EditText title, description;
    RadioGroup typeGroup, instituteGroup;
    RadioButton ndc, afwc, capston;
    CheckBox allNdc, allAfwc, allCapston;
    Button common, custom;
    RecyclerView listView;
    TaskAssignAdapter userTaskAssignAdapter;
    private List<CMModel> cmModelList;
    List<Student> ndcStList;
    List<Student> afwcStList;
    List<Student> capstonStList;

    public ArrayList<String> courseList;
    public static ArrayList<String> selectedList;

    UIHelper uiHelper;

    List<AttachmentModel> attachmentModelList;
    PigeonholeTaskAdd pigeonholeTaskAdd;


    AttachmentAdapter attachmentAdapter;
    AttachmentAdapterMain attachmentAdapterMain;
    public String selectedDate = "";

    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_CAMERA_PERM = 125;
    private static final int RC_STORAGE_CAMERA_PERM = 124;
    private ArrayList<String> listFiles;
    public static InsTructorTaskAssignFragment_old instance;
    ListView attachmentListMain;
    public static String id = "";

    public InsTructorTaskAssignFragment_old() {
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
        selectedList = new ArrayList<>();
        courseList = new ArrayList<>();
        uiHelper = new UIHelper(getActivity());
        initView(view);
        listFiles = new ArrayList<>();
        attachmentModelList = new ArrayList<>();
        attachmentListMain = view.findViewById(R.id.attachmentListMain);
//        AttachmentModel attachmentModel = new AttachmentModel("dd", "ddddddddd");
//
//        attachmentModelList.add(attachmentModel);
//        attachmentModel = new AttachmentModel("dd", "ddddddddd");
//        attachmentModelList.add(attachmentModel);
//        attachmentModel = new AttachmentModel("dd", "ddddddddd");
//        attachmentModelList.add(attachmentModel);
//        attachmentModel = new AttachmentModel("dd", "ddddddddd");
//        attachmentModelList.add(attachmentModel);

        attachmentAdapterMain = new AttachmentAdapterMain(getActivity(), attachmentModelList);
        attachmentAdapterMain.notifyDataSetChanged();
        attachmentListMain.setAdapter(attachmentAdapterMain);

        layoutDueDate = (LinearLayout) view.findViewById(R.id.layoutDueDate);
        layoutDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatepicker();

                // selectDate();
            }
        });

        id = "";

        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                id = b.getString("id", "");
        }
        if (id != null && !id.isEmpty())
            callTaskApi(id);
    }

    int year, month, day;
    DatePickerDialog datePickerDialog;

//    private void selectDate() {
//        datePickerDialog = new DatePickerDialog(getActivity(),
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                       // eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                }, year, month, day);
//        datePickerDialog.show();
//
//    }


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
        selectedDate = "";
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
            selectedDate = dateFormatServer;
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
                //assignToPopupMenu();
                callPigeonholeGetCourseApi();

                break;
            case R.id.assignBtn:
                if(assignBtn.getText().toString().equalsIgnoreCase("Update"))
                    initTaskEditApi();
                else
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

    private void assignToPopupMenu() {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), assignTo);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_assign_to, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.common:
//                        //callPigeonholeGetCourseApi("common");
//                        break;
//                    case R.id.custom:
//                        callPigeonholeGetCourseApi("custom");
//                        break;
//                }
//                Toast.makeText(
//                        getActivity(),
//                        "You Clicked : " + item.getTitle(),
//                        Toast.LENGTH_SHORT
//                ).show();
                return true;
            }
        });

        popup.show(); //showing popup menu
    }

    private void initTaskAssignApi() {

        pigeonholeTaskAdd = new PigeonholeTaskAdd();
        if (!title.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setTitle(title.getText().toString());
        if (!description.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setDescription(description.getText().toString());
        if (!dueDate.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setTitle(dueDate.getText().toString());

        UserCourses userCourses = AppSharedPreference.getUserCourse();
        if ((allNdc!=null && allNdc.isChecked()) || (allAfwc !=null && allAfwc.isChecked()) || (allCapston != null && allCapston.isChecked())) {
            selectedList = new ArrayList<>();
            if (allNdc!=null && allNdc.isChecked() && ndcStList.size() > 0) {
                if (userCourses.isNdc())
                    courseList.add("1");
                for (Student list : ndcStList) {
                    selectedList.add(list.getId());
                }
            }
            if (allAfwc != null && allAfwc.isChecked() && afwcStList.size() > 0) {
                if (userCourses.isAfwc())
                    courseList.add("2");
                for (Student list : afwcStList) {
                    selectedList.add(list.getId());
                }
            }
            if (allCapston != null && allCapston.isChecked() && capstonStList.size() > 0) {
                if (userCourses.isCapston())
                    courseList.add("3");
                for (Student list : capstonStList) {
                    selectedList.add(list.getId());
                }
            }
        } else {
            if (ndcCount > 0) {
                if (userCourses.isNdc())
                    courseList.add("1");
            }
            if (afwcCount > 0) {
                if (userCourses.isAfwc())
                    courseList.add("2");
            }
            if (capstonCount > 0) {
                if (userCourses.isCapston())
                    courseList.add("3");
            }
        }

        String titleSt = title.getText().toString().trim();
        String descriptionSt = description.getText().toString().trim();

        if (titleSt.isEmpty() || selectedList.size() <= 0) {
            Toast.makeText(getActivity(), "Title and Assign To should not be empty", Toast.LENGTH_LONG).show();
            return;
        }


//        Toast.makeText(getActivity(), ""+courseList.size() , Toast.LENGTH_LONG).show();
        confirmDialog();
//        callTaskAddApi();

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
        fileAttachDialogView.findViewById(R.id.btnAttach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
//               attachmentAdapterMain = new AttachmentAdapterMain(getActivity(), attachmentModelList);
//                attachmentAdapterMain.notifyDataSetChanged();
//                attachmentListMain.setAdapter(attachmentAdapterMain);
                attachmentAdapterMain.setData(attachmentModelList);
                attachmentAdapterMain.notifyDataSetChanged();

                if(!id.isEmpty() && attachmentModelsEdit!=null && attachmentModelsEdit.size()>0)
                {
                    attachmentAdapterMain.AddData(attachmentModelsEdit);
                }
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
        UserCourses userCourses = AppSharedPreference.getUserCourse();

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


        allNdc = assignToDialogView.findViewById(R.id.all_ndc);
        allCapston = assignToDialogView.findViewById(R.id.all_capston);
        allAfwc = assignToDialogView.findViewById(R.id.all_afwc);


        ndc = assignToDialogView.findViewById(R.id.ndc);
        afwc = assignToDialogView.findViewById(R.id.afwc);
        capston = assignToDialogView.findViewById(R.id.capston);

        if (userCourses.isNdc()) {
            allNdc.setVisibility(View.VISIBLE);
            ndc.setVisibility(View.VISIBLE);
        } else {
            allNdc.setVisibility(View.INVISIBLE);
            ndc.setVisibility(View.INVISIBLE);
        }

        if (userCourses.isAfwc()) {
            allAfwc.setVisibility(View.VISIBLE);
            afwc.setVisibility(View.VISIBLE);
        } else {
            allAfwc.setVisibility(View.INVISIBLE);
            afwc.setVisibility(View.INVISIBLE);
        }

        if (userCourses.isCapston()) {

            allCapston.setVisibility(View.VISIBLE);
            capston.setVisibility(View.VISIBLE);
        } else {
            allCapston.setVisibility(View.INVISIBLE);
            capston.setVisibility(View.INVISIBLE);
        }

        listView = assignToDialogView.findViewById(R.id.list);


        commonTypeLayout = assignToDialogView.findViewById(R.id.commonTypeLayout);
        if (!SELECTED_TAB.isEmpty() && SELECTED_TAB.equalsIgnoreCase("custom"))
            enableCustomSelection();
        else
            enableCommonSelection();

        instituteGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.ndc) {
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && ndcStList!=null) {
                        for (int i = 0; i < ndcStList.size(); i++) {
                            if (editSelectedList.contains(ndcStList.get(i).getId()))
                                ndcStList.get(i).setSelected(true);
                        }
                    }

                    userTaskAssignAdapter.clear();

                    userTaskAssignAdapter.addAllData(ndcStList);
                    //some code
                } else if (checkedId == R.id.afwc) {
                    //some code
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "afwc", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && afwcStList!=null) {
                        for (int i = 0; i < afwcStList.size(); i++) {
                            if (editSelectedList.contains(afwcStList.get(i).getId()))
                                afwcStList.get(i).setSelected(true);
                        }
                    }
                    userTaskAssignAdapter.clear();
                    if (afwcStList.size() > 0) {
                        userTaskAssignAdapter.addAllData(afwcStList);
                    }

                } else if (checkedId == R.id.capston) {

                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "capston", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && capstonStList!=null) {
                        for (int i = 0; i < capstonStList.size(); i++) {
                            if (editSelectedList.contains(capstonStList.get(i).getId()))
                                capstonStList.get(i).setSelected(true);
                        }
                    }
                    userTaskAssignAdapter.clear();
                    {
                        userTaskAssignAdapter.addAllData(capstonStList);
                    }

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

    private void showDialogAssignToCommon() {
        UserCourses userCourses = AppSharedPreference.getUserCourse();

        ndcCount = 0;
        afwcCount = 0;
        capstonCount = 0;
        selectedList = new ArrayList<>();
        courseList = new ArrayList<>();
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View assignToDialogView = factory.inflate(R.layout.dialog_instructor_assign_to_common, null);



        allNdc = assignToDialogView.findViewById(R.id.all_ndc);
        allCapston = assignToDialogView.findViewById(R.id.all_capston);
        allAfwc = assignToDialogView.findViewById(R.id.all_afwc);


        if (userCourses.isNdc()) {
            allNdc.setVisibility(View.VISIBLE);
//            ndc.setVisibility(View.VISIBLE);
        } else {
            allNdc.setVisibility(View.INVISIBLE);
//            ndc.setVisibility(View.INVISIBLE);
        }

        if (userCourses.isAfwc()) {
            allAfwc.setVisibility(View.VISIBLE);
//            afwc.setVisibility(View.VISIBLE);
        } else {
            allAfwc.setVisibility(View.INVISIBLE);
//            afwc.setVisibility(View.INVISIBLE);
        }

        if (userCourses.isCapston()) {

            allCapston.setVisibility(View.VISIBLE);
//            capston.setVisibility(View.VISIBLE);
        } else {
            allCapston.setVisibility(View.INVISIBLE);
//            capston.setVisibility(View.INVISIBLE);
        }

        if(SELECTED_TAB != null && SELECTED_TAB.equalsIgnoreCase("common") && editCourseListSt!=null && editCourseListSt.size()>0)
        {
            for(int i=0; i< editCourseListSt.size(); i++){
                if(editCourseListSt.get(i).equals("1"))
                    allNdc.setChecked(true);
                else if(editCourseListSt.get(i).equals("2"))
                    allAfwc.setChecked(true);
                else if(editCourseListSt.get(i).equals("3"))
                    allCapston.setChecked(true);

            }
        }

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

    private void showDialogAssignToCustom() {
        UserCourses userCourses = AppSharedPreference.getUserCourse();

        ndcCount = 0;
        afwcCount = 0;
        capstonCount = 0;
        selectedList = new ArrayList<>();
        courseList = new ArrayList<>();
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View assignToDialogView = factory.inflate(R.layout.dialog_instructor_assign_to_custom, null);

//        typeGroup = assignToDialogView.findViewById(R.id.typeGroup);
        instituteGroup = assignToDialogView.findViewById(R.id.instituteGroup);


        ndc = assignToDialogView.findViewById(R.id.ndc);
        afwc = assignToDialogView.findViewById(R.id.afwc);
        capston = assignToDialogView.findViewById(R.id.capston);


        listView = assignToDialogView.findViewById(R.id.list);

        ndc.setChecked(true);

        instituteGroup.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);


        userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc", editSelectedList, SELECTED_TAB);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        listView.addItemDecoration(new SmallVerticalSpaceItemDecoration(getResources()));
        listView.setLayoutManager(linearLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(userTaskAssignAdapter);

//        if (editSelectedList != null && editSelectedList.size() > 0 && afwcStList!=null) {
//            for (int i = 0; i < afwcStList.size(); i++) {
//                if (editSelectedList.contains(afwcStList.get(i).getId()))
//                    afwcStList.get(i).setSelected(true);
//            }
//        }
        userTaskAssignAdapter.addAllData(ndcStList);


//        commonTypeLayout = assignToDialogView.findViewById(R.id.commonTypeLayout);
//        if (!SELECTED_TAB.isEmpty() && SELECTED_TAB.equalsIgnoreCase("custom"))
//            enableCustomSelection();
//        else
//            enableCommonSelection();

        instituteGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.ndc) {
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && ndcStList!=null) {
                        for (int i = 0; i < ndcStList.size(); i++) {
                            if (editSelectedList.contains(ndcStList.get(i).getId()))
                                ndcStList.get(i).setSelected(true);
                        }
                    }

                    userTaskAssignAdapter.clear();

                    userTaskAssignAdapter.addAllData(ndcStList);
                    //some code
                } else if (checkedId == R.id.afwc) {
                    //some code
                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "afwc", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && afwcStList!=null) {
                        for (int i = 0; i < afwcStList.size(); i++) {
                            if (editSelectedList.contains(afwcStList.get(i).getId()))
                                afwcStList.get(i).setSelected(true);
                        }
                    }
                    userTaskAssignAdapter.clear();
                    if (afwcStList.size() > 0) {
                        userTaskAssignAdapter.addAllData(afwcStList);
                    }

                } else if (checkedId == R.id.capston) {

                    userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "capston", editSelectedList, SELECTED_TAB);
                    //userTaskAssignAdapter.notifyDataSetChanged();
                    listView.setAdapter(userTaskAssignAdapter);
                    if (editSelectedList != null && editSelectedList.size() > 0 && capstonStList!=null) {
                        for (int i = 0; i < capstonStList.size(); i++) {
                            if (editSelectedList.contains(capstonStList.get(i).getId()))
                                capstonStList.get(i).setSelected(true);
                        }
                    }
                    userTaskAssignAdapter.clear();
                    {
                        userTaskAssignAdapter.addAllData(capstonStList);
                    }

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
        custom.setTextColor(custom.getContext().getResources().getColor(R.color.black));
        commonTypeLayout.setVisibility(View.VISIBLE);

        instituteGroup.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        if (editCourseListSt != null && editCourseListSt.size() > 0) {
            for (int course_st = 0; course_st < editCourseListSt.size(); course_st++)
                if (editCourseListSt.get(course_st).equals("1"))
                    allNdc.setChecked(true);
                else if (editCourseListSt.get(course_st).equals("2"))
                    allAfwc.setChecked(true);
                else if (editCourseListSt.get(course_st).equals("3"))
                    allCapston.setChecked(true);
        } else {
            allNdc.setChecked(false);
            allAfwc.setChecked(false);
            allCapston.setChecked(false);
        }
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
        //getCMData();
        custom.setTextColor(custom.getContext().getResources().getColor(R.color.white));
        custom.setBackgroundColor(custom.getContext().getResources().getColor(R.color.ndc_color));
        common.setBackgroundColor(common.getContext().getResources().getColor(R.color.ash));
        common.setTextColor(common.getContext().getResources().getColor(R.color.black));
        commonTypeLayout.setVisibility(View.GONE);
        ndc.setChecked(true);

        instituteGroup.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);


        userTaskAssignAdapter = new TaskAssignAdapter(getActivity(), "ndc", editSelectedList, SELECTED_TAB);
//        userTaskAssignAdapter.notifyDataSetChanged();
//        listView.setAdapter(userTaskAssignAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
//                linearLayoutManager.getOrientation());
//        rv.addItemDecoration(dividerItemDecoration);
        listView.addItemDecoration(new SmallVerticalSpaceItemDecoration(getResources()));
        listView.setLayoutManager(linearLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(userTaskAssignAdapter);

        if (editSelectedList != null && editSelectedList.size() > 0 && afwcStList!=null) {
            for (int i = 0; i < afwcStList.size(); i++) {
                if (editSelectedList.contains(afwcStList.get(i).getId()))
                    afwcStList.get(i).setSelected(true);
            }
        }
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

    RequestBody rbDueDate, rbApiKey, rbTitle, rbDescription;

    private void callTaskAddApi() {

        //File myFile = new File(selectedFilePath);


        rbApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());
        rbTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title.getText().toString().trim());
        rbDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description.getText().toString().trim());
        if (!selectedDate.equalsIgnoreCase("Due Date"))
            rbDueDate = RequestBody.create(MediaType.parse("multipart/form-data"), dueDate.getText().toString());


//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);

//        builder.addFormDataPart("user_name", "Robert");
//        builder.addFormDataPart("email", "mobile.apps.pro.vn@gmail.com");

        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
//        for (int i = 0; i < attachmentModelList.size(); i++) {
//            File file = new File(attachmentModelList.get(i).getFilePath());
//            builder.addFormDataPart("file[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//        }
//
//
//        MultipartBody attachments = builder.build();


        List<String> filePaths = new ArrayList<>();


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("title", title.getText().toString().trim());
        builder.addFormDataPart("description", description.getText().toString().trim());
        builder.addFormDataPart("api_key", AppSharedPreference.getApiKey());
        builder.addFormDataPart("due_date", selectedDate);

        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
        for (int j = 0; j < selectedList.size(); j++) {
            builder.addFormDataPart("users[" + j + "]", selectedList.get(j));
        }
        for (int k = 0; k < courseList.size(); k++) {
            builder.addFormDataPart("course[" + k + "]", courseList.get(k));
        }
        for (int i = 0; i < attachmentModelList.size(); i++) {
            File file = new File(attachmentModelList.get(i).getFilePath());
            builder.addFormDataPart("attachments[" + i + "]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }


        MultipartBody requestBody = builder.build();


//        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[attachmentModelList.size()];
//
//        List<MultipartBody.Part> parts = new ArrayList<>();
//        for (int i=0; i < attachmentModelList.size(); i++){
//            File file = new File(attachmentModelList.get(i).getFilePath());
//            RequestBody attachmentBody = RequestBody.create(MediaType.parse("image/*"), file);
//            surveyImagesParts[i] = MultipartBody.Part.createFormData("attachments", file.getName(), attachmentBody);
//           // parts.add(MultipartBody.Part.createFormData("attachment"+i, file.getName(), attachmentBody));
//
//        }

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Submitting your task...");
        String[] users = new String[selectedList.size()];
        users = selectedList.toArray(users);

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();


                        if (value.code() == 200) {
                            Log.v("PigeonholeFragment", value.message());
                            Toast.makeText(getActivity(), "Your task successfully submitted", Toast.LENGTH_SHORT).show();
                            clearForm();
                        } else
                            Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

//final String selectedType
    private void callPigeonholeGetCourseApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


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

                        uiHelper.dismissLoadingDialog();
//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (pigeonholeGetCourseApiResponse.getCode() == 200) {
                            Log.v("PigeonholeFragment", value.message());
                            parseCourseData(pigeonholeGetCourseApiResponse.getCourseData().getCourses());
//                            if(selectedType.equalsIgnoreCase("common"))
//                            showDialogAssignToCommon();
//                            else if(selectedType.equalsIgnoreCase("custom"))
//                                showDialogAssignToCustom();
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
        UserCourses userCourses = new UserCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equalsIgnoreCase("ND COURSE")) {

                ndcStList = courses.get(i).getStudents();
                userCourses.setNdc(true);
            } else if (courses.get(i).getName().equalsIgnoreCase("AFW COURSE")) {
                afwcStList = courses.get(i).getStudents();
                userCourses.setAfwc(true);
            } else if (courses.get(i).getName().equalsIgnoreCase("CAPSTON")) {
                capstonStList = courses.get(i).getStudents();
                userCourses.setCapston(true);
            }
        }
        AppSharedPreference.setUserCourse(userCourses);
    }


    private void callTaskApi(String id) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getSinglePHDetails(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<PHTaskViewResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<PHTaskViewResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        PHTaskViewResponse phTaskViewResponse = value.body();
//                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if (phTaskViewResponse.getCode() == 200) {
                            Log.v("PigeonholeFragment", value.message());

                            PHTaskViewData phTaskViewData = phTaskViewResponse.getPhTaskViewData();
                            //setPageData(phTaskViewData);
                            populateData(phTaskViewData);
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

    List<Course> editCourseList = new ArrayList<>();
    ArrayList<String> editSelectedList = new ArrayList<>();
    ArrayList<String> editCourseListSt = new ArrayList<>();
    ArrayList<AttachmentModel> attachmentModelsEdit = new ArrayList<>();
    private static String SELECTED_TAB = "";

    private void populateData(PHTaskViewData phTaskViewData) {
        PHSingleTask phSingleTask = phTaskViewData.getPhSingleTask();
        if (phSingleTask.getTitle() != null)
            title.setText(phSingleTask.getTitle());
        if (phSingleTask.getDescription() != null)
            description.setText(phSingleTask.getDescription());

        if (phSingleTask.getDueDate() != null) {
            try {
                String st = AppUtility.getDateString(phSingleTask.getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER);
                dueDate.setText(AppUtility.getDateString(phSingleTask.getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
            } catch (Exception e) {

            }
        }
        List<Attachment> attachmentEditList;
        AttachmentModel attachmentModel = new AttachmentModel();
        if (phSingleTask.getAttachments() != null) {
            attachmentEditList = phSingleTask.getAttachments();
            attachmentModelsEdit = new ArrayList<>();
            for (int i = 0; i < attachmentEditList.size(); i++) {
                attachmentModel.setFileName(attachmentEditList.get(i).getName());
                attachmentModelsEdit.add(attachmentModel);
            }

            attachmentAdapterMain.setData(attachmentModelsEdit);
            attachmentAdapterMain.notifyDataSetChanged();
        }
        String total = phSingleTask.getTotal();
        int totalSt = 0;
        if (phTaskViewData.getCourses() != null) {
            editCourseList = phTaskViewData.getCourses();
            for (int j = 0; j < editCourseList.size(); j++) {
                totalSt = editCourseList.get(j).getStudents().size();
                if (editCourseList.get(j).getStudents() != null) {
                    for (int st_id = 0; st_id < editCourseList.get(j).getStudents().size(); st_id++)
                        editSelectedList.add(editCourseList.get(j).getStudents().get(st_id).getId());
                }
                editCourseListSt.add(editCourseList.get(j).getId());
            }
            if (total != null) {
                if (Integer.parseInt(total) == totalSt) {
                    SELECTED_TAB = "common";
                } else {
                    SELECTED_TAB = "custom";
                }
            }

        }


        assignBtn.setText("Update");
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


    private void initTaskEditApi() {

        pigeonholeTaskAdd = new PigeonholeTaskAdd();
        if (!title.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setTitle(title.getText().toString());
        if (!description.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setDescription(description.getText().toString());
        if (!dueDate.getText().toString().trim().isEmpty())
            pigeonholeTaskAdd.setTitle(dueDate.getText().toString());

        UserCourses userCourses = AppSharedPreference.getUserCourse();
        if(allNdc==null || allAfwc ==null || allCapston==null) {

           selectedList = editSelectedList;
           courseList = editCourseListSt;
        }
        else {
            if (allNdc.isChecked() || allAfwc.isChecked() || allCapston.isChecked()) {
                selectedList = new ArrayList<>();
                if (allNdc.isChecked() && ndcStList.size() > 0) {
                    if (userCourses.isNdc())
                        courseList.add("1");
                    for (Student list : ndcStList) {
                        selectedList.add(list.getId());
                    }
                }
                if (allAfwc.isChecked() && afwcStList.size() > 0) {
                    if (userCourses.isAfwc())
                        courseList.add("2");
                    for (Student list : afwcStList) {
                        selectedList.add(list.getId());
                    }
                }
                if (allCapston.isChecked() && capstonStList.size() > 0) {
                    if (userCourses.isCapston())
                        courseList.add("3");
                    for (Student list : capstonStList) {
                        selectedList.add(list.getId());
                    }
                }
            } else {
                if (ndcCount > 0) {
                    if (userCourses.isNdc())
                        courseList.add("1");
                }
                if (afwcCount > 0) {
                    if (userCourses.isAfwc())
                        courseList.add("2");
                }
                if (capstonCount > 0) {
                    if (userCourses.isCapston())
                        courseList.add("3");
                }
            }
        }

        String titleSt = title.getText().toString().trim();
        String descriptionSt = description.getText().toString().trim();

        if (titleSt.isEmpty() || selectedList.size() <= 0) {
            Toast.makeText(getActivity(), "Title and Assign To should not be empty", Toast.LENGTH_LONG).show();
            return;
        }


//        Toast.makeText(getActivity(), ""+courseList.size() , Toast.LENGTH_LONG).show();

        callTaskEditApi();

    }
    private void callTaskEditApi() {


        int m =0;

//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);

//        builder.addFormDataPart("user_name", "Robert");
//        builder.addFormDataPart("email", "mobile.apps.pro.vn@gmail.com");

        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
//        for (int i = 0; i < attachmentModelList.size(); i++) {
//            File file = new File(attachmentModelList.get(i).getFilePath());
//            builder.addFormDataPart("file[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//        }
//
//
//        MultipartBody attachments = builder.build();


        List<String> filePaths = new ArrayList<>();


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("title", title.getText().toString().trim());
        builder.addFormDataPart("description", description.getText().toString().trim());
        builder.addFormDataPart("api_key", AppSharedPreference.getApiKey());
        builder.addFormDataPart("due_date", selectedDate);

        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
        for (int j = 0; j < selectedList.size(); j++) {
            builder.addFormDataPart("users[" + j + "]", selectedList.get(j));
        }
        for (int k = 0; k < courseList.size(); k++) {
            builder.addFormDataPart("course[" + k + "]", courseList.get(k));
        }
        if(attachmentModelList!=null && attachmentModelList.size()>0) {
            for (int i = 0; i < attachmentModelList.size(); i++) {
                if (attachmentModelList.get(i).getFilePath() != null) {
                    File file = new File(attachmentModelList.get(i).getFilePath());
                    builder.addFormDataPart("attachments[" + i + "]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                } else {
                    builder.addFormDataPart("attachments_edit[" + m + "]", attachmentModelList.get(i).getFileName());
                    m++;
                }
            }
        }
        else if(attachmentModelsEdit!=null && attachmentModelsEdit.size()>0 ) {
            for (int p = 0; p < attachmentModelsEdit.size(); p++) {
                builder.addFormDataPart("attachments_edit[" + p + "]", attachmentModelsEdit.get(p).getFileName());
            }
        }

        MultipartBody requestBody = builder.build();

        MultipartBody.Builder builder1 = new MultipartBody.Builder();
        builder1.setType(MultipartBody.FORM);
        builder1.addFormDataPart("id", id);
        MultipartBody reQuestId = builder1.build();


//        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[attachmentModelList.size()];
//
//        List<MultipartBody.Part> parts = new ArrayList<>();
//        for (int i=0; i < attachmentModelList.size(); i++){
//            File file = new File(attachmentModelList.get(i).getFilePath());
//            RequestBody attachmentBody = RequestBody.create(MediaType.parse("image/*"), file);
//            surveyImagesParts[i] = MultipartBody.Part.createFormData("attachments", file.getName(), attachmentBody);
//           // parts.add(MultipartBody.Part.createFormData("attachment"+i, file.getName(), attachmentBody));
//
//        }

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Submitting your task...");
        String[] users = new String[selectedList.size()];
        users = selectedList.toArray(users);

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId(URLHelper.GET_PIGEONHOLE_TASK_EDIT + "/" + id + "/").getTaskEdit(requestBody)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();


                        if (value.code() == 200) {
                            Log.v("PigeonholeFragment", value.message());
                            Toast.makeText(getActivity(), "Your task successfully submitted", Toast.LENGTH_SHORT).show();
                            clearForm();
                        } else
                            Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    AlertDialog confirmViewDialog;
    TextView dialogTitle, dialogDescription, dialogDueDate;
    TaskAssignConfirmAdapter taskAssignConfirmAdapter;
    TaskAssignAttachmentConfirmAdapter taskAssignAttachmentConfirmAdapter;
    RecyclerView selectedUserRv, attachmentConfirmRv;
    public static ArrayList<Student> confirmList = new ArrayList<>();
    public void confirmDialog() {
//        if(selectedList!=null && selectedList.size()>0) {
//            for (int i = 0; i < selectedList.size(); i++)
//            {
//                if(ndcStList.contains())
//            }
//        }

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View confirmView = factory.inflate(R.layout.dialog_task_assign_confirm, null);
        dialogTitle = confirmView.findViewById(R.id.dialogTitle);
        dialogDescription = confirmView.findViewById(R.id.dialogDescription);
        dialogDueDate = confirmView.findViewById(R.id.dialogDueDate);
        selectedUserRv = confirmView.findViewById(R.id.task_confirm_recycleview);

        attachmentConfirmRv = confirmView.findViewById(R.id.attachment_confirm_rv);

        taskAssignConfirmAdapter = new TaskAssignConfirmAdapter(getActivity(), confirmList);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        selectedUserRv.addItemDecoration(new SmallVerticalSpaceItemDecoration(getResources()));
        selectedUserRv.setLayoutManager(linearLayoutManager);
        selectedUserRv.setItemAnimator(new DefaultItemAnimator());
        selectedUserRv.setAdapter(taskAssignConfirmAdapter);
        taskAssignConfirmAdapter.notifyDataSetChanged();


        taskAssignAttachmentConfirmAdapter = new TaskAssignAttachmentConfirmAdapter(getActivity(), attachmentModelList);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        attachmentConfirmRv.addItemDecoration(new SmallVerticalSpaceItemDecoration(getResources()));
        attachmentConfirmRv.setLayoutManager(linearLayoutManager);
        attachmentConfirmRv.setItemAnimator(new DefaultItemAnimator());
        attachmentConfirmRv.setAdapter(taskAssignAttachmentConfirmAdapter);
        taskAssignConfirmAdapter.notifyDataSetChanged();

        dialogTitle.setText(title.getText().toString());
        dialogDescription.setText(description.getText().toString());
        dialogDueDate.setText(dueDate.getText().toString());



//        text.setText(st);
        confirmViewDialog = new AlertDialog.Builder(getActivity()).create();
        confirmViewDialog.setCancelable(false);
        confirmViewDialog.setView(confirmView);
        confirmView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTaskAddApi();

                confirmViewDialog.dismiss();
            }
        });
        confirmView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmViewDialog.dismiss();
            }
        });

        confirmViewDialog.show();

    }

    private void clearForm() {
        title.setText("");
        description.setText("");
        dueDate.setText("Due Date");
        selectedList.clear();
        courseList.clear();
        attachmentModelList.clear();
        attachmentAdapterMain.notifyDataSetChanged();
        attachmentModelsEdit.clear();

    }
}
