package com.classtune.ndc.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.classtune.ndc.apiresponse.pigeonhole_api.PigeonholeTaskAdd;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.model.AttachmentModel;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.NetworkConnection;
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
public class CMTaskSubmitFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    public static CMTaskSubmitFragment instance;
    TextView description;
    Button attachFileBtn, submitBtn;
    ListView attachmentListMain;
    private ArrayList<String> listFiles;
    UIHelper uiHelper;
    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_STORAGE_CAMERA_PERM = 124;
    public static String phTaskSubmitId = "";

    public CMTaskSubmitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cmtask_submit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        submitBtn = view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
        uiHelper = new UIHelper(getActivity());
        listFiles = new ArrayList<>();
        attachmentModelList = new ArrayList<>();
        attachmentListMain = view.findViewById(R.id.attachmentListMain);
        description = view.findViewById(R.id.description);
        attachFileBtn = view.findViewById(R.id.attachFile);
        attachFileBtn.setOnClickListener(this);


        attachmentAdapterMain = new AttachmentAdapterMain(getActivity(), attachmentModelList);
        attachmentAdapterMain.notifyDataSetChanged();
        attachmentListMain.setAdapter(attachmentAdapterMain);

        Bundle b = getArguments();
        if (getArguments() != null) {
            if (b.getString("id", "") != null)
                phTaskSubmitId = b.getString("id", "");
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

    private void showChooser() {
        instance = this;
        // showFileDialog();
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

                for (VideoFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");
                    attachmentModelList.add(new AttachmentModel(f.getName(), path));
                }
                attachmentAdapter.notifyDataSetChanged();

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


    private boolean hasStorageAndCameraPermission() {
        return EasyPermissions.hasPermissions(getActivity(), STORAGE_AND_CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attachFile:
                readStorageStateTask();
                break;
            case R.id.btnBrowse:
                browseFile(SELECTED_TYPE);
                break;
            case R.id.submit:
                initTaskSubmitApi();
                break;
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
    List<AttachmentModel> attachmentModelList;
    AttachmentAdapter attachmentAdapter;
    AttachmentAdapterMain attachmentAdapterMain;

    private void showDialogAttachment() {


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

        fileAttachDialog = new AlertDialog.Builder(getActivity()).create();
        fileAttachDialog.setView(fileAttachDialogView);
        fileAttachDialogView.findViewById(R.id.btnAttach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attachmentAdapterMain.setData(attachmentModelList);
                attachmentAdapterMain.notifyDataSetChanged();

//                if(!id.isEmpty() && attachmentModelsEdit!=null && attachmentModelsEdit.size()>0)
//                {
//                    attachmentAdapterMain.AddData(attachmentModelsEdit);
//                }
                fileAttachDialog.dismiss();
            }
        });


        fileAttachDialog.show();

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


    private void initTaskSubmitApi() {

        if (!description.getText().toString().trim().isEmpty())
            callTaskSubmitApi();

    }

    private void callTaskSubmitApi() {


        List<String> filePaths = new ArrayList<>();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("content", description.getText().toString().trim());
        builder.addFormDataPart("api_key", AppSharedPreference.getApiKey());


        for (int i = 0; i < attachmentModelList.size(); i++) {
            File file = new File(attachmentModelList.get(i).getFilePath());
            builder.addFormDataPart("attachments[" + i + "]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }


        MultipartBody requestBody = builder.build();

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Submitting your task...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId(URLHelper.GET_PIGEONHOLE_TASK_SUBMIT_TASK + phTaskSubmitId + "/").getPHTaskSubmitTask(requestBody)

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
                            getActivity().getSupportFragmentManager().popBackStack();
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


}
