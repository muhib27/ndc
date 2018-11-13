package com.classtune.ndc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.classtune.ndc.R;
import com.classtune.ndc.utils.NetworkConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // load();
    }

    private void load() {
        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
    }


}
