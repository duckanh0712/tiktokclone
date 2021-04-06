package com.example.tiktukapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoStepOneActivity extends AppCompatActivity {

    private static final String TAG = "ActivityVideoStepOne";
    private Context context;
    private VideoView videoView;
    ActionBar toolbar;
    Uri videoUri = null;
    private static final int PICK_VIDEO = 100;

    Button btnUploadFile, btnContinue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_step_one);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        videoView = findViewById(R.id.videoPrintView);
        btnUploadFile = findViewById(R.id.btnUploadFile);
        btnContinue =findViewById(R.id.btnContinue);
        context = getApplicationContext();
        //toolbar = findViewById(R.id.toolbar);
        //toolbar = getSupportActionBar();

//        Intent intent = getIntent();
//        videoUri = Uri.parse(intent.getStringExtra("videoUri"));
//        Log.d(TAG,"video" + intent.getStringExtra("videoUri"));
//        videoView.setVideoURI(videoUri);
//        videoView.start();

        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(gallery,"Select video"), PICK_VIDEO);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoUri != null) {
                    Intent intent = new Intent(context, VideoUploadActivity.class);
                    intent.putExtra("videoUri",videoUri.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(context,"Vui lòng chọn video trước!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_VIDEO){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }
}