package com.classtune.ndc.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.adapter.TaskAssignAttachmentConfirmAdapter;
import com.classtune.ndc.adapter.TaskAssignConfirmAdapter;
import com.classtune.ndc.apiresponse.LoginApiModel;
import com.classtune.ndc.apiresponse.menu_api.MenuApiResponse;
import com.classtune.ndc.apiresponse.menu_api.User;
import com.classtune.ndc.apiresponse.menu_api.UserMenu;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.model.LoginResponseModel;
import com.classtune.ndc.model.Wrapper;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.GsonParser;
import com.classtune.ndc.utils.MyApplication;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks,EasyPermissions.RationaleCallbacks {
    EditText etUserName;
    EditText etPassword;
    CheckBox rememberMe;
    Button btnLogin;
    UIHelper uiHelper;

    //String fcm_id = "eSWBaw0MaMM:APA91bFhVluppQU8GIpUuMUEF2gCXuWE4ZXiV6Nv9Wsm9ywYe7m4fDx6aK6DakJgCqvu4Iv7_L91AfNxrfXQICVL-pjSTI1b_00MsA5RNqZ_MOy7QQLJqJLslyEQavUSKn13Rc3tWYxy";

    private TextView tvForgetPassword;
    public String username = "", password = "";
    ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};

    private static final int RC_PHONE_STATE = 123;
    //private static final int RC_LOCATION_CONTACTS_PERM = 124;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // AppSharedPreference.setFcm(fcm_id);

        uiHelper = new UIHelper(LoginActivity.this);


        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("Authenticating...");
       // progressDialog.show();

        etUserName = (EditText) findViewById(R.id.et_username);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        tvForgetPassword.setOnClickListener(this);
        //etUserName.setText("ovi@gmail.com");
        rememberMe = findViewById(R.id.rememberMe);
        etPassword = (EditText) findViewById(R.id.et_password);
        //etPassword.setText("123456");
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        if(AppSharedPreference.getRememberMe())
        {
            if(!AppSharedPreference.getUserName().isEmpty())
                etUserName.setText(AppSharedPreference.getUserName());
            if(!AppSharedPreference.getUserPassword().isEmpty())
                etPassword.setText(AppSharedPreference.getUserPassword());
            rememberMe.setChecked(true);
        }

    }

    private boolean hasPhoneStatePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE);
    }

    @AfterPermissionGranted(RC_PHONE_STATE)
    public void readPhoneStateTask() {
//        if (hasPhoneStatePermission()) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Phone State things", Toast.LENGTH_LONG).show();
            validateFieldAndCallLogIn();
//        } else {
//            // Ask for one permission
//            EasyPermissions.requestPermissions(
//                    this,
//                    getString(R.string.rationale_camera),
//                    RC_PHONE_STATE,
//                    Manifest.permission.READ_PHONE_STATE);
//        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                validateFieldAndCallLogIn();
                readPhoneStateTask();
                break;
//            case R.id.tv_forget_password:
//                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
//                startActivity(intent);
//                break;

            default:
                break;
        }
    }

    private void validateFieldAndCallLogIn() {
        username = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUserName.setFocusable(true);
            etUserName.setError(getString(R.string.java_loginactivity_enter_user_name));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setFocusable(true);
            etPassword.setError(getString(R.string.java_loginactivity_enter_password));
            etPassword.requestFocus();
        }
        else {
//            if (ContextCompat.checkSelfPermission(LoginActivity.this,
//                    Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//
//                showMessageOKCancel("We need Phone Call Permission only for device id");
//                // Should we show an explanation?
//                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
//                        Manifest.permission.READ_PHONE_STATE)) {
//
//
//
//                } else {
//
//
//                    ActivityCompat.requestPermissions(LoginActivity.this,
//                            new String[]{Manifest.permission.READ_PHONE_STATE},
//                            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//
//                }
//            }
//            else {

            callLoginApi(username, password);
           // }
        }

    }


    private void callLoginApi(final String username, final String password) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().userLogin(username, password, AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LoginApiModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<LoginApiModel> value) {
                        uiHelper.dismissLoadingDialog();
                        LoginApiModel loginApiModel = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (loginApiModel.getCode()!= null && loginApiModel.getCode() == 200) {
                        //    passwordChangeDialog();

                            AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey(), rememberMe.isChecked());
                            callMenuApi();
                        }
                        else
                            uiHelper.dismissLoadingDialog();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();

