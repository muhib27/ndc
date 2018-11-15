package com.classtune.ndc.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.model.LoginResponseModel;
import com.classtune.ndc.model.Wrapper;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.GsonParser;
import com.classtune.ndc.utils.NetworkConnection;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUserName;
    EditText etPassword;
    Button btnLogin;

    private TextView tvForgetPassword;
    public String username = "", password = "";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
       // progressDialog.show();

        etUserName = (EditText) findViewById(R.id.et_username);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        tvForgetPassword.setOnClickListener(this);
        //etUserName.setText("ovi@gmail.com");
        etPassword = (EditText) findViewById(R.id.et_password);
        //etPassword.setText("123456");
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                validateFieldAndCallLogIn();
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
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().userLogin(username, password)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {

                        Log.e("login", "onResponse: "+value.body());
                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
                                value.body());

//                        if (wrapper.getStatus().getCode() == 200) {
                            AppSharedPreference.setUserNameAndPassword(username, password);

//                            Headers headers = value.headers();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

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
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                    }
                });


    }
}
