package com.classtune.ndc.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.classtune.ndc.R;
import com.classtune.ndc.utils.URLHelper;
//import com.marcinmoskala.videoplayview.VideoPlayView;

public class PlayerActivity extends AppCompatActivity {
    private Button btnonce, btncontinuously, btnstop, btnplay;
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private boolean isContinuously = false;
    private ProgressBar progressBar;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if (getIntent() != null)
            url = getIntent().getStringExtra("url");

        String u = URLHelper.BASE_URL  + url;
//        VideoPlayView bigVideoView = findViewById(R.id.bigVideoView);
////        bigVideoView.setVideoUrl("http://teamworkbd.com/assets/uploads/reading_file/videoplayback2.mp4");
//        Glide.with(this)
//                .load(R.drawable.loader)
//                .load("http://teamworkbd.com/assets/uploads/reading_file/videoplayback2.mp4")
//                .into(bigVideoView.getLoadingView());
        progressBar = (ProgressBar) findViewById(R.id.progrss);
//        btnonce = (Button) findViewById(R.id.btnonce);
//        btncontinuously = (Button) findViewById(R.id.btnconti);
//        btnstop = (Button) findViewById(R.id.btnstop);
//        btnplay = (Button) findViewById(R.id.btnplay);
        vv = (VideoView) findViewById(R.id.vv);

        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        String uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4"; //update package name
        uri = Uri.parse(u);

        playVideo();

//        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                if(isContinuously){
//                    vv.start();
//                }
//            }
//        });

//        btnstop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vv.pause();
//            }
//        });

//        btnplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vv.start();
//            }
//        });

//        btnonce.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isContinuously = false;
//                progressBar.setVisibility(View.VISIBLE);
//                vv.setMediaController(mediacontroller);
//                vv.setVideoURI(uri);
//                vv.requestFocus();
//                vv.start();
//            }
//        });

//        btncontinuously.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isContinuously = true;
//                progressBar.setVisibility(View.VISIBLE);
//                vv.setMediaController(mediacontroller);
//                vv.setVideoURI(uri);
//                vv.requestFocus();
//                vv.start();
//            }
//        });

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void playVideo() {
        progressBar.setVisibility(View.VISIBLE);
        vv.setMediaController(mediacontroller);
        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();
    }

}