//                        } else {
//
//                            Toast.makeText(getApplicationContext(), wrapper.getStatus().getMsg(), Toast.LENGTH_SHORT).show();
//                        }
                    }


                    @Override
                    public void onError(Throwable e) {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
//                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    AlertDialog confirmViewDialog;
    EditText dialogPassword, dialogConfirmPassword;
   // TextView dialogTitle;


    public void passwordChangeDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View confirmView = factory.inflate(R.layout.dialog_password_change, null);
        dialogPassword = confirmView.findViewById(R.id.password);
        dialogConfirmPassword = confirmView.findViewById(R.id.confirm_password);

        confirmViewDialog = new AlertDialog.Builder(this).create();
        confirmViewDialog.setCancelable(false);
        confirmViewDialog.setView(confirmView);
        confirmView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogPassword.equals(dialogConfirmPassword))
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

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
    private void callMenuApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
//        uiHelper.showLoadingDialog("Authenticating...");



        RetrofitApiClient.getApiInterface().getMenu(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<MenuApiResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<MenuApiResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        MenuApiResponse menuApiResponse = value.body();

//                        AppSharedPreference.setUserNameAndPassword(username, password, loginApiModel.getData().getApiKey());

                        if(menuApiResponse.getCode()==200) {
                            AppSharedPreference.setUserBasicInfo(menuApiResponse.getMenuData().getUser());
                            setUpUserPermission(menuApiResponse.getMenuData().getMenus().getSubMenu());
                            setUpUserMenu(menuApiResponse.getMenuData().getMenus().getMainMenu());

                         //   UserPermission u = AppSharedPreference.getUserPermission();

//                            User user1 = AppSharedPreference.getUserBasicInfo();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
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

    private void setUpUserPermission(List<String> subMenu) {
        UserPermission userPermission = new UserPermission();
        for(int i=0; i<subMenu.size(); i++){
            if(subMenu.get(i).equalsIgnoreCase("tasks-list"))
                userPermission.setTasksList(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-add"))
                userPermission.setTasksAdd(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-edit"))
                userPermission.setTasksEdit(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-delete"))
                userPermission.setTasksDelete(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-submit_task"))
                userPermission.setUserTasksSubmitTask(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-view"))
                userPermission.setTasksView(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-submitted_list"))
                userPermission.setTasksSubmittedList(true);
            else if(subMenu.get(i).equalsIgnoreCase("tasks-single_view"))
                userPermission.setTasksSingleView(true);
            else if(subMenu.get(i).equalsIgnoreCase("user-add"))
                userPermission.setUserAdd(true);
            else if(subMenu.get(i).equalsIgnoreCase("user-edit"))
                userPermission.setUserEdit(true);
            else if(subMenu.get(i).equalsIgnoreCase("user-delete"))
                userPermission.setUserDelete(true);
        }

        AppSharedPreference.setUserPermission(userPermission);

    }
    private void setUpUserMenu(List<String> menu) {
        UserMenu userMenu = new UserMenu();
        for(int i=0; i<menu.size(); i++){
            if(menu.get(i).equalsIgnoreCase("tasks"))
                userMenu.setTasks(true);
            else if(menu.get(i).equalsIgnoreCase("events"))
                userMenu.setEvents(true);
            else if(menu.get(i).equalsIgnoreCase("research"))
                userMenu.setResearch(true);
            else if(menu.get(i).equalsIgnoreCase("research_wing"))
                userMenu.setResearch_wing(true);
            else if(menu.get(i).equalsIgnoreCase("notice"))
                userMenu.setNotice(true);
            else if(menu.get(i).equalsIgnoreCase("routine"))
                userMenu.setRoutine(true);
            else if(menu.get(i).equalsIgnoreCase("reading_list"))
                userMenu.setReading_list(true);

        }

        AppSharedPreference.setUserMenu(userMenu);

    }


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
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasPhoneStatePermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

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
}
